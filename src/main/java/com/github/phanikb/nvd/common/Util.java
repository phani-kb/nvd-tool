package com.github.phanikb.nvd.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import static com.github.phanikb.nvd.common.Constants.DEFAULT_REQUEST_TIMEOUT_SECS;
import static com.github.phanikb.nvd.common.Constants.OUT_FILE_PREFIX;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.hc.core5.util.TimeValue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.github.phanikb.nvd.enums.ArchiveType;
import com.github.phanikb.nvd.enums.FeedType;

public final class Util {
    private static final Logger logger = LogManager.getLogger(Util.class);
    private static final NvdProperties properties = NvdProperties.getInstance();

    private Util() {
        // prevent instantiation
    }

    /**
     * Loads a file from the classpath.
     *
     * @param fileName the name of the file to load
     * @return an InputStream of the file, or an empty Optional if the file is not found
     */
    public static Optional<InputStream> loadFileFromClasspath(final String fileName) {
        return Optional.ofNullable(Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName));
    }


}