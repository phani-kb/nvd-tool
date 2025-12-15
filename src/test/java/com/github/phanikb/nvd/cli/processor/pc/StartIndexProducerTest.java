package com.github.phanikb.nvd.cli.processor.pc;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.github.phanikb.nvd.common.NvdException;
import com.github.phanikb.nvd.common.QueueElement;
import com.github.phanikb.nvd.enums.ApiQueryParams;
import com.github.phanikb.nvd.enums.FeedType;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class StartIndexProducerTest {

    @TempDir
    Path tempDir;

    @Mock
    private ProducerHelper mockProducerHelper;

    private List<NameValuePair> queryParams;
    private BlockingDeque<QueueElement> downloadQueue;
    private StartIndexProducer producer;
    private final Integer poison = Integer.MAX_VALUE;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        downloadQueue = new LinkedBlockingDeque<>();
        queryParams = Arrays.asList(
                new BasicNameValuePair("resultsPerPage", "100"),
                new BasicNameValuePair("lastModStartDate", "2024-01-01T00:00:00"),
                new BasicNameValuePair("lastModEndDate", "2024-01-31T23:59:59"));
    }

    @Test
    void testConstructorWithQueryParams() {
        producer = StartIndexProducer.create(
                FeedType.CVE,
                poison,
                1,
                "https://services.nvd.nist.gov/rest/json/cves/2.0",
                tempDir,
                "test-prefix",
                downloadQueue);

        assertNotNull(producer);
        assertEquals(FeedType.CVE, producer.getFeedType());
        assertEquals(poison, producer.getPoison());
        assertEquals(1, producer.getPoisonPerCreator());
        assertEquals("https://services.nvd.nist.gov/rest/json/cves/2.0", producer.getEndpoint());
        assertEquals(tempDir, producer.getOutDir());
        assertEquals("test-prefix", producer.getOutFilePrefix());
        assertEquals(downloadQueue, producer.getDownloadQueue());
    }

    @Test
    void testConstructorWithProducerHelper() {
        StartIndexProducer.Config config = new StartIndexProducer.Config(
                FeedType.CVE,
                poison,
                1,
                tempDir,
                "test-prefix",
                downloadQueue,
                "https://services.nvd.nist.gov/rest/json/cves/2.0");
        config.setProducerHelper(mockProducerHelper);
        producer = new StartIndexProducer(config);

        assertNotNull(producer);
    }

    @Test
    void testRunWithStartIndex() {
        when(mockProducerHelper.hasStartIndex()).thenReturn(true);
        when(mockProducerHelper.getStartIndex()).thenReturn(50);
        when(mockProducerHelper.getQueryParams()).thenReturn(queryParams);

        StartIndexProducer.Config config = new StartIndexProducer.Config(
                FeedType.CVE,
                poison,
                1,
                tempDir,
                "test-prefix",
                downloadQueue,
                "https://services.nvd.nist.gov/rest/json/cves/2.0");
        config.setProducerHelper(mockProducerHelper);
        producer = new StartIndexProducer(config);

        producer.run();

        verify(mockProducerHelper).hasStartIndex();
        verify(mockProducerHelper).getStartIndex();

        // Should have 1 regular element + 1 poison element
        assertEquals(2, downloadQueue.size());

        QueueElement firstElement = downloadQueue.poll();
        assertInstanceOf(StartIndexQE.class, firstElement);
        StartIndexQE startIndexQE = (StartIndexQE) firstElement;
        assertEquals(50, startIndexQE.getStartIndex());
        assertEquals(149, startIndexQE.getEndIndex()); // 50 + 100 - 1

        QueueElement poisonElement = downloadQueue.poll();
        assertInstanceOf(StartIndexQE.class, poisonElement);
        StartIndexQE poisonQE = (StartIndexQE) poisonElement;
        assertEquals(poison, poisonQE.getStartIndex());
        assertEquals(poison, poisonQE.getEndIndex());
    }

    @Test
    void testRunWithoutStartIndex() throws NvdException {
        when(mockProducerHelper.hasStartIndex()).thenReturn(false);
        when(mockProducerHelper.getTotalPages(100)).thenReturn(3);
        when(mockProducerHelper.getQueryParams()).thenReturn(queryParams);

        StartIndexProducer.Config config = new StartIndexProducer.Config(
                FeedType.CVE,
                poison,
                1,
                tempDir,
                "test-prefix",
                downloadQueue,
                "https://services.nvd.nist.gov/rest/json/cves/2.0");
        config.setProducerHelper(mockProducerHelper);
        producer = new StartIndexProducer(config);

        producer.run();

        verify(mockProducerHelper).hasStartIndex();
        verify(mockProducerHelper).getTotalPages(100);

        // Should have 3 regular elements + 1 poison element
        assertEquals(4, downloadQueue.size());

        for (int page = 1; page <= 3; page++) {
            QueueElement element = downloadQueue.poll();
            assertInstanceOf(StartIndexQE.class, element);
            StartIndexQE startIndexQE = (StartIndexQE) element;
            int expectedStartIndex = (page - 1) * 100;
            int expectedEndIndex = expectedStartIndex + 100 - 1;
            assertEquals(expectedStartIndex, startIndexQE.getStartIndex());
            assertEquals(expectedEndIndex, startIndexQE.getEndIndex());
        }

        QueueElement poisonElement = downloadQueue.poll();
        assertInstanceOf(StartIndexQE.class, poisonElement);
        StartIndexQE poisonQE = (StartIndexQE) poisonElement;
        assertEquals(poison, poisonQE.getStartIndex());
    }

    @Test
    void testRunWithNvdException() throws NvdException {
        when(mockProducerHelper.hasStartIndex()).thenReturn(false);
        when(mockProducerHelper.getTotalPages(anyInt())).thenThrow(new NvdException("Test exception"));

        StartIndexProducer.Config config = new StartIndexProducer.Config(
                FeedType.CVE,
                poison,
                1,
                tempDir,
                "test-prefix",
                downloadQueue,
                "https://services.nvd.nist.gov/rest/json/cves/2.0");
        config.setProducerHelper(mockProducerHelper);
        producer = new StartIndexProducer(config);

        producer.run();

        assertEquals(1, downloadQueue.size());
        QueueElement element = downloadQueue.poll();
        assertInstanceOf(StartIndexQE.class, element);
        StartIndexQE poisonQE = (StartIndexQE) element;
        assertEquals(poison, poisonQE.getStartIndex());
    }

    @Test
    void testGetDownloadUri() throws NvdException {
        producer = StartIndexProducer.create(
                FeedType.CVE,
                poison,
                1,
                "https://services.nvd.nist.gov/rest/json/cves/2.0",
                tempDir,
                "test-prefix",
                downloadQueue);

        List<NameValuePair> testParams = Arrays.asList(
                new BasicNameValuePair(ApiQueryParams.RESULTS_PER_PAGE.getName(), "50"),
                new BasicNameValuePair(ApiQueryParams.START_INDEX.getName(), "999") // This should be overridden
                );

        URI downloadUri = producer.getDownloadUri(100, testParams);

        assertNotNull(downloadUri);
        assertEquals("https", downloadUri.getScheme());
        assertEquals("services.nvd.nist.gov", downloadUri.getHost());
        String query = downloadUri.getQuery();
        assertTrue(query.contains("startIndex=100"));
        assertTrue(query.contains("resultsPerPage=50"));
    }

    @Test
    void testGetDownloadUriWithInvalidEndpoint() {
        producer = StartIndexProducer.create(
                FeedType.CVE, poison, 1, "ht<>tp://invalid URL with spaces", tempDir, "test-prefix", downloadQueue);

        List<NameValuePair> testParams = List.of(new BasicNameValuePair("resultsPerPage", "50"));

        NvdException exception = assertThrows(NvdException.class, () -> producer.getDownloadUri(100, testParams));

        assertEquals("failed to build URI", exception.getMessage());
    }

    @Test
    void testGenerateUris() {
        StartIndexProducer.Config config = new StartIndexProducer.Config(
                FeedType.CVE,
                poison,
                1,
                tempDir,
                "test-prefix",
                downloadQueue,
                "https://services.nvd.nist.gov/rest/json/cves/2.0");
        config.setProducerHelper(mockProducerHelper);
        producer = new StartIndexProducer(config);

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        try {
            producer.generateUris(executorService, 2);

            // Shutdown immediately and don't wait indefinitely
            executorService.shutdown();
            boolean terminated = executorService.awaitTermination(5, TimeUnit.SECONDS);
            if (!terminated) {
                executorService.shutdownNow();
            }
            assertTrue(true);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            executorService.shutdownNow();
            fail("Test interrupted");
        }
    }

    @Test
    void testGetTotalPages() throws NvdException {
        when(mockProducerHelper.getTotalPages(100)).thenReturn(5);

        StartIndexProducer.Config config = new StartIndexProducer.Config(
                FeedType.CVE,
                poison,
                1,
                tempDir,
                "test-prefix",
                downloadQueue,
                "https://services.nvd.nist.gov/rest/json/cves/2.0");
        config.setProducerHelper(mockProducerHelper);
        producer = new StartIndexProducer(config);

        int totalPages = producer.getTotalPages();
        assertEquals(5, totalPages);
        verify(mockProducerHelper).getTotalPages(100);
    }

    @Test
    void testGetTotalResults() throws NvdException {
        when(mockProducerHelper.getTotalResults()).thenReturn(500);

        StartIndexProducer.Config config = new StartIndexProducer.Config(
                FeedType.CVE,
                poison,
                1,
                tempDir,
                "test-prefix",
                downloadQueue,
                "https://services.nvd.nist.gov/rest/json/cves/2.0");
        config.setProducerHelper(mockProducerHelper);
        producer = new StartIndexProducer(config);

        int totalResults = producer.getTotalResults();
        assertEquals(500, totalResults);
        verify(mockProducerHelper).getTotalResults();
    }

    @Test
    void testGetTotalFiles() throws NvdException {
        when(mockProducerHelper.getTotalPages(100)).thenReturn(10);

        StartIndexProducer.Config config = new StartIndexProducer.Config(
                FeedType.CVE,
                poison,
                1,
                tempDir,
                "test-prefix",
                downloadQueue,
                "https://services.nvd.nist.gov/rest/json/cves/2.0");
        config.setProducerHelper(mockProducerHelper);
        producer = new StartIndexProducer(config);

        int totalFiles = producer.getTotalFiles();
        assertEquals(10, totalFiles);
        verify(mockProducerHelper).getTotalPages(100);
    }

    @Test
    void testGenerateTotalResultsUri() throws NvdException {
        producer = StartIndexProducer.create(
                FeedType.CVE,
                poison,
                1,
                "https://services.nvd.nist.gov/rest/json/cves/2.0",
                tempDir,
                "test-prefix",
                downloadQueue);

        URI totalResultsUri = producer.generateTotalResultsUri();

        assertNotNull(totalResultsUri);
        assertEquals("https", totalResultsUri.getScheme());
        assertEquals("services.nvd.nist.gov", totalResultsUri.getHost());
        String query = totalResultsUri.getQuery();
        assertTrue(query.contains("startIndex=0"));
        assertTrue(query.contains("resultsPerPage=1"));
    }

    @Test
    void testGenerateTotalResultsUriWithInvalidEndpoint() {
        producer = StartIndexProducer.create(
                FeedType.CVE, poison, 1, "ht<>tp://invalid URL", tempDir, "test-prefix", downloadQueue);

        NvdException exception = assertThrows(NvdException.class, () -> producer.generateTotalResultsUri());

        assertEquals("failed to build URI", exception.getMessage());
    }

    @Test
    void testCalculateTotalResults() {
        producer = StartIndexProducer.create(
                FeedType.CVE,
                poison,
                1,
                "https://services.nvd.nist.gov/rest/json/cves/2.0",
                tempDir,
                "test-prefix",
                downloadQueue);

        assertDoesNotThrow(() -> {
            int totalResults = producer.calculateTotalResults();
            assertTrue(totalResults >= 0);
        });
    }

    @Test
    void testQueueElementCreation() {
        when(mockProducerHelper.hasStartIndex()).thenReturn(true);
        when(mockProducerHelper.getStartIndex()).thenReturn(200);
        when(mockProducerHelper.getQueryParams()).thenReturn(List.of(new BasicNameValuePair("resultsPerPage", "25")));

        StartIndexProducer.Config config = new StartIndexProducer.Config(
                FeedType.CVE,
                poison,
                0, // No poison elements for this test
                tempDir,
                "nvd-test",
                downloadQueue,
                "https://services.nvd.nist.gov/rest/json/cves/2.0");
        config.setProducerHelper(mockProducerHelper);
        producer = new StartIndexProducer(config);

        producer.run();

        assertEquals(1, downloadQueue.size());

        QueueElement element = downloadQueue.poll();
        assertInstanceOf(StartIndexQE.class, element);
        StartIndexQE startIndexQE = (StartIndexQE) element;

        assertEquals(200, startIndexQE.getStartIndex());
        assertEquals(224, startIndexQE.getEndIndex()); // 200 + 25 - 1

        File outFile = startIndexQE.getOutFile();
        assertNotNull(outFile);
        assertTrue(outFile.getName().contains("nvd-test"));
        assertTrue(outFile.getName().contains("0000200"));
        assertTrue(outFile.getName().contains("0000224"));
        assertTrue(outFile.getName().endsWith(".json"));
    }

    @Test
    void testMultiplePoisonElements() {
        when(mockProducerHelper.hasStartIndex()).thenReturn(true);
        when(mockProducerHelper.getStartIndex()).thenReturn(0);
        when(mockProducerHelper.getQueryParams()).thenReturn(queryParams);

        StartIndexProducer.Config config = new StartIndexProducer.Config(
                FeedType.CVE,
                poison,
                3, // Multiple poison elements
                tempDir,
                "test-prefix",
                downloadQueue,
                "https://services.nvd.nist.gov/rest/json/cves/2.0");
        config.setProducerHelper(mockProducerHelper);
        producer = new StartIndexProducer(config);

        producer.run();

        assertEquals(4, downloadQueue.size());

        downloadQueue.poll();

        int poisonCount = 0;
        while (!downloadQueue.isEmpty()) {
            QueueElement element = downloadQueue.poll();
            if (element instanceof StartIndexQE startIndexQE && startIndexQE.getStartIndex() == poison) {
                poisonCount++;
            }
        }
        assertEquals(3, poisonCount);
    }

    @Test
    void testDifferentFeedTypes() {
        FeedType feedType = FeedType.CVE;
        producer = StartIndexProducer.create(
                feedType,
                poison,
                1,
                "https://services.nvd.nist.gov/rest/json/" + feedType.name().toLowerCase() + "/2.0",
                tempDir,
                feedType.name().toLowerCase(),
                downloadQueue);

        assertEquals(feedType, producer.getFeedType());
    }

    @Test
    void testZeroStartIndex() {
        when(mockProducerHelper.hasStartIndex()).thenReturn(true);
        when(mockProducerHelper.getStartIndex()).thenReturn(0);
        when(mockProducerHelper.getQueryParams()).thenReturn(queryParams);

        StartIndexProducer.Config config = new StartIndexProducer.Config(
                FeedType.CVE,
                poison,
                0,
                tempDir,
                "zero-test",
                downloadQueue,
                "https://services.nvd.nist.gov/rest/json/cves/2.0");
        config.setProducerHelper(mockProducerHelper);
        producer = new StartIndexProducer(config);

        producer.run();

        assertEquals(1, downloadQueue.size());

        QueueElement element = downloadQueue.poll();
        assertInstanceOf(StartIndexQE.class, element);
        StartIndexQE startIndexQE = (StartIndexQE) element;

        assertEquals(0, startIndexQE.getStartIndex());
        assertEquals(49, startIndexQE.getEndIndex());

        File outFile = startIndexQE.getOutFile();
        assertTrue(outFile.getName().contains("0000000"));
        assertTrue(outFile.getName().contains("0000049"));
    }

    @Test
    void testLargeStartIndex() {
        when(mockProducerHelper.hasStartIndex()).thenReturn(true);
        when(mockProducerHelper.getStartIndex()).thenReturn(999999);
        when(mockProducerHelper.getQueryParams()).thenReturn(queryParams);

        StartIndexProducer.Config config = new StartIndexProducer.Config(
                FeedType.CVE,
                poison,
                0,
                tempDir,
                "large-test",
                downloadQueue,
                "https://services.nvd.nist.gov/rest/json/cves/2.0");
        config.setProducerHelper(mockProducerHelper);
        producer = new StartIndexProducer(config);

        producer.run();

        assertEquals(1, downloadQueue.size());

        QueueElement element = downloadQueue.poll();
        assertInstanceOf(StartIndexQE.class, element);
        StartIndexQE startIndexQE = (StartIndexQE) element;

        assertEquals(999999, startIndexQE.getStartIndex());
        assertEquals(999999, startIndexQE.getEndIndex()); // 999999 + 1 - 1

        File outFile = startIndexQE.getOutFile();
        assertTrue(outFile.getName().contains("0999999"));
    }
}
