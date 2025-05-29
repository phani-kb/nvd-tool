package com.github.phanikb.nvd.common;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ConstantsTest {

    @Test
    public void testFileNameConstants() {
        assertEquals("nvd.yml", Constants.NVD_PROPERTIES_FILE, "NVD properties filename should match");
        assertEquals("cli.defaults.properties", Constants.CLI_PROPERTIES_FILE, "CLI properties filename should match");
    }

    @Test
    public void testUrlPlaceholderConstants() {
        assertEquals("{download-type}", Constants.CVE_URL_DOWNLOAD_TYPE, "Download type placeholder should match");
        assertEquals("{archive-type}", Constants.CVE_URL_ARCHIVE_TYPE, "Archive type placeholder should match");
    }

    @Test
    public void testDefaultDatetimePoison() {
        assertEquals(
                LocalDateTime.MAX,
                Constants.DEFAULT_DATETIME_POISON,
                "Default datetime poison should be LocalDateTime.MAX");
    }

    @Test
    public void testDefaultPoison() {
        assertEquals(Integer.MAX_VALUE, Constants.DEFAULT_POISON, "Default poison should be Integer.MAX_VALUE");
    }

    @Test
    public void testThreadingConstants() {
        assertEquals(1, Constants.DEFAULT_NUMBER_OF_PRODUCERS, "Default number of producers should be 1");
        assertTrue(Constants.NUMBER_OF_PROCESSORS > 0, "Number of processors should be positive");
        assertEquals(
                Runtime.getRuntime().availableProcessors(),
                Constants.NUMBER_OF_PROCESSORS,
                "Number of processors should match Runtime.availableProcessors");
    }

    @Test
    public void testRangeDaysConstant() {
        assertEquals(120, Constants.DEFAULT_MAX_RANGE_IN_DAYS, "Default max range in days should be 120");
    }

    @Test
    public void testRequestConstants() {
        assertEquals(6, Constants.DEFAULT_REQUEST_TIMEOUT_SECS, "Default request timeout should be 6 seconds");
        assertEquals(
                6000,
                Constants.DEFAULT_DELAY_BETWEEN_REQUESTS_IN_MS,
                "Default delay between requests should be 6000ms");
        assertEquals(3, Constants.DEFAULT_MAX_RETRIES, "Default max retries should be 3");
        assertEquals(10, Constants.DEFAULT_MAX_DOWNLOAD_ATTEMPTS, "Default max download attempts should be 10");
        assertEquals(30, Constants.DEFAULT_RETRY_INTERVAL_SECS, "Default retry interval should be 30 seconds");
    }

    @Test
    public void testOutputFilePrefix() {
        assertEquals("nvd", Constants.OUT_FILE_PREFIX, "Output file prefix should be 'nvd'");
    }

    @Test
    public void testTimeoutConstants() {
        assertEquals(5, Constants.DEFAULT_PRODUCER_TIMEOUT_IN_MINUTES, "Default producer timeout should be 5 minutes");
        assertEquals(
                60, Constants.DEFAULT_CONSUMER_TIMEOUT_IN_MINUTES, "Default consumer timeout should be 60 minutes");
    }

    @Test
    public void testPaginationConstants() {
        assertEquals(10, Constants.DEFAULT_MIN_RESULTS_PER_PAGE, "Default min results per page should be 10");
    }

    @Test
    public void testRateLimitConstant() {
        assertEquals(5, Constants.DEFAULT_RATE_LIMIT, "Default rate limit should be 5");
    }

    @Test
    public void testStorageConstants() {
        assertEquals(2, Constants.MIN_FREE_SPACE_IN_GB, "Min free space should be 2 GB");
    }

    @Test
    public void testLoggingConstants() {
        assertEquals(
                10, Constants.LOG_EVERY_N_PROCESSED_ELEMENTS, "Log frequency should be every 10 processed elements");
    }

    @Test
    public void testPrivateConstructor() {
        try {
            Class<?> clazz = Constants.class;
            java.lang.reflect.Constructor<?> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        } catch (IllegalAccessException
                | IllegalArgumentException
                | InstantiationException
                | NoSuchMethodException
                | SecurityException
                | InvocationTargetException e) {
            fail("Exception should not be thrown when invoking private constructor");
        }
    }
}
