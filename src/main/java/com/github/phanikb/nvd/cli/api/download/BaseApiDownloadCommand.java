package com.github.phanikb.nvd.cli.api.download;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import picocli.CommandLine;

import com.github.phanikb.nvd.cli.processor.api.download.ApiDownloader;
import com.github.phanikb.nvd.common.NvdException;
import com.github.phanikb.nvd.enums.CommandApiEndpointType;

import static com.github.phanikb.nvd.common.Constants.DEFAULT_MIN_RESULTS_PER_PAGE;
import static com.github.phanikb.nvd.common.Constants.DEFAULT_NUMBER_OF_PRODUCERS;

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
        } catch (Exception e) {
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
}
