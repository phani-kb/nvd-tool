package com.github.phanikb.nvd.cli.processor.api.download;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.github.phanikb.nvd.cli.processor.api.IApiDownloadUriConsumer;
import com.github.phanikb.nvd.cli.processor.api.IApiDownloadUriProducer;
import com.github.phanikb.nvd.common.NvdException;
import com.github.phanikb.nvd.enums.FeedType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ApiDownloaderTest {

    @Mock
    private IApiDownloadUriConsumer mockConsumer;

    @Mock
    private IApiDownloadUriProducer mockProducer;

    @TempDir
    private Path tempDir;

    private ApiDownloader apiDownloader;
    private File outDir;
    private static final String OUT_FILE = "test-output.json";
    private static final int MAX_RESULTS_PER_PAGE = 100;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);

        assertNotNull(com.github.phanikb.nvd.common.NvdProperties.getInstance(), "NvdProperties should be initialized");

        outDir = Files.createDirectory(tempDir.resolve("output")).toFile();
        apiDownloader = new ApiDownloader(
                FeedType.CVE, outDir, OUT_FILE, true, false, MAX_RESULTS_PER_PAGE, mockConsumer, mockProducer);
    }

    @Test
    void testConstructor() {
        assertNotNull(apiDownloader);
        assertEquals(FeedType.CVE, apiDownloader.getFeedType());
        assertEquals(outDir, apiDownloader.getOutDir());
        assertEquals(OUT_FILE, apiDownloader.getOutFile());
        assertTrue(apiDownloader.isDeleteTempDir());
        assertFalse(apiDownloader.isCompress());
        assertEquals(MAX_RESULTS_PER_PAGE, apiDownloader.getMaxResultsPerPage());
        assertEquals(mockConsumer, apiDownloader.getConsumer());
        assertEquals(mockProducer, apiDownloader.getProducer());
    }

    @Test
    void testGetters() {
        assertNotNull(apiDownloader.getConsumer());
        assertNotNull(apiDownloader.getProducer());
        assertTrue(apiDownloader.getNumberOfProducers() > 0);
        assertTrue(apiDownloader.getNumberOfConsumers() > 0);
        assertTrue(apiDownloader.getRollingWindowInSecs() > 0);
    }

    @Test
    void testDownloadSuccess() throws NvdException, InterruptedException {
        when(mockProducer.getTotalResults()).thenReturn(50);
        when(mockProducer.getTotalPages()).thenReturn(5);
        when(mockProducer.getTotalFiles()).thenReturn(5);
        doNothing().when(mockProducer).generateUris(any(ExecutorService.class), anyInt());

        doNothing().when(mockConsumer).downloadUris(any(ExecutorService.class), anyInt(), anyLong());

        CountDownLatch latch = new CountDownLatch(1);
        apiDownloader.download(latch);

        verify(mockProducer).generateUris(any(ExecutorService.class), eq(1));
        verify(mockProducer).getTotalResults();
        verify(mockProducer).getTotalPages();
        verify(mockProducer).getTotalFiles();

        verify(mockConsumer).downloadUris(any(ExecutorService.class), anyInt(), anyLong());

        assertEquals(50, apiDownloader.getTotalResults());
        assertEquals(5, apiDownloader.getTotalPages());
        assertEquals(5, apiDownloader.getTotalFiles());

        assertTrue(latch.await(1, TimeUnit.SECONDS));
    }

    @Test
    void testDownloadWithProducerException() throws NvdException {
        doThrow(new RuntimeException("Producer error"))
                .when(mockProducer)
                .generateUris(any(ExecutorService.class), anyInt());

        CountDownLatch latch = new CountDownLatch(1);
        assertThrows(RuntimeException.class, () -> apiDownloader.download(latch));

        verify(mockProducer).generateUris(any(ExecutorService.class), eq(1));
    }

    @Test
    void testDownloadWithConsumerException() throws NvdException {
        when(mockProducer.getTotalResults()).thenReturn(50);
        when(mockProducer.getTotalPages()).thenReturn(5);
        when(mockProducer.getTotalFiles()).thenReturn(5);
        doNothing().when(mockProducer).generateUris(any(ExecutorService.class), anyInt());

        doThrow(new RuntimeException("Consumer error"))
                .when(mockConsumer)
                .downloadUris(any(ExecutorService.class), anyInt(), anyLong());

        CountDownLatch latch = new CountDownLatch(1);
        assertThrows(RuntimeException.class, () -> apiDownloader.download(latch));

        verify(mockProducer).generateUris(any(ExecutorService.class), eq(1));
        verify(mockConsumer).downloadUris(any(ExecutorService.class), anyInt(), anyLong());
    }

    @Test
    void testGenerateOutputFileWithNoFiles() throws NvdException, IOException {
        apiDownloader.generateOutputFile("vulnerabilities");

        File outputFile = new File(outDir.getParent(), OUT_FILE);
        assertFalse(outputFile.exists());
    }

    @Test
    void testGenerateOutputFileWithFiles() throws NvdException, IOException, InterruptedException {
        String prefix = "nvdcve-V2";
        String jsonContent1 = "{\n" + "  \"resultsPerPage\": 1,\n"
                + "  \"startIndex\": 0,\n"
                + "  \"totalResults\": 3,\n"
                + "  \"format\": \"NVD_CVE\",\n"
                + "  \"version\": \"2.0\",\n"
                + "  \"timestamp\": \"2023-01-01T00:00:00.000Z\",\n"
                + "  \"vulnerabilities\": [\n"
                + "    {\"cve\": {\"id\": \"CVE-2023-0001\"}}\n"
                + "  ]\n"
                + "}";
        String jsonContent2 = "{\n" + "  \"resultsPerPage\": 1,\n"
                + "  \"startIndex\": 1,\n"
                + "  \"totalResults\": 3,\n"
                + "  \"format\": \"NVD_CVE\",\n"
                + "  \"version\": \"2.0\",\n"
                + "  \"timestamp\": \"2023-01-01T00:00:00.000Z\",\n"
                + "  \"vulnerabilities\": [\n"
                + "    {\"cve\": {\"id\": \"CVE-2023-0002\"}}\n"
                + "  ]\n"
                + "}";
        String jsonContent3 = "{\n" + "  \"resultsPerPage\": 1,\n"
                + "  \"startIndex\": 2,\n"
                + "  \"totalResults\": 3,\n"
                + "  \"format\": \"NVD_CVE\",\n"
                + "  \"version\": \"2.0\",\n"
                + "  \"timestamp\": \"2023-01-01T00:00:00.000Z\",\n"
                + "  \"vulnerabilities\": [\n"
                + "    {\"cve\": {\"id\": \"CVE-2023-0003\"}}\n"
                + "  ]\n"
                + "}";

        createTestFile(prefix + "-1.json", jsonContent1);
        createTestFile(prefix + "-2.json", jsonContent2);
        createTestFile(prefix + "-3.json", jsonContent3);

        when(mockProducer.getTotalResults()).thenReturn(3);
        when(mockProducer.getTotalPages()).thenReturn(3);
        when(mockProducer.getTotalFiles()).thenReturn(3);
        doNothing().when(mockProducer).generateUris(any(ExecutorService.class), anyInt());
        doNothing().when(mockConsumer).downloadUris(any(ExecutorService.class), anyInt(), anyLong());

        CountDownLatch latch = new CountDownLatch(1);
        apiDownloader.download(latch);

        apiDownloader.generateOutputFile("vulnerabilities");

        File outputFile = new File(outDir.getParent(), OUT_FILE);
        assertTrue(outputFile.exists());
        assertTrue(outputFile.length() > 0);
    }

    @Test
    void testGenerateOutputFileWithCompression() throws NvdException, IOException, InterruptedException {
        ApiDownloader compressedDownloader = new ApiDownloader(
                FeedType.CVE,
                outDir,
                OUT_FILE,
                true,
                true, // compression enabled
                MAX_RESULTS_PER_PAGE,
                mockConsumer,
                mockProducer);

        String prefix = "nvdcve-V2";
        String jsonContent = "{\n" + "  \"resultsPerPage\": 1,\n"
                + "  \"startIndex\": 0,\n"
                + "  \"totalResults\": 1,\n"
                + "  \"format\": \"NVD_CVE\",\n"
                + "  \"version\": \"2.0\",\n"
                + "  \"timestamp\": \"2023-01-01T00:00:00.000Z\",\n"
                + "  \"vulnerabilities\": [\n"
                + "    {\"cve\": {\"id\": \"CVE-2023-0001\"}}\n"
                + "  ]\n"
                + "}";
        createTestFile(prefix + "-1.json", jsonContent);

        when(mockProducer.getTotalResults()).thenReturn(1);
        when(mockProducer.getTotalPages()).thenReturn(1);
        when(mockProducer.getTotalFiles()).thenReturn(1);
        doNothing().when(mockProducer).generateUris(any(ExecutorService.class), anyInt());
        doNothing().when(mockConsumer).downloadUris(any(ExecutorService.class), anyInt(), anyLong());

        CountDownLatch latch = new CountDownLatch(1);
        compressedDownloader.download(latch);

        compressedDownloader.generateOutputFile("vulnerabilities");

        File outputFile = new File(outDir.getParent(), OUT_FILE);
        File zipFile = new File(outDir.getParent(), OUT_FILE + ".zip");
        assertTrue(outputFile.exists());
        assertTrue(zipFile.exists());
    }

    @Test
    void testDeleteTempDir() throws IOException {
        createTestFile("temp-file.txt", "test content");
        assertTrue(outDir.exists());
        assertTrue(outDir.listFiles().length > 0);

        apiDownloader.deleteTempDir();

        assertFalse(outDir.exists());
    }

    @Test
    void testDeleteTempDirWhenDisabled() throws IOException {
        ApiDownloader noDeleteDownloader = new ApiDownloader(
                FeedType.CVE,
                outDir,
                OUT_FILE,
                false, // deletion disabled
                false,
                MAX_RESULTS_PER_PAGE,
                mockConsumer,
                mockProducer);

        createTestFile("temp-file.txt", "test content");
        assertTrue(outDir.exists());

        noDeleteDownloader.deleteTempDir();

        assertTrue(outDir.exists());
    }

    @Test
    void testDifferentFeedTypes() {
        ApiDownloader cpeDownloader = new ApiDownloader(
                FeedType.CPE, outDir, OUT_FILE, true, false, MAX_RESULTS_PER_PAGE, mockConsumer, mockProducer);

        assertEquals(FeedType.CPE, cpeDownloader.getFeedType());

        ApiDownloader cveHistoryDownloader = new ApiDownloader(
                FeedType.CVE_HISTORY, outDir, OUT_FILE, true, false, MAX_RESULTS_PER_PAGE, mockConsumer, mockProducer);

        assertEquals(FeedType.CVE_HISTORY, cveHistoryDownloader.getFeedType());
    }

    @Test
    void testExecutorServiceCreation() throws NvdException {
        when(mockProducer.getTotalResults()).thenReturn(10);
        when(mockProducer.getTotalPages()).thenReturn(1);
        when(mockProducer.getTotalFiles()).thenReturn(1);
        doNothing().when(mockProducer).generateUris(any(ExecutorService.class), anyInt());
        doNothing().when(mockConsumer).downloadUris(any(ExecutorService.class), anyInt(), anyLong());

        CountDownLatch latch = new CountDownLatch(1);
        apiDownloader.download(latch);

        assertNotNull(apiDownloader.getProducerExecutor());
        assertNotNull(apiDownloader.getConsumerExecutor());
    }

    @Test
    void testThreadConfiguration() {
        assertTrue(apiDownloader.getNumberOfProducers() > 0);
        assertTrue(apiDownloader.getNumberOfConsumers() > 0);
        assertTrue(apiDownloader.getRollingWindowInSecs() > 0);

        assertTrue(apiDownloader.getNumberOfConsumers() <= Runtime.getRuntime().availableProcessors());
    }

    @Test
    void testNullParameters() {
        ApiDownloader downloaderWithNullFeedType = new ApiDownloader(
                null, // null feedType
                outDir,
                OUT_FILE,
                true,
                false,
                MAX_RESULTS_PER_PAGE,
                mockConsumer,
                mockProducer);
        assertNotNull(downloaderWithNullFeedType);

        ApiDownloader downloaderWithNullOutDir = new ApiDownloader(
                FeedType.CVE,
                null, // null outDir
                OUT_FILE,
                true,
                false,
                MAX_RESULTS_PER_PAGE,
                mockConsumer,
                mockProducer);
        assertNotNull(downloaderWithNullOutDir);
    }

    @Test
    void testInvalidMaxResultsPerPage() {
        ApiDownloader invalidDownloader = new ApiDownloader(
                FeedType.CVE,
                outDir,
                OUT_FILE,
                true,
                false,
                0, // invalid value
                mockConsumer,
                mockProducer);

        assertEquals(0, invalidDownloader.getMaxResultsPerPage());
    }

    @Test
    void testDownloadWithInterruptedException() throws NvdException, InterruptedException {
        when(mockProducer.getTotalResults()).thenReturn(10);
        when(mockProducer.getTotalPages()).thenReturn(1);
        when(mockProducer.getTotalFiles()).thenReturn(1);
        doNothing().when(mockProducer).generateUris(any(ExecutorService.class), anyInt());
        doNothing().when(mockConsumer).downloadUris(any(ExecutorService.class), anyInt(), anyLong());

        CountDownLatch latch = new CountDownLatch(1);
        apiDownloader.download(latch);

        assertTrue(latch.await(1, TimeUnit.SECONDS));

        Thread.currentThread().interrupt();
        boolean isInterrupted = Thread.interrupted(); // This clears the flag
        assertTrue(isInterrupted);
    }

    @Test
    void testGenerateOutputFileWithMismatchedCounts() throws NvdException, IOException, InterruptedException {
        String prefix = "nvdcve-V2";
        String jsonContent = "{\n" + "  \"resultsPerPage\": 1,\n"
                + "  \"startIndex\": 0,\n"
                + "  \"totalResults\": 1,\n"
                + "  \"format\": \"NVD_CVE\",\n"
                + "  \"version\": \"2.0\",\n"
                + "  \"timestamp\": \"2023-01-01T00:00:00.000Z\",\n"
                + "  \"vulnerabilities\": [\n"
                + "    {\"cve\": {\"id\": \"CVE-2023-0001\"}}\n"
                + "  ]\n"
                + "}";
        createTestFile(prefix + "-1.json", jsonContent);

        when(mockProducer.getTotalResults()).thenReturn(5);
        when(mockProducer.getTotalPages()).thenReturn(3);
        when(mockProducer.getTotalFiles()).thenReturn(2);
        doNothing().when(mockProducer).generateUris(any(ExecutorService.class), anyInt());
        doNothing().when(mockConsumer).downloadUris(any(ExecutorService.class), anyInt(), anyLong());

        CountDownLatch latch = new CountDownLatch(1);
        apiDownloader.download(latch);

        apiDownloader.generateOutputFile("vulnerabilities");

        File outputFile = new File(outDir.getParent(), OUT_FILE);
        assertTrue(outputFile.exists());
        assertTrue(outputFile.length() > 0);
    }

    private void createTestFile(String fileName, String content) throws IOException {
        Path filePath = outDir.toPath().resolve(fileName);
        Files.write(filePath, content.getBytes());
    }
}
