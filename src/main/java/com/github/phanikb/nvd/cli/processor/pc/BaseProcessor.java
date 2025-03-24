package com.github.phanikb.nvd.cli.processor.pc;

import java.net.URI;
import java.nio.file.Path;
import java.util.concurrent.BlockingDeque;

import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lombok.Getter;

import com.github.phanikb.nvd.common.HttpUtil;
import com.github.phanikb.nvd.common.NvdException;
import com.github.phanikb.nvd.common.QueueElement;
import com.github.phanikb.nvd.common.Util;
import com.github.phanikb.nvd.enums.FeedType;

@Getter
public abstract class BaseProcessor<T> implements Runnable {
    protected static final Logger logger = LogManager.getLogger(BaseProcessor.class);
    protected final BlockingDeque<QueueElement> downloadQueue;
    protected final FeedType feedType;
    protected final T poison;
    protected final int poisonPerCreator;
    protected final int maxResultsPerPage;
    protected final String endpoint;
    protected final Path outDir;
    protected final String outFilePrefix;

    protected BaseProcessor(
            FeedType feedType,
            T poison,
            int poisonPerCreator,
            int maxResultsPerPage,
            String endpoint,
            Path outDir,
            String outFilePrefix,
            BlockingDeque<QueueElement> downloadQueue) {
        this.feedType = feedType;
        this.poison = poison;
        this.outDir = outDir;
        this.outFilePrefix = outFilePrefix;
        this.poisonPerCreator = poisonPerCreator;
        this.maxResultsPerPage = maxResultsPerPage;
        this.endpoint = endpoint;
        this.downloadQueue = downloadQueue;
    }

    public BaseProcessor(
            FeedType feedType, T poison, Path outDir, String outFilePrefix, BlockingDeque<QueueElement> downloadQueue) {
        this.feedType = feedType;
        this.poison = poison;
        this.poisonPerCreator = 0;
        this.maxResultsPerPage = 0;
        this.endpoint = null;
        this.outDir = outDir;
        this.outFilePrefix = outFilePrefix;
        this.downloadQueue = downloadQueue;
        try {
            createOutDir();
        } catch (NvdException e) {
            throw new RuntimeException(e);
        }
    }

    protected <T> int getResults(URI uri, HttpClientResponseHandler<T> responseHandler) throws NvdException {
        if (uri == null || uri.getHost() == null) {
            throw new NvdException("invalid URI: " + uri);
        }
        logger.debug("results uri: {} ", uri);
        FeedType type = getFeedType();
        int retryInterval = HttpUtil.getRetryIntervalInSecs();
        int maxRetries = HttpUtil.getMaxRetries();

        // TODO: get api results
        return 0;
    }

    protected void createOutDir() throws NvdException {
        if (outDir != null) {
            Util.createDir(outDir);
        }
    }
}
