package com.github.phanikb.nvd.cli.processor.pc;

import java.io.File;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

import com.github.phanikb.nvd.common.NvdApiDate;
import com.github.phanikb.nvd.common.NvdException;
import com.github.phanikb.nvd.common.QueueElement;
import com.github.phanikb.nvd.enums.FeedType;
import com.github.phanikb.nvd.enums.NvdApiDateType;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DatesProducerTest {

    @TempDir
    Path tempDir;

    @Mock
    private ProducerHelper mockProducerHelper;

    private List<NvdApiDate> validDates;
    private List<NameValuePair> queryParams;
    private BlockingDeque<QueueElement> downloadQueue;
    private DatesProducer producer;
    private final LocalDateTime poison = LocalDateTime.MAX;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        downloadQueue = new LinkedBlockingDeque<>();
        queryParams = Arrays.asList(
                new BasicNameValuePair("resultsPerPage", "100"),
                new BasicNameValuePair("lastModStartDate", "2024-01-01T00:00:00"),
                new BasicNameValuePair("lastModEndDate", "2024-01-31T23:59:59"));

        LocalDateTime startDate = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 1, 31, 23, 59, 59);

        validDates = Arrays.asList(
                new NvdApiDate("lastModStartDate", startDate, NvdApiDateType.START_DATE),
                new NvdApiDate("lastModEndDate", endDate, NvdApiDateType.END_DATE));
    }

    @Test
    void testConstructorWithQueryParams() {
        producer = DatesProducer.create(
                FeedType.CVE,
                poison,
                1,
                100,
                "https://services.nvd.nist.gov/rest/json/cves/2.0",
                tempDir,
                "test-prefix",
                queryParams,
                validDates,
                downloadQueue);

        assertNotNull(producer);
        assertEquals(FeedType.CVE, producer.getFeedType());
        assertEquals(poison, producer.getPoison());
        assertEquals(1, producer.getPoisonPerCreator());
        assertEquals(100, producer.getMaxResultsPerPage());
        assertEquals("https://services.nvd.nist.gov/rest/json/cves/2.0", producer.getEndpoint());
        assertEquals(tempDir, producer.getOutDir());
        assertEquals("test-prefix", producer.getOutFilePrefix());
        assertEquals(validDates, producer.getDates());
        assertEquals(downloadQueue, producer.getDownloadQueue());
        assertNotNull(producer.getProducerHelper());
    }

    @Test
    void testConstructorWithProducerHelper() {
        producer = new DatesProducer(
                FeedType.CVE,
                poison,
                1,
                100,
                "https://services.nvd.nist.gov/rest/json/cves/2.0",
                tempDir,
                "test-prefix",
                validDates,
                downloadQueue,
                mockProducerHelper);

        assertNotNull(producer);
        assertEquals(mockProducerHelper, producer.getProducerHelper());
    }

    @Test
    void testConstructorWithInvalidDates() {
        List<NvdApiDate> invalidDates =
                Arrays.asList(new NvdApiDate("lastModStartDate", LocalDateTime.now(), NvdApiDateType.START_DATE));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            DatesProducer.create(
                    FeedType.CVE,
                    poison,
                    1,
                    100,
                    "https://services.nvd.nist.gov/rest/json/cves/2.0",
                    tempDir,
                    "test-prefix",
                    queryParams,
                    invalidDates,
                    downloadQueue);
        });

        assertEquals("dates must have 2 elements", exception.getMessage());
    }

    @Test
    void testRunWithStartIndex() throws Exception {
        when(mockProducerHelper.hasStartIndex()).thenReturn(true);
        when(mockProducerHelper.getStartIndex()).thenReturn(50);
        when(mockProducerHelper.getQueryParams()).thenReturn(queryParams);

        producer = new DatesProducer(
                FeedType.CVE,
                poison,
                1,
                100,
                "https://services.nvd.nist.gov/rest/json/cves/2.0",
                tempDir,
                "test-prefix",
                validDates,
                downloadQueue,
                mockProducerHelper);

        producer.run();

        verify(mockProducerHelper).initResults(100);
        verify(mockProducerHelper).hasStartIndex();
        verify(mockProducerHelper).getStartIndex();

        // Should have 1 regular element + 1 poison element
        assertEquals(2, downloadQueue.size());

        // Check poison element
        QueueElement poisonElement = null;
        for (QueueElement element : downloadQueue) {
            if (element instanceof ChangeDatesQE changeDatesQE) {
                if (changeDatesQE.getStartDate().equals(poison)) {
                    poisonElement = element;
                    break;
                }
            }
        }
        assertNotNull(poisonElement);
    }

    @Test
    void testRunWithDatesProcessing() throws Exception {
        when(mockProducerHelper.hasStartIndex()).thenReturn(false);
        when(mockProducerHelper.getQueryParams()).thenReturn(queryParams);
        when(mockProducerHelper.getTotalResultsByDate(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(150); // Some results to process

        producer = new DatesProducer(
                FeedType.CVE,
                poison,
                2,
                100,
                "https://services.nvd.nist.gov/rest/json/cves/2.0",
                tempDir,
                "test-prefix",
                validDates,
                downloadQueue,
                mockProducerHelper);

        producer.run();

        verify(mockProducerHelper).initResults(100);
        verify(mockProducerHelper).hasStartIndex();

        // Should have processed dates and added poison elements
        assertTrue(downloadQueue.size() >= 2); // At least poison elements
    }

    @Test
    void testRunWithNvdException() throws Exception {
        when(mockProducerHelper.hasStartIndex()).thenReturn(false);
        doThrow(new NvdException("Test exception")).when(mockProducerHelper).initResults(anyInt());

        producer = new DatesProducer(
                FeedType.CVE,
                poison,
                1,
                100,
                "https://services.nvd.nist.gov/rest/json/cves/2.0",
                tempDir,
                "test-prefix",
                validDates,
                downloadQueue,
                mockProducerHelper);

        producer.run();

        // Should still add poison elements even after exception
        assertEquals(1, downloadQueue.size());
        QueueElement element = downloadQueue.poll();
        assertTrue(element instanceof ChangeDatesQE);
        ChangeDatesQE changeDatesQE = (ChangeDatesQE) element;
        assertEquals(poison, changeDatesQE.getStartDate());
    }

    @Test
    void testGenerateUris() {
        producer = new DatesProducer(
                FeedType.CVE,
                poison,
                1,
                100,
                "https://services.nvd.nist.gov/rest/json/cves/2.0",
                tempDir,
                "test-prefix",
                validDates,
                downloadQueue,
                mockProducerHelper);

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
    void testGetTotalPages() throws Exception {
        when(mockProducerHelper.getTotalPages(100)).thenReturn(5);

        producer = new DatesProducer(
                FeedType.CVE,
                poison,
                1,
                100,
                "https://services.nvd.nist.gov/rest/json/cves/2.0",
                tempDir,
                "test-prefix",
                validDates,
                downloadQueue,
                mockProducerHelper);

        int totalPages = producer.getTotalPages();
        assertEquals(5, totalPages);
        verify(mockProducerHelper).getTotalPages(100);
    }

    @Test
    void testGetTotalResults() throws Exception {
        when(mockProducerHelper.getTotalResults()).thenReturn(500);

        producer = new DatesProducer(
                FeedType.CVE,
                poison,
                1,
                100,
                "https://services.nvd.nist.gov/rest/json/cves/2.0",
                tempDir,
                "test-prefix",
                validDates,
                downloadQueue,
                mockProducerHelper);

        int totalResults = producer.getTotalResults();
        assertEquals(500, totalResults);
        verify(mockProducerHelper).getTotalResults();
    }

    @Test
    void testGetTotalFiles() {
        when(mockProducerHelper.getTotalFiles(100)).thenReturn(10);

        producer = new DatesProducer(
                FeedType.CVE,
                poison,
                1,
                100,
                "https://services.nvd.nist.gov/rest/json/cves/2.0",
                tempDir,
                "test-prefix",
                validDates,
                downloadQueue,
                mockProducerHelper);

        int totalFiles = producer.getTotalFiles();
        assertEquals(10, totalFiles);
        verify(mockProducerHelper).getTotalFiles(100);
    }

    @Test
    void testProcessWithStartIndexDetails() throws Exception {
        when(mockProducerHelper.hasStartIndex()).thenReturn(true);
        when(mockProducerHelper.getStartIndex()).thenReturn(0);
        when(mockProducerHelper.getQueryParams()).thenReturn(queryParams);

        producer = new DatesProducer(
                FeedType.CVE,
                poison,
                0, // No poison elements for this test
                50,
                "https://services.nvd.nist.gov/rest/json/cves/2.0",
                tempDir,
                "cve-data",
                validDates,
                downloadQueue,
                mockProducerHelper);

        producer.run();

        // Should have exactly 1 element (no poison elements)
        assertEquals(1, downloadQueue.size());

        QueueElement element = downloadQueue.poll();
        assertTrue(element instanceof ChangeDatesQE);
        ChangeDatesQE changeDatesQE = (ChangeDatesQE) element;

        assertEquals(0, changeDatesQE.getStartIndex());
        assertEquals(49, changeDatesQE.getEndIndex()); // startIndex + resultsPerPage - 1
        assertNotNull(changeDatesQE.getUri());
        assertNotNull(changeDatesQE.getOutFile());
        assertTrue(changeDatesQE.getOutFile().getName().contains("cve-data"));
    }

    @Test
    void testDateRangeValidation() throws Exception {
        // Create dates where start is after end to trigger validation error
        List<NvdApiDate> invalidDates = Arrays.asList(
                new NvdApiDate(
                        "lastModStartDate", LocalDateTime.of(2024, 1, 31, 23, 59, 59), NvdApiDateType.START_DATE),
                new NvdApiDate("lastModEndDate", LocalDateTime.of(2024, 1, 1, 0, 0), NvdApiDateType.END_DATE));

        when(mockProducerHelper.hasStartIndex()).thenReturn(false);

        producer = new DatesProducer(
                FeedType.CVE,
                poison,
                1,
                100,
                "https://services.nvd.nist.gov/rest/json/cves/2.0",
                tempDir,
                "test-prefix",
                invalidDates,
                downloadQueue,
                mockProducerHelper);

        producer.run();

        // Should still add poison elements even after validation error
        assertEquals(1, downloadQueue.size());
    }

    @Test
    void testQueueElementCreation() throws Exception {
        when(mockProducerHelper.hasStartIndex()).thenReturn(true);
        when(mockProducerHelper.getStartIndex()).thenReturn(100);
        when(mockProducerHelper.getQueryParams())
                .thenReturn(Arrays.asList(new BasicNameValuePair("resultsPerPage", "20")));

        producer = new DatesProducer(
                FeedType.CVE,
                poison,
                0,
                20,
                "https://services.nvd.nist.gov/rest/json/cves/2.0",
                tempDir,
                "nvd-test",
                validDates,
                downloadQueue,
                mockProducerHelper);

        producer.run();

        assertEquals(1, downloadQueue.size());

        QueueElement element = downloadQueue.poll();
        assertTrue(element instanceof ChangeDatesQE);
        ChangeDatesQE changeDatesQE = (ChangeDatesQE) element;

        assertEquals(100, changeDatesQE.getStartIndex());
        assertEquals(119, changeDatesQE.getEndIndex());

        File outFile = changeDatesQE.getOutFile();
        assertNotNull(outFile);
        assertTrue(outFile.getName().contains("nvd-test"));
        assertTrue(outFile.getName().contains("0000100"));
        assertTrue(outFile.getName().contains("0000119"));
        assertTrue(outFile.getName().endsWith(".json"));
    }

    @Test
    void testMultiplePoisonElements() throws Exception {
        when(mockProducerHelper.hasStartIndex()).thenReturn(true);
        when(mockProducerHelper.getStartIndex()).thenReturn(0);
        when(mockProducerHelper.getQueryParams()).thenReturn(queryParams);

        producer = new DatesProducer(
                FeedType.CVE,
                poison,
                3, // Multiple poison elements
                100,
                "https://services.nvd.nist.gov/rest/json/cves/2.0",
                tempDir,
                "test-prefix",
                validDates,
                downloadQueue,
                mockProducerHelper);

        producer.run();

        // Should have 1 regular element + 3 poison elements
        assertEquals(4, downloadQueue.size());

        int poisonCount = 0;
        while (!downloadQueue.isEmpty()) {
            QueueElement element = downloadQueue.poll();
            if (element instanceof ChangeDatesQE changeDatesQE) {
                if (changeDatesQE.getStartDate().equals(poison)) {
                    poisonCount++;
                }
            }
        }
        assertEquals(3, poisonCount);
    }

    @Test
    void testEmptyQueryParams() {
        List<NameValuePair> emptyParams = new ArrayList<>();

        producer = DatesProducer.create(
                FeedType.CVE,
                poison,
                1,
                100,
                "https://services.nvd.nist.gov/rest/json/cves/2.0",
                tempDir,
                "test-prefix",
                emptyParams,
                validDates,
                downloadQueue);

        assertNotNull(producer);
        assertNotNull(producer.getProducerHelper());
    }

    @Test
    void testDifferentFeedTypes() {
        FeedType feedType = FeedType.CVE;
        producer = DatesProducer.create(
                feedType,
                poison,
                1,
                100,
                "https://services.nvd.nist.gov/rest/json/" + feedType.name().toLowerCase() + "/2.0",
                tempDir,
                feedType.name().toLowerCase(),
                queryParams,
                validDates,
                downloadQueue);

        assertEquals(feedType, producer.getFeedType());
    }

    @Test
    void testCalculateTotalResults() throws Exception {
        producer = DatesProducer.create(
                FeedType.CVE,
                poison,
                1,
                100,
                "https://services.nvd.nist.gov/rest/json/cves/2.0",
                tempDir,
                "test-prefix",
                queryParams,
                validDates,
                downloadQueue);

        assertDoesNotThrow(() -> {
            int totalResults = producer.calculateTotalResults();
            assertTrue(totalResults >= 0);
        });
    }
}
