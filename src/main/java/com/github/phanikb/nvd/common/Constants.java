package com.github.phanikb.nvd.common;

public final class Constants {
    public static final String NVD_PROPERTIES_FILE = "nvd.yml";
    public static final String CLI_PROPERTIES_FILE = "cli.defaults.properties";
    public static final String OUT_FILE_PREFIX = "nvd";
    public static final int MIN_FREE_SPACE_IN_GB = 2; // in GB

    private Constants() {
        // prevent instantiation
    }
}