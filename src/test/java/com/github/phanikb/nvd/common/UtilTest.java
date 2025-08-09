package com.github.phanikb.nvd.common;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.hc.core5.util.TimeValue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.github.phanikb.nvd.enums.FeedType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UtilTest {

    @Test
    public void testIsNullOrEmpty() {
        assertTrue(Util.isNullOrEmpty(null), "null should be considered empty");
        assertTrue(Util.isNullOrEmpty(""), "Empty string should be considered empty");
        assertFalse(Util.isNullOrEmpty("text"), "Non-empty string should not be considered empty");
    }

    @Test
    public void testLoadFileFromClasspath() {
        Optional<InputStream> result = Util.loadFileFromClasspath("cli.defaults.properties");
        assertTrue(result.isPresent(), "Should find cli.defaults.properties in classpath");

        result = Util.loadFileFromClasspath("non-existent-file.txt");
        assertFalse(result.isPresent(), "Should not find non-existent file in classpath");
    }

    @Test
    public void testFindFileFromClasspathWithNullOrEmpty() {
        Optional<File> result = Util.findFileFromClasspath(null);
        assertFalse(result.isPresent(), "Should return empty Optional for null filename");

        result = Util.findFileFromClasspath("");
        assertFalse(result.isPresent(), "Should return empty Optional for empty filename");
    }

    @Test
    public void testFindFileFromClasspath() {
        Optional<File> result = Util.findFileFromClasspath("cli.defaults.properties");

        if (result.isPresent()) {
            assertTrue(result.get().exists(), "File should exist");
            assertEquals("cli.defaults.properties", result.get().getName(), "File name should match");
        }

        result = Util.findFileFromClasspath("non-existent-file.txt");
        assertFalse(result.isPresent(), "Should not find non-existent file in classpath");
    }

    @Test
    public void testGetUsableSpace() throws IOException {
        Path tempDir = Files.createTempDirectory("util-test");
        File dir = tempDir.toFile();

        try {
            int space = Util.getUsableSpace(dir);
            assertTrue(space >= 0, "Usable space should be non-negative");
        } finally {
            Files.deleteIfExists(tempDir);
        }
    }

    @Test
    public void testIsValidDownloadUri() throws Exception {
        // Valid URI with path
        URI validUri = new URI("https://example.com/path/file.txt");
        assertTrue(Util.isValidDownloadUri(validUri), "Valid URI with path should return true");

        // Invalid URI without path
        URI invalidUri = new URI("https://example.com/");
        assertFalse(Util.isValidDownloadUri(invalidUri), "URI without filename should return false");

        // URI with empty path
        URI emptyPathUri = new URI("https://example.com");
        assertFalse(Util.isValidDownloadUri(emptyPathUri), "URI with empty path should return false");
    }

    @Test
    public void testLoadProperties() {
        // Test with null file
        Properties props = Util.loadProperties(null);
        assertNotNull(props, "Should return empty properties for null file");
        assertTrue(props.isEmpty(), "Properties should be empty for null file");

        // Test with existing file
        File file = new File("cli.defaults.properties");
        props = Util.loadProperties(file);
        assertNotNull(props, "Should return properties object");

        // Test with non-existent file
        File nonExistentFile = new File("non-existent.properties");
        props = Util.loadProperties(nonExistentFile);
        assertNotNull(props, "Should return empty properties for non-existent file");
        assertTrue(props.isEmpty(), "Properties should be empty for non-existent file");
    }

    @Test
    public void testPropertyBasedGetters() {
        // Test property-based getter methods
        assertTrue(Util.getMaxDownloadAttempts() > 0, "Max download attempts should be positive");
        assertTrue(Util.getMaxThreads() > 0, "Max threads should be positive");
        assertTrue(Util.getLogEveryNProcessedElements() > 0, "Log frequency should be positive");
        assertTrue(Util.getProducerWaitTimeToFinishInMinutes() > 0, "Producer timeout should be positive");
        assertTrue(Util.getConsumerWaitTimeToFinishInMinutes() > 0, "Consumer timeout should be positive");

        // Test user agent
        String userAgent = Util.getDefaultUserAgent();
        assertNotNull(userAgent, "User agent should not be null");
        assertTrue(userAgent.contains("nvd-tool"), "User agent should contain project name");
    }

    @Test
    public void testGetOutFilePrefix() {
        // Test with different feed types
        String prefix = Util.getOutFilePrefix(FeedType.CVE);
        assertNotNull(prefix, "Output file prefix should not be null");
        assertTrue(prefix.startsWith("nvd"), "Prefix should start with nvd");
        assertTrue(prefix.contains("cve"), "Prefix should contain feed type");
    }

    @Test
    public void testGetRangeDates() {
        LocalDateTime start = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2024, 1, 10, 0, 0);

        List<LocalDateTime> dates = Util.getRangeDates(start, end, 5);
        assertNotNull(dates, "Range dates should not be null");
        assertFalse(dates.isEmpty(), "Range dates should not be empty");
        assertTrue(dates.size() >= 2, "Should have at least start and end dates");
        assertEquals(start, dates.get(0), "First date should be start date");
        assertEquals(end, dates.get(dates.size() - 1), "Last date should be end date");
    }

    @Test
    public void testValidateDateRange() {
        LocalDateTime start = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2023, 1, 2, 0, 0);

        // Valid date range
        Util.validateDateRange(start, end, false);

        // Invalid: start after end
        assertThrows(
                IllegalArgumentException.class,
                () -> {
                    Util.validateDateRange(end, start, false);
                },
                "Should throw exception when start is after end");

        // Invalid: start in future
        LocalDateTime futureDate = LocalDateTime.now().plusDays(1);
        assertThrows(
                IllegalArgumentException.class,
                () -> {
                    Util.validateDateRange(futureDate, futureDate.plusDays(1), false);
                },
                "Should throw exception when start is in future");
    }

    @Test
    public void testValidateDateRangeFormatCheck() {
        LocalDateTime start = LocalDateTime.of(2023, 1, 1, 12, 34, 56, 789000000);
        LocalDateTime end = LocalDateTime.of(2023, 1, 2, 23, 45, 59, 123000000);
        Util.validateDateRange(start, end, true);
        assertThrows(NullPointerException.class, () -> Util.validateDateRange(null, end, true));
        assertThrows(NullPointerException.class, () -> Util.validateDateRange(start, null, true));
    }

    @Test
    public void testGetFiles() throws IOException {
        Path tempDir = Files.createTempDirectory("util-test");
        File dir = tempDir.toFile();

        try {
            // Create test files
            File file1 = new File(dir, "nvd-test1.json");
            File file2 = new File(dir, "nvd-test2.json");
            File file3 = new File(dir, "other-file.json");

            Files.createFile(file1.toPath());
            Files.createFile(file2.toPath());
            Files.createFile(file3.toPath());

            File[] files = Util.getFiles(dir, "nvd");
            assertNotNull(files, "Files array should not be null");
            assertEquals(2, files.length, "Should find 2 files with nvd prefix");
        } finally {
            // Cleanup
            Files.walk(tempDir).map(Path::toFile).forEach(File::delete);
        }
    }

    @Test
    public void testWaitToFinish() throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        // Submit a quick task
        executor.submit(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Should finish successfully
        Util.waitToFinish(executor, 5, TimeUnit.SECONDS);
    }

    @Test
    public void testWaitToFinishTimeout() {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        // Submit a long-running task
        executor.submit(() -> {
            try {
                Thread.sleep(5000); // 5 seconds
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Should timeout
        assertThrows(
                NvdException.class,
                () -> {
                    Util.waitToFinish(executor, 1, TimeUnit.SECONDS);
                },
                "Should throw exception on timeout");
    }

    @Test
    public void testCreateDir(@TempDir Path tempDir) throws Exception {
        Path testDir = tempDir.resolve("test-directory");

        // Should create directory successfully
        Util.createDir(testDir);
        assertTrue(Files.exists(testDir), "Directory should be created");
        assertTrue(Files.isDirectory(testDir), "Path should be a directory");

        // Should handle null gracefully
        Util.createDir(null);
    }

    @Test
    public void testDeleteDirRemovesDirectory(@TempDir Path tempDir) throws IOException {
        File dir = tempDir.toFile();
        File file1 = new File(dir, "file1.txt");
        File file2 = new File(dir, "file2.txt");
        Files.createFile(file1.toPath());
        Files.createFile(file2.toPath());
        assertTrue(file1.exists() && file2.exists(), "Files should exist before deleteDir");
        Util.deleteDir(dir);
        assertFalse(dir.exists(), "Directory should not exist after deleteDir");
    }

    @Test
    public void testGetExponentialBackoffIncreases() {
        TimeValue t1 = Util.getExponentialBackoff(1);
        TimeValue t2 = Util.getExponentialBackoff(2);
        TimeValue t3 = Util.getExponentialBackoff(3);
        assertTrue(t2.toMilliseconds() > t1.toMilliseconds(), "Backoff should increase with attempts");
        assertTrue(t3.toMilliseconds() > t2.toMilliseconds(), "Backoff should increase with attempts");
    }

    @Test
    public void testSleepQuietlyDoesNotThrow() {
        long start = System.currentTimeMillis();
        Util.sleepQuietly(1); // should sleep for a short time
        long elapsed = System.currentTimeMillis() - start;
        assertTrue(elapsed >= Util.getExponentialBackoff(1).toMilliseconds(), "Should sleep at least backoff time");
    }

    @Test
    public void testSleepDoesSleep() throws InterruptedException {
        TimeValue retryInterval = TimeValue.of(100, TimeUnit.MILLISECONDS);
        long start = System.currentTimeMillis();
        Util.sleep(1, retryInterval);
        long elapsed = System.currentTimeMillis() - start;
        assertTrue(
                elapsed >= Util.getExponentialBackoff(1, retryInterval).toMilliseconds(),
                "Should sleep at least backoff time");
    }
}
