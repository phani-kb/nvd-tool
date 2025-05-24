package com.github.phanikb.nvd.cli.api.download;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;

import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import picocli.CommandLine;

import com.github.phanikb.nvd.cli.api.LastModApiOptions;
import com.github.phanikb.nvd.cli.processor.api.IApiDownloadUriConsumer;
import com.github.phanikb.nvd.cli.processor.api.IApiDownloadUriProducer;
import com.github.phanikb.nvd.cli.processor.api.download.ApiDownloader;
import com.github.phanikb.nvd.cli.processor.pc.DatesConsumer;
import com.github.phanikb.nvd.cli.processor.pc.DatesProducer;
import com.github.phanikb.nvd.cli.processor.pc.StartIndexConsumer;
import com.github.phanikb.nvd.cli.processor.pc.StartIndexProducer;
import com.github.phanikb.nvd.common.DateFormats;
import com.github.phanikb.nvd.common.NvdApiDate;
import com.github.phanikb.nvd.common.NvdException;
import com.github.phanikb.nvd.common.NvdProperties;
import com.github.phanikb.nvd.common.QueueElement;
import com.github.phanikb.nvd.common.Util;
import com.github.phanikb.nvd.enums.ApiQueryParams;
import com.github.phanikb.nvd.enums.CommandApiEndpointType;
import com.github.phanikb.nvd.enums.FeedType;
import com.github.phanikb.nvd.enums.NvdApiDateType;

import static com.github.phanikb.nvd.common.Constants.DEFAULT_DATETIME_POISON;
import static com.github.phanikb.nvd.common.Constants.DEFAULT_MIN_RESULTS_PER_PAGE;
import static com.github.phanikb.nvd.common.Constants.DEFAULT_NUMBER_OF_PRODUCERS;
import static com.github.phanikb.nvd.common.Constants.DEFAULT_POISON;

@CommandLine.Command(
        name = "base-api-download",
        mixinStandardHelpOptions = true,
        description = "Download data from API(s).")
public abstract class BaseApiDownloadCommand implements Callable<Integer>, IApiDownloadCommand {
    protected static final Logger logger = LogManager.getLogger(BaseApiDownloadCommand.class);

    @CommandLine.ParentCommand
    protected ApiDownloadCommand parent;

    @CommandLine.Mixin
    protected ApiDownloadCommonOptions apiCommonOptions;

    @Override
    public Integer call() throws Exception {
        validateOptions();
        logger.info("results per page = {} ", apiCommonOptions.getResultsPerPage());
        if (apiCommonOptions.getStartIndex() != null) {
            logger.info("start index = {} ", apiCommonOptions.getStartIndex());
        }

        return execute(getApiDownloader());
    }

    @Override
    public Integer execute(ApiDownloader downloader) throws NvdException {
        try {
            downloader.download(getLatch());
            downloader.generateOutputFile(downloader.getFeedType().getCollectionNodeName());
            downloader.deleteTempDir();
            return 0;
        } catch (NvdException e) {
            logger.error("error downloading data: {}", e.getMessage());
            throw new NvdException(e.getMessage(), e);
        }
    }

    @Override
    public void validateOptions() {
        parent.validateOptions();
        if (apiCommonOptions.getResultsPerPage() < DEFAULT_MIN_RESULTS_PER_PAGE) {
            throw new IllegalArgumentException(
                    String.format("results per page must be at least %d", DEFAULT_MIN_RESULTS_PER_PAGE));
        }
        if (apiCommonOptions.getStartIndex() != null && apiCommonOptions.getStartIndex() < 0) {
            throw new IllegalArgumentException("start index must be greater than or equal to 0");
        }
    }

    @Override
    public void validateCommandName(CommandLine.Model.CommandSpec spec) {
        String commandName = spec.name();
        for (CommandApiEndpointType command : CommandApiEndpointType.values()) {
            if (command.getCommandName().equals(commandName)) {
                return;
            }
        }
        throw new IllegalArgumentException("Invalid command name: " + commandName);
    }

    @Override
    public String getApiEndpoint(CommandLine.Model.CommandSpec spec) {
        String commandName = spec.name();
        for (CommandApiEndpointType command : CommandApiEndpointType.values()) {
            if (command.getCommandName().equals(commandName)) {
                String endpoint = NvdProperties.getApiEndpoint(command.getApiEndpointType());
                logger.info("command: {} api endpoint: {}", commandName, endpoint);
                return endpoint;
            }
        }
        throw new IllegalArgumentException("Invalid command name: " + commandName);
    }

    @Override
    public Integer getStartIndex() {
        return apiCommonOptions.getStartIndex();
    }

    @Override
    public int getMaxResultsPerPage() {
        return apiCommonOptions.getResultsPerPage();
    }

    @Override
    public File getOutDir() {
        return parent.getOutDir();
    }

    @Override
    public boolean isDeleteTempDir() {
        return parent.isDeleteTempDir();
    }

    @Override
    public boolean isCompress() {
        return parent.isZip();
    }

    @Override
    public String getOutFilename() {
        return parent.getOutFilename();
    }

    @Override
    public CountDownLatch getLatch() {
        return new CountDownLatch(DEFAULT_NUMBER_OF_PRODUCERS);
    }

    @Override
    public List<NameValuePair> getQueryParams() {
        List<NameValuePair> queryParams = new ArrayList<>();
        if (getStartIndex() != null && getStartIndex() >= 0) {
            queryParams.add(
                    new BasicNameValuePair(ApiQueryParams.START_INDEX.getName(), Objects.toString(getStartIndex())));
        }
        queryParams.add(new BasicNameValuePair(
                ApiQueryParams.RESULTS_PER_PAGE.getName(), Objects.toString(getMaxResultsPerPage())));
        return queryParams;
    }

    public List<NameValuePair> getDateRangeQueryParams(
            ApiQueryParams sd, LocalDateTime startDate, ApiQueryParams ed, LocalDateTime endDate) {
        List<NameValuePair> queryParams = new ArrayList<>();
        if (startDate != null) {
            queryParams.add(
                    new BasicNameValuePair(sd.getName(), startDate.format(DateFormats.ISO_DATE_TIME_EXT_FORMATTER)));
            if (endDate != null) {
                queryParams.add(
                        new BasicNameValuePair(ed.getName(), endDate.format(DateFormats.ISO_DATE_TIME_EXT_FORMATTER)));
            }
        }
        return queryParams;
    }

    protected List<NvdApiDate> getDates(LastModApiOptions.LastModDateRange lastModDateRange) {
        List<NvdApiDate> dates = new ArrayList<>();
        if (lastModDateRange != null) {
            LocalDateTime startDate = lastModDateRange.getLastModStartDate();
            LocalDateTime endDate = lastModDateRange.getLastModEndDate();
            dates.add(new NvdApiDate(
                    ApiQueryParams.LAST_MODIFIED_START_DATE.getName(), startDate, NvdApiDateType.START_DATE));
            dates.add(
                    new NvdApiDate(ApiQueryParams.LAST_MODIFIED_END_DATE.getName(), endDate, NvdApiDateType.END_DATE));
        }
        return dates;
    }

    protected ApiDownloader getApiDownloader(
            FeedType feedType, List<NvdApiDate> dates, CommandLine.Model.CommandSpec spec) {
        int poisonPerCreator = Util.getMaxThreads() / DEFAULT_NUMBER_OF_PRODUCERS;
        int rpp = getMaxResultsPerPage();
        String prefix = Util.getOutFilePrefix(feedType);
        String endpoint = getApiEndpoint(spec);
        IApiDownloadUriConsumer consumer;
        IApiDownloadUriProducer producer;
        BlockingDeque<QueueElement> downloadQueue = new LinkedBlockingDeque<>();
        List<NameValuePair> queryParams = getQueryParams();
        if (dates != null && dates.size() == 2) {
            producer = new DatesProducer(
                    feedType,
                    DEFAULT_DATETIME_POISON,
                    poisonPerCreator,
                    rpp,
                    endpoint,
                    getOutDir().toPath(),
                    prefix,
                    queryParams,
                    dates,
                    downloadQueue);
            consumer = new DatesConsumer(
                    feedType, DEFAULT_DATETIME_POISON, getOutDir().toPath(), prefix, downloadQueue);
        } else {
            producer = new StartIndexProducer(
                    feedType,
                    DEFAULT_POISON,
                    poisonPerCreator,
                    rpp,
                    endpoint,
                    getOutDir().toPath(),
                    prefix,
                    queryParams,
                    downloadQueue);
            consumer =
                    new StartIndexConsumer(feedType, DEFAULT_POISON, getOutDir().toPath(), prefix, downloadQueue);
        }
        return new ApiDownloader(
                feedType, getOutDir(), this.getOutFilename(), isDeleteTempDir(), isCompress(), rpp, consumer, producer);
    }
}
