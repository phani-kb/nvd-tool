package com.github.phanikb.nvd.cli.processor.pc;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.github.phanikb.nvd.common.NvdApiDate;
import com.github.phanikb.nvd.common.NvdException;
import com.github.phanikb.nvd.common.QueueElement;
import com.github.phanikb.nvd.enums.FeedType;
import com.github.phanikb.nvd.enums.NvdApiDateType;

import static org.junit.jupiter.api.Assertions.*;

class DatesProcessorTest {

    @TempDir
    Path tempDir;

    private List<NvdApiDate> validDates;
    private BlockingDeque<QueueElement> downloadQueue;
    private TestDatesProcessor processor;

    private static class TestDatesProcessor extends DatesProcessor<LocalDateTime> {

        public TestDatesProcessor(
                FeedType feedType,
                LocalDateTime poison,
                int poisonPerCreator,
                int maxResultsPerPage,
                String endpoint,
                Path outDir,
                String outFilePrefix,
                List<NvdApiDate> dates,
                BlockingDeque<QueueElement> downloadQueue) {
            super(
                    feedType,
                    poison,
                    poisonPerCreator,
                    maxResultsPerPage,
                    endpoint,
                    outDir,
                    outFilePrefix,
                    dates,
                    downloadQueue);
        }

        public TestDatesProcessor(
                FeedType feedType,
                LocalDateTime poison,
                Path outDir,
                String outFilePrefix,
                BlockingDeque<QueueElement> downloadQueue) {
            super(feedType, poison, outDir, outFilePrefix, downloadQueue);
        }

        @Override
        public void run() {}
    }

    @BeforeEach
    void setUp() {
        downloadQueue = new LinkedBlockingDeque<>();

        LocalDateTime startDate = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 1, 31, 23, 59, 59);

        validDates = Arrays.asList(
                new NvdApiDate("lastModStartDate", startDate, NvdApiDateType.START_DATE),
                new NvdApiDate("lastModEndDate", endDate, NvdApiDateType.END_DATE));
    }

    @Test
    void testConstructorWithValidDates() {
        processor = new TestDatesProcessor(
                FeedType.CVE,
                LocalDateTime.MAX,
                1,
                100,
                "https://services.nvd.nist.gov/rest/json/cves/2.0",
                tempDir,
                "test-prefix",
                validDates,
                downloadQueue);

        assertNotNull(processor);
        assertEquals(FeedType.CVE, processor.getFeedType());
        assertEquals(LocalDateTime.MAX, processor.getPoison());
        assertEquals(1, processor.getPoisonPerCreator());
        assertEquals(100, processor.getMaxResultsPerPage());
        assertEquals("https://services.nvd.nist.gov/rest/json/cves/2.0", processor.getEndpoint());
        assertEquals(tempDir, processor.getOutDir());
        assertEquals("test-prefix", processor.getOutFilePrefix());
        assertEquals(validDates, processor.getDates());
        assertEquals(downloadQueue, processor.getDownloadQueue());
    }

    @Test
    void testConstructorWithInvalidDatesSize() {
        List<NvdApiDate> invalidDates =
                Arrays.asList(new NvdApiDate("lastModStartDate", LocalDateTime.now(), NvdApiDateType.START_DATE));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new TestDatesProcessor(
                    FeedType.CVE,
                    LocalDateTime.MAX,
                    1,
                    100,
                    "https://services.nvd.nist.gov/rest/json/cves/2.0",
                    tempDir,
                    "test-prefix",
                    invalidDates,
                    downloadQueue);
        });

        assertEquals("dates must have 2 elements", exception.getMessage());
    }

    @Test
    void testConstructorWithThreeDates() {
        List<NvdApiDate> threeDates = Arrays.asList(
                new NvdApiDate("lastModStartDate", LocalDateTime.now(), NvdApiDateType.START_DATE),
                new NvdApiDate("lastModEndDate", LocalDateTime.now().plusDays(1), NvdApiDateType.END_DATE),
                new NvdApiDate("extraDate", LocalDateTime.now().plusDays(2), NvdApiDateType.START_DATE));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new TestDatesProcessor(
                    FeedType.CVE,
                    LocalDateTime.MAX,
                    1,
                    100,
                    "https://services.nvd.nist.gov/rest/json/cves/2.0",
                    tempDir,
                    "test-prefix",
                    threeDates,
                    downloadQueue);
        });

        assertEquals("dates must have 2 elements", exception.getMessage());
    }

    @Test
    void testSimpleConstructor() {
        processor = new TestDatesProcessor(FeedType.CVE, LocalDateTime.MAX, tempDir, "test-prefix", downloadQueue);

        assertNotNull(processor);
        assertEquals(FeedType.CVE, processor.getFeedType());
        assertEquals(LocalDateTime.MAX, processor.getPoison());
        assertEquals(tempDir, processor.getOutDir());
        assertEquals("test-prefix", processor.getOutFilePrefix());
        assertTrue(processor.getDates().isEmpty());
        assertEquals(downloadQueue, processor.getDownloadQueue());
    }

    @Test
    void testGetNvdApiStartDate() {
        processor = new TestDatesProcessor(
                FeedType.CVE,
                LocalDateTime.MAX,
                1,
                100,
                "https://services.nvd.nist.gov/rest/json/cves/2.0",
                tempDir,
                "test-prefix",
                validDates,
                downloadQueue);

        NvdApiDate startDate = processor.getNvdApiStartDate();
        assertNotNull(startDate);
        assertEquals(NvdApiDateType.START_DATE, startDate.type());
        assertEquals("lastModStartDate", startDate.name());
        assertEquals(LocalDateTime.of(2024, 1, 1, 0, 0), startDate.value());
    }

    @Test
    void testGetNvdApiEndDate() {
        processor = new TestDatesProcessor(
                FeedType.CVE,
                LocalDateTime.MAX,
                1,
                100,
                "https://services.nvd.nist.gov/rest/json/cves/2.0",
                tempDir,
                "test-prefix",
                validDates,
                downloadQueue);

        NvdApiDate endDate = processor.getNvdApiEndDate();
        assertNotNull(endDate);
        assertEquals(NvdApiDateType.END_DATE, endDate.type());
        assertEquals("lastModEndDate", endDate.name());
        assertEquals(LocalDateTime.of(2024, 1, 31, 23, 59, 59), endDate.value());
    }

    @Test
    void testGetNvdApiStartDateNotFound() {
        List<NvdApiDate> datesWithoutStart = Arrays.asList(
                new NvdApiDate("lastModEndDate", LocalDateTime.now(), NvdApiDateType.END_DATE),
                new NvdApiDate("anotherEndDate", LocalDateTime.now().plusDays(1), NvdApiDateType.END_DATE));

        processor = new TestDatesProcessor(
                FeedType.CVE,
                LocalDateTime.MAX,
                1,
                100,
                "https://services.nvd.nist.gov/rest/json/cves/2.0",
                tempDir,
                "test-prefix",
                datesWithoutStart,
                downloadQueue);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            processor.getNvdApiStartDate();
        });

        assertEquals("start date not found", exception.getMessage());
    }

    @Test
    void testGetNvdApiEndDateNotFound() {
        List<NvdApiDate> datesWithoutEnd = Arrays.asList(
                new NvdApiDate("lastModStartDate", LocalDateTime.now(), NvdApiDateType.START_DATE),
                new NvdApiDate("anotherStartDate", LocalDateTime.now().plusDays(1), NvdApiDateType.START_DATE));

        processor = new TestDatesProcessor(
                FeedType.CVE,
                LocalDateTime.MAX,
                1,
                100,
                "https://services.nvd.nist.gov/rest/json/cves/2.0",
                tempDir,
                "test-prefix",
                datesWithoutEnd,
                downloadQueue);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            processor.getNvdApiEndDate();
        });

        assertEquals("end date not found", exception.getMessage());
    }

    @Test
    void testGetStartDate() {
        processor = new TestDatesProcessor(
                FeedType.CVE,
                LocalDateTime.MAX,
                1,
                100,
                "https://services.nvd.nist.gov/rest/json/cves/2.0",
                tempDir,
                "test-prefix",
                validDates,
                downloadQueue);

        LocalDateTime startDate = processor.getStartDate();
        assertEquals(LocalDateTime.of(2024, 1, 1, 0, 0), startDate);
    }

    @Test
    void testGetEndDate() {
        processor = new TestDatesProcessor(
                FeedType.CVE,
                LocalDateTime.MAX,
                1,
                100,
                "https://services.nvd.nist.gov/rest/json/cves/2.0",
                tempDir,
                "test-prefix",
                validDates,
                downloadQueue);

        LocalDateTime endDate = processor.getEndDate();
        assertEquals(LocalDateTime.of(2024, 1, 31, 23, 59, 59), endDate);
    }

    @Test
    void testValidateDateRangeValid() {
        processor = new TestDatesProcessor(FeedType.CVE, LocalDateTime.MAX, tempDir, "test-prefix", downloadQueue);

        LocalDateTime start = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2024, 1, 31, 23, 59, 59);

        assertDoesNotThrow(() -> processor.validateDateRange(start, end));
    }

    @Test
    void testValidateDateRangeStartAfterEnd() {
        processor = new TestDatesProcessor(FeedType.CVE, LocalDateTime.MAX, tempDir, "test-prefix", downloadQueue);

        LocalDateTime start = LocalDateTime.of(2024, 1, 31, 23, 59, 59);
        LocalDateTime end = LocalDateTime.of(2024, 1, 1, 0, 0);

        NvdException exception = assertThrows(NvdException.class, () -> {
            processor.validateDateRange(start, end);
        });

        assertEquals("Invalid date range", exception.getMessage());
    }

    @Test
    void testValidateDateRangeNullStartDate() {
        processor = new TestDatesProcessor(FeedType.CVE, LocalDateTime.MAX, tempDir, "test-prefix", downloadQueue);

        LocalDateTime end = LocalDateTime.of(2024, 1, 31, 23, 59, 59);

        NvdException exception = assertThrows(NvdException.class, () -> {
            processor.validateDateRange(null, end);
        });

        assertEquals("Invalid date range", exception.getMessage());
    }

    @Test
    void testValidateDateRangeStartInFuture() {
        processor = new TestDatesProcessor(FeedType.CVE, LocalDateTime.MAX, tempDir, "test-prefix", downloadQueue);

        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = LocalDateTime.now().plusDays(2);

        NvdException exception = assertThrows(NvdException.class, () -> {
            processor.validateDateRange(start, end);
        });

        assertEquals("Invalid date range", exception.getMessage());
    }

    @Test
    void testValidateDateRangeMoreThanYear() {
        processor = new TestDatesProcessor(FeedType.CVE, LocalDateTime.MAX, tempDir, "test-prefix", downloadQueue);

        LocalDateTime start = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2024, 6, 1, 0, 0); // More than a year

        assertDoesNotThrow(() -> processor.validateDateRange(start, end));
    }

    @Test
    void testValidateDateRangeMoreThanMaxDays() {
        processor = new TestDatesProcessor(FeedType.CVE, LocalDateTime.MAX, tempDir, "test-prefix", downloadQueue);

        LocalDateTime start = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2024, 6, 1, 0, 0); // More than 120 days

        assertDoesNotThrow(() -> processor.validateDateRange(start, end));
    }

    @Test
    void testGetDownloadFile() {
        processor = new TestDatesProcessor(FeedType.CVE, LocalDateTime.MAX, tempDir, "test-prefix", downloadQueue);

        LocalDateTime startDate = LocalDateTime.of(2024, 1, 1, 12, 30, 45);
        LocalDateTime endDate = LocalDateTime.of(2024, 1, 31, 18, 45, 30);
        int startIndex = 100;
        int endIndex = 199;

        File downloadFile = processor.getDownloadFile(startDate, endDate, startIndex, endIndex, tempDir);

        assertNotNull(downloadFile);
        assertTrue(downloadFile.getName().contains("test-prefix"));
        assertTrue(downloadFile.getName().contains("0000100"));
        assertTrue(downloadFile.getName().contains("0000199"));
        assertTrue(downloadFile.getName().endsWith(".json"));
        assertTrue(downloadFile.getAbsolutePath().startsWith(tempDir.toString()));
    }

    @Test
    void testGetDownloadUri() throws Exception {
        processor = new TestDatesProcessor(
                FeedType.CVE,
                LocalDateTime.MAX,
                1,
                100,
                "https://services.nvd.nist.gov/rest/json/cves/2.0",
                tempDir,
                "test-prefix",
                validDates,
                downloadQueue);

        NvdApiDate startDate =
                new NvdApiDate("lastModStartDate", LocalDateTime.of(2024, 1, 1, 0, 0), NvdApiDateType.START_DATE);
        NvdApiDate endDate =
                new NvdApiDate("lastModEndDate", LocalDateTime.of(2024, 1, 31, 23, 59, 59), NvdApiDateType.END_DATE);
        List<NameValuePair> queryParams = Arrays.asList(new BasicNameValuePair("resultsPerPage", "100"));
        long startIndex = 50;

        URI downloadUri = processor.getDownloadUri(startDate, endDate, startIndex, queryParams);

        assertNotNull(downloadUri);
        assertEquals("https", downloadUri.getScheme());
        assertEquals("services.nvd.nist.gov", downloadUri.getHost());
        assertTrue(downloadUri.getQuery().contains("lastModStartDate="));
        assertTrue(downloadUri.getQuery().contains("lastModEndDate="));
        assertTrue(downloadUri.getQuery().contains("startIndex=50"));
        assertTrue(downloadUri.getQuery().contains("resultsPerPage=100"));
    }

    @Test
    void testGetDownloadUriWithInvalidEndpoint() {
        processor = new TestDatesProcessor(
                FeedType.CVE,
                LocalDateTime.MAX,
                1,
                100,
                "ht<>tp://invalid URL with spaces and [brackets]", // Cause a URISyntaxException
                tempDir,
                "test-prefix",
                validDates,
                downloadQueue);

        NvdApiDate startDate =
                new NvdApiDate("lastModStartDate", LocalDateTime.of(2024, 1, 1, 0, 0), NvdApiDateType.START_DATE);
        NvdApiDate endDate =
                new NvdApiDate("lastModEndDate", LocalDateTime.of(2024, 1, 31, 23, 59, 59), NvdApiDateType.END_DATE);
        List<NameValuePair> queryParams = new ArrayList<>();
        long startIndex = 0;

        NvdException exception = assertThrows(NvdException.class, () -> {
            processor.getDownloadUri(startDate, endDate, startIndex, queryParams);
        });

        assertEquals("failed to build URI", exception.getMessage());
    }

    @Test
    void testTotalResultsByDateMap() {
        processor = new TestDatesProcessor(FeedType.CVE, LocalDateTime.MAX, tempDir, "test-prefix", downloadQueue);

        assertNotNull(processor.getTotalResultsByDate());
        assertTrue(processor.getTotalResultsByDate().isEmpty());
    }

    @Test
    void testGetDownloadFileWithDifferentFormats() {
        processor = new TestDatesProcessor(FeedType.CVE, LocalDateTime.MAX, tempDir, "nvd-cve", downloadQueue);

        LocalDateTime startDate = LocalDateTime.of(2024, 12, 25, 9, 15, 30);
        LocalDateTime endDate = LocalDateTime.of(2024, 12, 31, 21, 45, 0);
        int startIndex = 1;
        int endIndex = 50;

        File downloadFile = processor.getDownloadFile(startDate, endDate, startIndex, endIndex, tempDir);

        String fileName = downloadFile.getName();
        assertTrue(fileName.startsWith("nvd-cve-"));
        assertTrue(fileName.contains("0000001")); // 7-digit formatted start index
        assertTrue(fileName.contains("0000050")); // 7-digit formatted end index
        assertTrue(fileName.endsWith(".json"));

        assertTrue(fileName.contains("20241225")); // start date format
        assertTrue(fileName.contains("20241231")); // end date format
    }

    @Test
    void testValidateDateRangeWithNullEndDate() {
        processor = new TestDatesProcessor(FeedType.CVE, LocalDateTime.MAX, tempDir, "test-prefix", downloadQueue);

        LocalDateTime start = LocalDateTime.of(2024, 1, 1, 0, 0);

        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            processor.validateDateRange(start, null);
        });

        assertNotNull(exception);
    }

    @Test
    void testValidateDateRangeWithEndDateInFuture() {
        processor = new TestDatesProcessor(FeedType.CVE, LocalDateTime.MAX, tempDir, "test-prefix", downloadQueue);

        LocalDateTime start = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.now().plusDays(1); // Future date

        assertDoesNotThrow(() -> processor.validateDateRange(start, end));
    }

    @Test
    void testGetDownloadUriWithEmptyQueryParams() throws Exception {
        processor = new TestDatesProcessor(
                FeedType.CVE,
                LocalDateTime.MAX,
                1,
                100,
                "https://services.nvd.nist.gov/rest/json/cves/2.0",
                tempDir,
                "test-prefix",
                validDates,
                downloadQueue);

        NvdApiDate startDate =
                new NvdApiDate("pubStartDate", LocalDateTime.of(2024, 1, 1, 0, 0), NvdApiDateType.START_DATE);
        NvdApiDate endDate =
                new NvdApiDate("pubEndDate", LocalDateTime.of(2024, 1, 31, 23, 59, 59), NvdApiDateType.END_DATE);
        List<NameValuePair> queryParams = new ArrayList<>(); // Empty params
        long startIndex = 0;

        URI downloadUri = processor.getDownloadUri(startDate, endDate, startIndex, queryParams);

        assertNotNull(downloadUri);
        assertEquals("https", downloadUri.getScheme());
        assertEquals("services.nvd.nist.gov", downloadUri.getHost());
        assertTrue(downloadUri.getQuery().contains("pubStartDate="));
        assertTrue(downloadUri.getQuery().contains("pubEndDate="));
        assertTrue(downloadUri.getQuery().contains("startIndex=0"));
    }

    @Test
    void testValidateDateRangeEqualDates() {
        processor = new TestDatesProcessor(FeedType.CVE, LocalDateTime.MAX, tempDir, "test-prefix", downloadQueue);

        LocalDateTime sameDate = LocalDateTime.of(2024, 1, 15, 12, 0);

        assertDoesNotThrow(() -> processor.validateDateRange(sameDate, sameDate));
    }

    @Test
    void testGetDownloadFileWithZeroIndexes() {
        processor = new TestDatesProcessor(FeedType.CVE, LocalDateTime.MAX, tempDir, "nvd-test", downloadQueue);

        LocalDateTime startDate = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 1, 1, 23, 59, 59);
        int startIndex = 0;
        int endIndex = 0;

        File downloadFile = processor.getDownloadFile(startDate, endDate, startIndex, endIndex, tempDir);

        String fileName = downloadFile.getName();
        assertTrue(fileName.contains("0000000")); // 7-digit formatted zero indexes
        assertTrue(fileName.contains("nvd-test"));
        assertTrue(fileName.endsWith(".json"));
    }

    @Test
    void testGetDownloadFileWithLargeIndexes() {
        processor = new TestDatesProcessor(FeedType.CVE, LocalDateTime.MAX, tempDir, "nvd", downloadQueue);

        LocalDateTime startDate = LocalDateTime.of(2024, 6, 15, 10, 30);
        LocalDateTime endDate = LocalDateTime.of(2024, 6, 30, 16, 45);
        int startIndex = 999999;
        int endIndex = 1000000;

        File downloadFile = processor.getDownloadFile(startDate, endDate, startIndex, endIndex, tempDir);

        String fileName = downloadFile.getName();
        assertTrue(fileName.contains("0999999")); // 7-digit formatted large start index
        assertTrue(fileName.contains("1000000")); // 7-digit formatted large end index
        assertTrue(fileName.contains("nvd"));
        assertTrue(fileName.endsWith(".json"));
    }
}
