package com.github.phanikb.nvd.cli.processor.pc;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutorService;

import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.net.URIBuilder;

import lombok.Getter;
import lombok.Setter;

import com.github.phanikb.nvd.cli.processor.api.IApiDownloadUriProducer;
import com.github.phanikb.nvd.common.NvdException;
import com.github.phanikb.nvd.common.QueueElement;
import com.github.phanikb.nvd.enums.ApiQueryParams;
import com.github.phanikb.nvd.enums.FeedType;

import static com.github.phanikb.nvd.cli.processor.api.download.NvdHttpClientResponseHandler.getResponseHandler;

public class StartIndexProducer extends StartIndexProcessor<Integer> implements IApiDownloadUriProducer {
    private ProducerHelper producerHelper;

    @Setter
    private int maxResultsPerPageOverride;

    public static class Config {
        public final FeedType type;
        public final int poison;
        public final int poisonPerCreator;
        public final Path outDir;
        public final String outFilePrefix;
        public final BlockingDeque<QueueElement> downloadQueue;

        @Getter
        public final String endpoint;

        @Setter
        @Getter
        private int maxResultsPerPage;

        @Setter
        @Getter
        private ProducerHelper producerHelper;

        public Config(
                FeedType type,
                int poison,
                int poisonPerCreator,
                Path outDir,
                String outFilePrefix,
                BlockingDeque<QueueElement> downloadQueue,
                String endpoint) {
            this.type = type;
            this.poison = poison;
            this.poisonPerCreator = poisonPerCreator;
            this.outDir = outDir;
            this.outFilePrefix = outFilePrefix;
            this.downloadQueue = downloadQueue;
            this.endpoint = endpoint;
            this.maxResultsPerPage = 0;
            this.producerHelper = null;
        }
    }

    public StartIndexProducer(Config config) {
        super(
                config.type,
                config.poison,
                config.poisonPerCreator,
                config.getMaxResultsPerPage(),
                config.endpoint,
                config.outDir,
                config.outFilePrefix,
                config.downloadQueue);
        this.producerHelper = config.getProducerHelper();
    }

    public static StartIndexProducer create(
            FeedType type,
            int poison,
            int poisonPerCreator,
            String endpoint,
            Path outDir,
            String outFilePrefix,
            BlockingDeque<QueueElement> downloadQueue) {
        Config config = new Config(type, poison, poisonPerCreator, outDir, outFilePrefix, downloadQueue, endpoint);
        StartIndexProducer producer = new StartIndexProducer(config);
        ProducerHelper helper = new ProducerHelper(type, producer::calculateTotalResults, List.of());
        producer.producerHelper = helper;
        config.setProducerHelper(helper);
        return producer;
    }

    @Override
    public int getMaxResultsPerPage() {
        return maxResultsPerPageOverride != 0 ? maxResultsPerPageOverride : super.getMaxResultsPerPage();
    }

    @Override
    public void setMaxResultsPerPage(int maxResultsPerPage) {
        this.maxResultsPerPageOverride = maxResultsPerPage;
    }

    @Override
    public void run() {
        try {
            int resultsPerPage = getMaxResultsPerPage();
            downloadQueue.clear();

            if (producerHelper.hasStartIndex()) {
                int startIndex = producerHelper.getStartIndex();
                int endIndex = startIndex + resultsPerPage - 1;
                addQueueElement(startIndex, endIndex);
            } else {
                int totalPages = producerHelper.getTotalPages(resultsPerPage);
                for (int page = 1; page <= totalPages; page++) {
                    int startIndex = (page - 1) * resultsPerPage;
                    int endIndex = startIndex + resultsPerPage - 1;
                    addQueueElement(startIndex, endIndex);
                }
            }
        } catch (InterruptedException | NvdException e) {
            logger.error(e.getMessage());
            Thread.currentThread().interrupt();
        } finally {
            addPoisonElements();
        }
    }

    private void addQueueElement(int startIndex, int endIndex) throws NvdException, InterruptedException {
        URI uri = getDownloadUri(startIndex, producerHelper.getQueryParams());
        File outFile = getDownloadFile(startIndex, endIndex, outDir);
        QueueElement element = new StartIndexQE(uri, startIndex, endIndex, outFile);
        logger.trace("adding element {}, queue size {} uri: {}", element, downloadQueue.size(), uri);
        downloadQueue.put(element);
    }

    private void addPoisonElements() {
        for (int i = 0; i < poisonPerCreator; i++) {
            try {
                downloadQueue.put(new StartIndexQE(null, poison, poison, null));
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }

    protected URI getDownloadUri(long startIndex, List<NameValuePair> queryParams) throws NvdException {
        try {
            URIBuilder builder = new URIBuilder(endpoint);
            builder.addParameters(queryParams);
            builder.removeParameter(ApiQueryParams.START_INDEX.getName());
            builder.addParameter(ApiQueryParams.START_INDEX.getName(), String.valueOf(startIndex));
            return builder.build();
        } catch (URISyntaxException e) {
            throw new NvdException("failed to build URI", e);
        }
    }

    @Override
    public void generateUris(ExecutorService executorService, int numberOfProducers) {
        for (int i = 0; i < numberOfProducers; i++) {
            Config config =
                    new Config(feedType, poison, poisonPerCreator, outDir, outFilePrefix, downloadQueue, endpoint);
            config.setMaxResultsPerPage(maxResultsPerPage);
            config.setProducerHelper(producerHelper);
            executorService.submit(new StartIndexProducer(config));
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
    public int getTotalFiles() throws NvdException {
        return producerHelper.getTotalPages(getMaxResultsPerPage());
    }

    @Override
    public int calculateTotalResults() throws NvdException {
        try {
            URI uri = generateTotalResultsUri();
            return getResults(uri, getResponseHandler(feedType));
        } catch (NvdException e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    public URI generateTotalResultsUri() throws NvdException {
        try {
            URIBuilder builder = new URIBuilder(endpoint);
            builder.addParameters(producerHelper.getQueryParams());
            builder.removeParameter(ApiQueryParams.START_INDEX.getName());
            builder.addParameter(ApiQueryParams.START_INDEX.getName(), "0");
            builder.removeParameter(ApiQueryParams.RESULTS_PER_PAGE.getName());
            builder.addParameter(ApiQueryParams.RESULTS_PER_PAGE.getName(), "1");
            return builder.build();
        } catch (URISyntaxException e) {
            throw new NvdException("failed to build URI", e);
        }
    }
}
