package com.github.phanikb.nvd.common;

import java.time.LocalDateTime;

public final class Constants {
    public static final String NVD_PROPERTIES_FILE = "nvd.yml";
    public static final String CLI_PROPERTIES_FILE = "cli.defaults.properties";
    public static final String CVE_URL_DOWNLOAD_TYPE = "{download-type}";
    public static final String CVE_URL_ARCHIVE_TYPE = "{archive-type}";
    public static final LocalDateTime DEFAULT_DATETIME_POISON = LocalDateTime.MAX;
    public static final int DEFAULT_POISON = Integer.MAX_VALUE;
    public static final int DEFAULT_NUMBER_OF_PRODUCERS = 1;
    public static final int NUMBER_OF_PROCESSORS = Runtime.getRuntime().availableProcessors();
    public static final int DEFAULT_MAX_RANGE_IN_DAYS = 120;
    public static final int DEFAULT_REQUEST_TIMEOUT_SECS = 6;
    public static final long DEFAULT_DELAY_BETWEEN_REQUESTS_IN_MS = 6000; // nist recommendation
    public static final int DEFAULT_MAX_RETRIES = 3;
    public static final int DEFAULT_MAX_DOWNLOAD_ATTEMPTS = 10;
    public static final int DEFAULT_RETRY_INTERVAL_SECS = 30;
    public static final String OUT_FILE_PREFIX = "nvd";
    public static final int DEFAULT_PRODUCER_TIMEOUT_IN_MINUTES = 5;
    public static final int DEFAULT_CONSUMER_TIMEOUT_IN_MINUTES = 60;
    public static final int DEFAULT_MIN_RESULTS_PER_PAGE = 10;
    public static final int DEFAULT_RATE_LIMIT = 5;
    public static final int MIN_FREE_SPACE_IN_GB = 2; // in GB
    public static final int LOG_EVERY_N_PROCESSED_ELEMENTS = 10;

    private Constants() {
        // prevent instantiation
    }
}