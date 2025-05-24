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

import com.github.phanikb.nvd.cli.processor.api.IApiDownloadUriProducer;
import com.github.phanikb.nvd.common.NvdException;
import com.github.phanikb.nvd.common.QueueElement;
import com.github.phanikb.nvd.enums.FeedType;

import static com.github.phanikb.nvd.cli.processor.api.download.NvdHttpClientResponseHandler.getResponseHandler;

public class StartIndexProducer extends StartIndexProcessor<Integer> implements IApiDownloadUriProducer {
    private final ProducerHelper producerHelper;

    public StartIndexProducer(
            FeedType type,
            int poison,
            int poisonPerCreator,
            int maxResultsPerPage,
            String endpoint,
            Path outDir,
            String outFilePrefix,
            List<NameValuePair> queryParams,
            BlockingDeque<QueueElement> downloadQueue) {
        super(type, poison, poisonPerCreator, maxResultsPerPage, endpoint, outDir, outFilePrefix, downloadQueue);
        this.producerHelper = new ProducerHelper(type, this::calculateTotalResults, queryParams);
    }

    public StartIndexProducer(
            FeedType type,
            int poison,
            int poisonPerCreator,
            int maxResultsPerPage,
            String endpoint,
            Path outDir,
            String outFilePrefix,
            BlockingDeque<QueueElement> downloadQueue,
            ProducerHelper producerHelper) {
        super(type, poison, poisonPerCreator, maxResultsPerPage, endpoint, outDir, outFilePrefix, downloadQueue);
        this.producerHelper = producerHelper;
    }

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
            builder.removeParameter("startIndex");
            builder.addParameter("startIndex", String.valueOf(startIndex));
            return builder.build();
        } catch (URISyntaxException e) {
            throw new NvdException("failed to build URI", e);
        }
    }

    @Override
    public void generateUris(ExecutorService executorService, int numberOfProducers) {
        for (int i = 0; i < numberOfProducers; i++) {
            executorService.submit(new StartIndexProducer(
                    feedType,
                    poison,
                    poisonPerCreator,
                    maxResultsPerPage,
                    endpoint,
                    outDir,
                    outFilePrefix,
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
    public int getTotalFiles() throws NvdException {
        return producerHelper.getTotalPages(getMaxResultsPerPage());
    }

    @Override
    public int calculateTotalResults() {
        try {
            URI uri = generateTotalResultsUri();
            return getResults(uri, getResponseHandler(feedType));
        } catch (NvdException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public URI generateTotalResultsUri() throws NvdException {
        try {
            URIBuilder builder = new URIBuilder(endpoint);
            builder.addParameters(producerHelper.getQueryParams());
            builder.removeParameter("startIndex");
            builder.addParameter("startIndex", "0");
            builder.removeParameter("resultsPerPage");
            builder.addParameter("resultsPerPage", "1");
            return builder.build();
        } catch (URISyntaxException e) {
            throw new NvdException("failed to build URI", e);
        }
    }
}
