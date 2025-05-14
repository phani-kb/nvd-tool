package com.github.phanikb.nvd.cli.processor.api.download;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lombok.Getter;

import com.github.phanikb.nvd.cli.processor.NvdDownloader;
import com.github.phanikb.nvd.cli.processor.api.IApiDownloadUriConsumer;
import com.github.phanikb.nvd.cli.processor.api.IApiDownloadUriProducer;
import com.github.phanikb.nvd.common.Constants;
import com.github.phanikb.nvd.common.HttpUtil;
import com.github.phanikb.nvd.common.NvdException;
import com.github.phanikb.nvd.common.Util;
import com.github.phanikb.nvd.enums.ArchiveType;
import com.github.phanikb.nvd.enums.FeedType;

import static com.github.phanikb.nvd.common.Util.getOutFilePrefix;

@Getter
public class ApiDownloader extends NvdDownloader {
    protected static final Logger logger = LogManager.getLogger(ApiDownloader.class);
    private final IApiDownloadUriConsumer consumer;
    private final IApiDownloadUriProducer producer;
    private final int numberOfProducers = Constants.DEFAULT_NUMBER_OF_PRODUCERS;
    private final int numberOfConsumers = Math.min(Constants.NUMBER_OF_PROCESSORS, Util.getMaxThreads());
    private final int rollingWindowInSecs = HttpUtil.getRollingWindowSizeInSecs();
    private ExecutorService producerExecutor;
    private ExecutorService consumerExecutor;
    private int totalResults;
    private int totalPages;
    private int totalFiles;

    public ApiDownloader(
            FeedType feedType,
            File outDir,
            String outFile,
            boolean deleteTempDir,
            boolean compress,
            int maxResultsPerPage,
            IApiDownloadUriConsumer consumer,
            IApiDownloadUriProducer producer) {
        super(feedType, outDir, maxResultsPerPage, outFile, deleteTempDir, compress);
        this.consumer = consumer;
        this.producer = producer;
    }

    @Override
    public void download(CountDownLatch latch) throws NvdException {
        logger.info(
                "starting download processor with {} producers and {} consumers, max requests/rate limit {}, rolling window size {} secs",
                numberOfProducers,
                numberOfConsumers,
                HttpUtil.getRateLimit(),
                rollingWindowInSecs);

        producerExecutor = Executors.newFixedThreadPool(numberOfProducers);
        startProducers(producerExecutor);
        logger.info("download creator finished");

        this.totalResults = producer.getTotalResults();
        this.totalPages = producer.getTotalPages();
        this.totalFiles = producer.getTotalFiles();

        consumerExecutor = Executors.newFixedThreadPool(numberOfConsumers);
        startConsumers(consumerExecutor, numberOfConsumers);
        logger.info("download processor worker finished");

        latch.countDown();
    }

    @Override
    public void generateOutputFile(String collectionNodeName) throws NvdException {
        File[] files = Util.getFiles(getOutDir(), getOutFilePrefix(getFeedType()));
        if (files.length == 0) {
            logger.warn("no files to merge");
        }

        if (files.length != totalFiles) {
            logger.warn("number of files to merge does not match number of pages");
        }

        Arrays.sort(files, Comparator.comparing(File::getName));

        // create output file above the output directory
        File outFile = new File(getOutDir().getParent(), getOutFile());
        int actualResults = Util.mergeFiles(files, outFile, collectionNodeName);
        if (actualResults != totalResults) {
            logger.warn("total results {} does not match expected count {}", actualResults, totalResults);
        }

        if (isCompress()) {
            Util.compressFile(outFile, ArchiveType.ZIP);
            logger.info("output file compressed");
        }
    }

    @Override
    public void deleteTempDir() {
        if (isDeleteTempDir()) {
            Util.deleteDir(getOutDir());
            logger.info("temporary directory {} deleted", getOutDir());
        }
    }

    private void startProducers(ExecutorService executorService) throws NvdException {
        producer.generateUris(executorService, Constants.DEFAULT_NUMBER_OF_PRODUCERS);
        Util.waitToFinish(executorService, Util.getProducerWaitTimeToFinishInMinutes(), TimeUnit.MINUTES);
    }

    private void startConsumers(ExecutorService executorService, int maxThreads) throws NvdException {
        logger.info("waiting for consumer tasks to finish...");
        consumer.downloadUris(executorService, maxThreads, Constants.DEFAULT_DELAY_BETWEEN_REQUESTS_IN_MS);
        Util.waitToFinish(executorService, Util.getConsumerWaitTimeToFinishInMinutes(), TimeUnit.MINUTES);
    }
}
