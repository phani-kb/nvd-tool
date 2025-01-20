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

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
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

    /**
     * Finds a file from the classpath.
     *
     * @param fileName the name of the file to find
     * @return a File object, or an empty Optional if the file is not found
     */
    public static Optional<File> findFileFromClasspath(final String fileName) {
        if (isNullOrEmpty(fileName)) {
            logger.error("File name cannot be null or empty");
            return Optional.empty();
        }
        URL url = Thread.currentThread().getContextClassLoader().getResource(fileName);
        return Optional.ofNullable(url).map(u -> new File(u.getFile()))
                .or(() -> {
                    logger.error("File {} does not exist", fileName);
                    return Optional.empty();
                });
    }

    /**
     * Gets the usable space in GB.
     *
     * @param dir the directory to check
     * @return the usable space in GB
     */
    public static int getUsableSpace(File dir) {
        return (int) (dir.getUsableSpace() / (1024 * 1024 * 1024));
    }

    /**
     * Checks if the given URI is a valid download URI.
     *
     * @param uri the URI to check
     * @return true if the URI is valid, false otherwise
     */
    public static boolean isValidDownloadUri(URI uri) {
        String path = uri.getPath();
        return !isNullOrEmpty(path) && !FilenameUtils.getName(path).isEmpty();
    }


    /**
     * Extracts the contents of an archive file to the specified output directory.
     *
     * @param filename  the name of the archive file
     * @param outputDir the directory to extract the contents to
     * @throws NvdException if an error occurs during extraction
     */
    public static void extract(String filename, File outputDir) throws NvdException {
        if (isNullOrEmpty(filename)) throw new NvdException("filename is null or empty");
        if (outputDir == null) throw new NvdException("outputDir is null");

        if (!outputDir.exists() || !outputDir.isDirectory() || !outputDir.canWrite()) {
            throw new NvdException("Invalid outputDir: " + outputDir);
        }

        String extension = FilenameUtils.getExtension(filename);
        if (isNullOrEmpty(extension)) throw new NvdException("cannot get extension from filename: " + filename);

        ArchiveType archiveType = ArchiveType.of("." + extension);
        if (archiveType == null) {
            logger.error("unsupported archive format: {}", filename);
            return;
        }

        File downloadedFile = new File(outputDir, filename);
        try {
            archiveType.extract(downloadedFile, outputDir);
        } catch (IOException | ArchiveException | CompressorException e) {
            throw new NvdException(e.getMessage(), e);
        }
    }

    /**
     * Loads properties from a file.
     *
     * @param file the file to load
     * @return the properties
     */
    public static Properties loadProperties(File file) {
        Properties properties = new Properties();
        if (file == null) return properties;

        try (InputStream is = loadFileFromClasspath(file.getName()).orElse(null)) {
            if (is == null) {
                logger.error("File {} not found", file.getName());
                return properties;
            }
            properties.load(is);
        } catch (IOException e) {
            logger.error("Error loading properties: {}", e.getMessage());
        }
        return properties;
    }
    public static void validateDateRange(LocalDateTime startDate, LocalDateTime endDate, boolean checkFormat) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("start date must be before or equal to end date.");
        }
        if (startDate.isAfter(DateFormats.TODAY_DATETIME)) {
            throw new IllegalArgumentException("start date must be before or equal to today.");
        }
        if (endDate != null && endDate.isAfter(DateFormats.TODAY_DATETIME)) {
            throw new IllegalArgumentException("end date must be before or equal to today.");
        }

        if (checkFormat) {
            startDate.format(DateFormats.ISO_DATE_TIME_EXT_FORMATTER);
            if (endDate != null) {
                endDate.format(DateFormats.ISO_DATE_TIME_EXT_FORMATTER);
            }
        }
    }
}