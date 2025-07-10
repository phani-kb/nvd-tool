package com.github.phanikb.nvd.cli.processor.pc;

import java.io.File;
import java.nio.file.Path;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.github.phanikb.nvd.common.QueueElement;
import com.github.phanikb.nvd.enums.FeedType;

import static org.junit.jupiter.api.Assertions.*;

class StartIndexProcessorTest {

    @TempDir
    Path tempDir;

    private BlockingDeque<QueueElement> downloadQueue;
    private TestStartIndexProcessor processor;
    private final Integer poison = Integer.MAX_VALUE;

    private static class TestStartIndexProcessor extends StartIndexProcessor<Integer> {

        public TestStartIndexProcessor(
                FeedType feedType,
                Integer poison,
                int poisonPerCreator,
                int maxResultsPerPage,
                String endpoint,
                Path outDir,
                String outFilePrefix,
                BlockingDeque<QueueElement> downloadQueue) {
            super(
                    feedType,
                    poison,
                    poisonPerCreator,
                    maxResultsPerPage,
                    endpoint,
                    outDir,
                    outFilePrefix,
                    downloadQueue);
        }

        public TestStartIndexProcessor(
                FeedType feedType,
                Integer poison,
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
    }

    @Test
    void testFullConstructor() {
        processor = new TestStartIndexProcessor(
                FeedType.CVE,
                poison,
                1,
                100,
                "https://services.nvd.nist.gov/rest/json/cves/2.0",
                tempDir,
                "test-prefix",
                downloadQueue);

        assertNotNull(processor);
        assertEquals(FeedType.CVE, processor.getFeedType());
        assertEquals(poison, processor.getPoison());
        assertEquals(1, processor.getPoisonPerCreator());
        assertEquals(100, processor.getMaxResultsPerPage());
        assertEquals("https://services.nvd.nist.gov/rest/json/cves/2.0", processor.getEndpoint());
        assertEquals(tempDir, processor.getOutDir());
        assertEquals("test-prefix", processor.getOutFilePrefix());
        assertEquals(downloadQueue, processor.getDownloadQueue());
    }

    @Test
    void testSimpleConstructor() {
        processor = new TestStartIndexProcessor(FeedType.CVE, poison, tempDir, "test-prefix", downloadQueue);

        assertNotNull(processor);
        assertEquals(FeedType.CVE, processor.getFeedType());
        assertEquals(poison, processor.getPoison());
        assertEquals(tempDir, processor.getOutDir());
        assertEquals("test-prefix", processor.getOutFilePrefix());
        assertEquals(downloadQueue, processor.getDownloadQueue());

        // These should be defaults from simple constructor
        assertEquals(0, processor.getPoisonPerCreator());
        assertEquals(0, processor.getMaxResultsPerPage());
        assertNull(processor.getEndpoint());
    }

    @Test
    void testGetDownloadFile() {
        processor = new TestStartIndexProcessor(FeedType.CVE, poison, tempDir, "nvd-test", downloadQueue);

        File downloadFile = processor.getDownloadFile(100, 199, tempDir);

        assertNotNull(downloadFile);
        assertTrue(downloadFile.getName().contains("nvd-test"));
        assertTrue(downloadFile.getName().contains("0000100")); // 7-digit formatted start index
        assertTrue(downloadFile.getName().contains("0000199")); // 7-digit formatted end index
        assertTrue(downloadFile.getName().endsWith(".json"));
        assertTrue(downloadFile.getAbsolutePath().startsWith(tempDir.toString()));
    }

    @Test
    void testGetDownloadFileWithZeroIndexes() {
        processor = new TestStartIndexProcessor(FeedType.CVE, poison, tempDir, "zero-test", downloadQueue);

        File downloadFile = processor.getDownloadFile(0, 0, tempDir);

        String fileName = downloadFile.getName();
        assertTrue(fileName.contains("zero-test"));
        assertTrue(fileName.contains("0000000")); // Both indexes should be zero
        assertTrue(fileName.endsWith(".json"));
    }

    @Test
    void testGetDownloadFileWithLargeIndexes() {
        processor = new TestStartIndexProcessor(FeedType.CVE, poison, tempDir, "large-test", downloadQueue);

        File downloadFile = processor.getDownloadFile(999999, 1000000, tempDir);

        String fileName = downloadFile.getName();
        assertTrue(fileName.contains("large-test"));
        assertTrue(fileName.contains("0999999")); // 7-digit formatted large start index
        assertTrue(fileName.contains("1000000")); // 7-digit formatted large end index
        assertTrue(fileName.endsWith(".json"));
    }

    @Test
    void testGetDownloadFileFormatting() {
        processor = new TestStartIndexProcessor(FeedType.CVE, poison, tempDir, "format-test", downloadQueue);

        File downloadFile = processor.getDownloadFile(50, 149, tempDir);

        String fileName = downloadFile.getName();

        assertTrue(fileName.startsWith("format-test-"));
        assertTrue(fileName.contains("0000050_0000149"));
        assertTrue(fileName.endsWith(".json"));

        String[] parts = fileName.split("-");
        assertTrue(parts.length >= 3); // prefix, indexes, timestamp
    }

    @Test
    void testGetDownloadFileWithDifferentPrefixes() {
        String[] prefixes = {"nvd-cve", "test", "data-export", "vulnerability-data"};

        for (String prefix : prefixes) {
            processor = new TestStartIndexProcessor(FeedType.CVE, poison, tempDir, prefix, downloadQueue);

            File downloadFile = processor.getDownloadFile(0, 99, tempDir);
            assertTrue(downloadFile.getName().startsWith(prefix + "-"));
        }
    }

    @Test
    void testGetDownloadFileTimestampUniqueness() throws InterruptedException {
        processor = new TestStartIndexProcessor(FeedType.CVE, poison, tempDir, "timestamp-test", downloadQueue);

        File file1 = processor.getDownloadFile(0, 99, tempDir);

        Thread.sleep(1000);

        File file2 = processor.getDownloadFile(100, 199, tempDir);

        assertNotEquals(file1.getName(), file2.getName());
    }

    @Test
    void testGetDownloadFileIndexOrdering() {
        processor = new TestStartIndexProcessor(FeedType.CVE, poison, tempDir, "order-test", downloadQueue);

        File downloadFile = processor.getDownloadFile(50, 25, tempDir); // End index less than start

        String fileName = downloadFile.getName();
        assertTrue(fileName.contains("0000050"));
        assertTrue(fileName.contains("0000025"));

        assertNotNull(downloadFile);
    }

    @Test
    void testRunMethodImplementation() {
        processor = new TestStartIndexProcessor(FeedType.CVE, poison, tempDir, "run-test", downloadQueue);

        assertDoesNotThrow(() -> processor.run());
    }

    @Test
    void testInheritanceFromBaseProcessor() {
        processor = new TestStartIndexProcessor(FeedType.CVE, poison, tempDir, "inheritance-test", downloadQueue);

        assertTrue(processor instanceof BaseProcessor);
        assertTrue(processor instanceof Runnable);
    }

    @Test
    void testDifferentFeedTypes() {
        for (FeedType feedType : FeedType.values()) {
            processor = new TestStartIndexProcessor(
                    feedType, poison, tempDir, feedType.name().toLowerCase(), downloadQueue);

            assertEquals(feedType, processor.getFeedType());

            File downloadFile = processor.getDownloadFile(0, 99, tempDir);
            assertTrue(downloadFile.getName().contains(feedType.name().toLowerCase()));
        }
    }

    @Test
    void testFileExtension() {
        processor = new TestStartIndexProcessor(FeedType.CVE, poison, tempDir, "extension-test", downloadQueue);

        File downloadFile = processor.getDownloadFile(0, 99, tempDir);

        assertTrue(downloadFile.getName().endsWith(".json"));
        assertFalse(downloadFile.getName().endsWith(".xml"));
        assertFalse(downloadFile.getName().endsWith(".txt"));
    }

    @Test
    void testFilePathConstruction() {
        processor = new TestStartIndexProcessor(FeedType.CVE, poison, tempDir, "path-test", downloadQueue);

        File downloadFile = processor.getDownloadFile(0, 99, tempDir);

        assertEquals(tempDir.toFile(), downloadFile.getParentFile());
        assertTrue(downloadFile.getAbsolutePath().contains(tempDir.toString()));
    }

    @Test
    void testIndexFormatConsistency() {
        processor = new TestStartIndexProcessor(FeedType.CVE, poison, tempDir, "format-consistency", downloadQueue);

        int[][] testCases = {
            {0, 0},
            {1, 1},
            {10, 20},
            {100, 200},
            {1000, 2000},
            {10000, 20000},
            {100000, 200000}
        };

        for (int[] testCase : testCases) {
            int startIndex = testCase[0];
            int endIndex = testCase[1];

            File downloadFile = processor.getDownloadFile(startIndex, endIndex, tempDir);
            String fileName = downloadFile.getName();

            String expectedStart = String.format("%07d", startIndex);
            String expectedEnd = String.format("%07d", endIndex);

            assertTrue(
                    fileName.contains(expectedStart),
                    "File name should contain " + expectedStart + " but was: " + fileName);
            assertTrue(
                    fileName.contains(expectedEnd),
                    "File name should contain " + expectedEnd + " but was: " + fileName);
        }
    }
}
