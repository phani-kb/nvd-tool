package com.github.phanikb.nvd.cli.processor.pc;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutorService;

import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.net.URIBuilder;
import org.apache.hc.core5.util.TimeValue;

import lombok.Getter;

import com.github.phanikb.nvd.cli.processor.api.IApiDownloadUriProducer;
import com.github.phanikb.nvd.cli.processor.api.download.NvdHttpClientResponseHandler;
import com.github.phanikb.nvd.common.Constants;
import com.github.phanikb.nvd.common.DateFormats;
import com.github.phanikb.nvd.common.NvdApiDate;
import com.github.phanikb.nvd.common.NvdException;
import com.github.phanikb.nvd.common.QueueElement;
import com.github.phanikb.nvd.common.RequestTracker;
import com.github.phanikb.nvd.common.Util;
import com.github.phanikb.nvd.enums.ApiQueryParams;
import com.github.phanikb.nvd.enums.FeedType;

import static com.github.phanikb.nvd.cli.processor.api.download.NvdHttpClientResponseHandler.getResponseHandler;
import static com.github.phanikb.nvd.common.Util.getRangeDates;

@Getter
public class DatesProducer extends DatesProcessor<LocalDateTime> implements IApiDownloadUriProducer {
    private ProducerHelper producerHelper;

    public static DatesProducer create(
            FeedType type,
            LocalDateTime poison,
            int poisonPerCreator,
            int maxResultsPerPage,
            String endpoint,
            Path outDir,
            String outFilePrefix,
            List<NameValuePair> queryParams,
            List<NvdApiDate> dates,
            BlockingDeque<QueueElement> downloadQueue) {
        DatesProducer producer = new DatesProducer(
                type,
                poison,
                poisonPerCreator,
                maxResultsPerPage,
                endpoint,
                outDir,
                outFilePrefix,
                dates,
                downloadQueue);
        ProducerHelper helper = new ProducerHelper(type, producer::calculateTotalResults, queryParams);
        producer.producerHelper = helper;
        return producer;
    }

    public DatesProducer(
            FeedType type,
            LocalDateTime poison,
            int poisonPerCreator,
            int maxResultsPerPage,
            String endpoint,
            Path outDir,
            String outFilePrefix,
            List<NvdApiDate> dates,
            BlockingDeque<QueueElement> downloadQueue) {
        super(type, poison, poisonPerCreator, maxResultsPerPage, endpoint, outDir, outFilePrefix, dates, downloadQueue);
        this.producerHelper = null;
    }

    public DatesProducer(
            FeedType type,
            LocalDateTime poison,
            int poisonPerCreator,
            int maxResultsPerPage,
            String endpoint,
            Path outDir,
            String outFilePrefix,
            List<NvdApiDate> dates,
            BlockingDeque<QueueElement> downloadQueue,
            ProducerHelper producerHelper) {
        super(type, poison, poisonPerCreator, maxResultsPerPage, endpoint, outDir, outFilePrefix, dates, downloadQueue);
        this.producerHelper = producerHelper;
    }

    @Override
    public void run() {
        try {
            LocalDateTime startDate = getStartDate();
            LocalDateTime endDate = getEndDate();

            validateDateRange(startDate, endDate);

            int resultsPerPage = getMaxResultsPerPage();
            downloadQueue.clear();

            producerHelper.initResults(resultsPerPage);

            if (producerHelper.hasStartIndex()) {
                processWithStartIndex(resultsPerPage);
            } else {
                processDates(resultsPerPage);
            }
        } catch (InterruptedException | NvdException e) {
            logger.error(e.getMessage());
            Thread.currentThread().interrupt();
        } finally {
            addPoisonElements(poisonPerCreator);
        }
    }

    private void processWithStartIndex(int resultsPerPage) throws NvdException, InterruptedException {
        int startIndex = producerHelper.getStartIndex();
        int endIndex = startIndex + resultsPerPage - 1;
        addQueueElement(getNvdApiStartDate(), getNvdApiEndDate(), startIndex, endIndex);
    }

    private void addQueueElement(NvdApiDate start, NvdApiDate end, int startIndex, int endIndex)
            throws NvdException, InterruptedException {
        URI uri = getDownloadUri(start, end, startIndex, producerHelper.getQueryParams());
        File outFile = getDownloadFile(start.value(), end.value(), startIndex, endIndex, outDir);
        QueueElement element = new ChangeDatesQE(uri, start.value(), end.value(), startIndex, endIndex, outFile);
        logger.trace("adding element: {}, queue size: {} uri: {}", element, downloadQueue.size(), uri);
        downloadQueue.put(element);
    }

    private void processDates(int resultsPerPage) throws NvdException, InterruptedException {
        NvdApiDate startDate = getNvdApiStartDate();
        NvdApiDate endDate = getNvdApiEndDate();
        List<LocalDateTime> dates =
                getRangeDates(startDate.value(), endDate.value(), Constants.DEFAULT_MAX_RANGE_IN_DAYS);
        for (int i = 0; i < dates.size() - 1; i++) {
            LocalDateTime start = dates.get(i).plusDays(i == 0 ? 0 : 1);
            LocalDateTime end = dates.get(i + 1);
            processDateRange(start, end, resultsPerPage, startDate, endDate);
        }
    }

    private void processDateRange(
            LocalDateTime start, LocalDateTime end, int resultsPerPage, NvdApiDate startDate, NvdApiDate endDate)
            throws NvdException, InterruptedException {
        int totalResultsByDate = producerHelper.getTotalResultsByDate(start, end);
        if (totalResultsByDate == 0) {
            return;
        }
        int totalPagesByDate = (int) Math.ceil((double) totalResultsByDate / resultsPerPage);
        logger.info(
                "start date: {} end date: {} results: {} pages: {}", start, end, totalResultsByDate, totalPagesByDate);

        for (int page = 1; page <= totalPagesByDate; page++) {
            int startIndex = (page - 1) * resultsPerPage;
            int endIndex = startIndex + resultsPerPage - 1;
            addQueueElement(
                    new NvdApiDate(startDate.name(), start, startDate.type()),
                    new NvdApiDate(endDate.name(), end, endDate.type()),
                    startIndex,
                    endIndex);
        }
    }

    private void addPoisonElements(int poisonPerCreator) {
        for (int i = 0; i < poisonPerCreator; i++) {
            try {
                downloadQueue.put(new ChangeDatesQE(null, poison, poison, 0, 0, null));
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void generateUris(ExecutorService executorService, int numberOfProducers) {
        for (int i = 0; i < numberOfProducers; i++) {
            executorService.submit(new DatesProducer(
                    feedType,
                    poison,
                    poisonPerCreator,
                    maxResultsPerPage,
                    endpoint,
                    outDir,
                    outFilePrefix,
                    dates,
                    downloadQueue,
                    producerHelper));
        }
    }

    @Override
    public int getTotalPages() throws NvdException {
        return producerHelper.getTotalPages(getMaxResultsPerPage());
    }

    @Override
    public int getTotalResults() throws NvdException {
        return producerHelper.getTotalResults();
    }

    @Override
    public int getTotalFiles() {
        return producerHelper.getTotalFiles(getMaxResultsPerPage());
    }

    @Override
    public int calculateTotalResults() throws NvdException {
        return getTotalResultsUsingDates();
    }

    public final int getTotalResultsUsingDates() throws NvdException {
        URIBuilder builder;
        try {
            builder = new URIBuilder(endpoint);
            builder.addParameters(producerHelper.getQueryParams());

            builder.removeParameter(getNvdApiStartDate().name());
            builder.removeParameter(getNvdApiEndDate().name());
            builder.removeParameter(ApiQueryParams.START_INDEX.getName());
            builder.removeParameter(ApiQueryParams.RESULTS_PER_PAGE.getName());
            builder.addParameter(ApiQueryParams.START_INDEX.getName(), "0");
            builder.addParameter(ApiQueryParams.RESULTS_PER_PAGE.getName(), "1");
        } catch (URISyntaxException e) {
            throw new NvdException("failed to build URI", e);
        }

        NvdApiDate startDate = getNvdApiStartDate();
        NvdApiDate endDate = getNvdApiEndDate();

        List<LocalDateTime> dates =
                getRangeDates(startDate.value(), endDate.value(), Constants.DEFAULT_MAX_RANGE_IN_DAYS);
        logger.info("total dates to process: {}", dates.size());
        int totalResults = 0;
        NvdHttpClientResponseHandler<?> responseHandler = getResponseHandler(feedType);

        int maxRequests = 1;
        long windowSizeInMillis = Constants.DEFAULT_DELAY_BETWEEN_REQUESTS_IN_MS + 1000L;
        RequestTracker requestTracker = new RequestTracker(maxRequests, windowSizeInMillis);

        for (int i = 0; i < dates.size() - 1; i++) {
            LocalDateTime start = dates.get(i).plusDays(i == 0 ? 0 : 1);
            LocalDateTime end = dates.get(i + 1);
            logger.debug("processing date range: {} to {}", start, end);
            builder.removeParameter(startDate.name());
            builder.addParameter(startDate.name(), DateFormats.DateFormat.ISO_DATE_TIME_EXT.format(start));
            builder.removeParameter(endDate.name());
            builder.addParameter(endDate.name(), DateFormats.DateFormat.ISO_DATE_TIME_EXT.format(end, true));
            try {
                URI uri = builder.build();

                String key = uri.toString();
                while (!requestTracker.addRequest(key)) {
                    logger.debug("waiting for request tracker to have space for key: {}", key);
                    Util.sleepQuietly(TimeValue.ofMilliseconds(Constants.DEFAULT_DELAY_BETWEEN_REQUESTS_IN_MS));
                }

                int count = getResults(uri, responseHandler);
                logger.debug("results for {} to {}: {}", start, end, count);
                producerHelper.addTotalResultsByDate(start, end, count);
                totalResults += count;
            } catch (URISyntaxException e) {
                throw new NvdException("failed to build URI: " + builder, e);
            }
        }

        return totalResults;
    }
}
