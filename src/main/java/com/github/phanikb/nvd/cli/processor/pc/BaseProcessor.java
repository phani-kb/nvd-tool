package com.github.phanikb.nvd.cli.processor.pc;

import java.net.URI;
import java.nio.file.Path;
import java.util.concurrent.BlockingDeque;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.util.TimeValue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lombok.Getter;

import com.github.phanikb.nvd.api2.cpe.CpeApiJson20Schema;
import com.github.phanikb.nvd.api2.cpe.match.CpematchApiJson20Schema;
import com.github.phanikb.nvd.api2.cve.CveApiJson20Schema;
import com.github.phanikb.nvd.api2.cve.history.CveHistoryApiJson20Schema;
import com.github.phanikb.nvd.cli.processor.api.download.CustomHttpRequestRetryStrategy;
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

    protected BaseProcessor(
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
            this.createOutDir();
        } catch (NvdException e) {
            throw new RuntimeException(e);
        }
    }

    protected <R> int getResults(URI uri, HttpClientResponseHandler<R> responseHandler) throws NvdException {
        if (uri == null || uri.getHost() == null) {
            throw new NvdException("invalid URI: " + uri);
        }
        logger.debug("results uri: {} ", uri);
        FeedType type = getFeedType();
        int retryInterval = HttpUtil.getRetryIntervalInSecs();
        int maxRetries = HttpUtil.getMaxRetries();

        try (CloseableHttpClient httpclient = HttpClients.custom()
                .setRetryStrategy(new CustomHttpRequestRetryStrategy(maxRetries, TimeValue.ofSeconds(retryInterval)))
                .setDefaultHeaders(HttpUtil.getNvdDefaultHeaders())
                .build()) {
            R apiJson = HttpUtil.getApiJson(uri, httpclient, responseHandler);
            return switch (type) {
                case CVE -> ((CveApiJson20Schema) apiJson).getTotalResults();
                case CVE_HISTORY -> ((CveHistoryApiJson20Schema) apiJson).getTotalResults();
                case CPE -> ((CpeApiJson20Schema) apiJson).getTotalResults();
                case CPE_MATCH -> ((CpematchApiJson20Schema) apiJson).getTotalResults();
                default -> throw new NvdException("unsupported API JSON type: " + apiJson.getClass());
            };
        } catch (Exception e) {
            throw new NvdException("failed to download " + type + " API JSON: " + e.getMessage(), e);
        }
    }

    private void createOutDir() throws NvdException {
        if (outDir != null) {
            Util.createDir(outDir);
        }
    }
}
