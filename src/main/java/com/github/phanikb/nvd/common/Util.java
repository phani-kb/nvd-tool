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

import static com.github.phanikb.nvd.common.Constants.DEFAULT_REQUEST_TIMEOUT_SECS;
import static com.github.phanikb.nvd.common.Constants.OUT_FILE_PREFIX;

public final class Util {
    private static final Logger logger = LogManager.getLogger(Util.class);
    private static volatile NvdProperties properties;

    private Util() {
        // prevent instantiation
    }

    private static NvdProperties getProperties() {
        if (properties == null) {
            synchronized (Util.class) {
                if (properties == null) {
                    properties = NvdProperties.getInstance();
                }
            }
        }
        return properties;
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
        return Optional.ofNullable(Util.class.getClassLoader().getResourceAsStream(fileName));
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
        URL url = Util.class.getClassLoader().getResource(fileName);
        return Optional.ofNullable(url).map(u -> new File(u.getFile())).or(() -> {
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
     * @param filename the name of the archive file
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

    public static int getMaxDownloadAttempts() {
        return getProperties().getNvd().getDownload().getUsingApi().getMaxDownloadAttempts();
    }

    public static void waitToFinish(ExecutorService executor, int timeout, TimeUnit timeUnit) throws NvdException {
        executor.shutdown();
        try {
            boolean finished = executor.awaitTermination(timeout, timeUnit);
            if (!finished) {
                throw new NvdException("task did not finish in time, timeout: " + timeout + " minutes.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new NvdException("task interrupted: " + e.getMessage(), e);
        }
    }

    public static int getMaxThreads() {
        return getProperties()
                .getNvd()
                .getDownload()
                .getUsingApi()
                .getProcessor()
                .getMaxThreads();
    }

    public static String getOutFilePrefix(FeedType feedType) {
        String apiVersion = getProperties().getNvd().getApi().getVersion().name();
        return OUT_FILE_PREFIX + feedType.getName() + "-" + apiVersion;
    }

    @SuppressWarnings("unchecked")
    private static <T extends Temporal & TemporalAdjuster> T addDays(T date, long days) {
        return (T) date.plus(days, ChronoUnit.DAYS);
    }

    public static <T extends Temporal & TemporalAdjuster> List<T> getRangeDates(
            T startDate, T endDate, int maxDateRangeDays) {
        List<T> dates = new ArrayList<>();
        T tempDate = startDate;
        while (ChronoUnit.DAYS.between(tempDate, endDate) >= 0) {
            dates.add(tempDate);
            tempDate = addDays(tempDate, maxDateRangeDays - 1);
        }
        dates.add(endDate);
        return dates;
    }

    public static void sleepQuietly(int attempts) {
        sleepQuietly(getExponentialBackoff(attempts));
    }

    private static TimeValue getExponentialBackoff(int attempts) {
        return getExponentialBackoff(attempts, TimeValue.of(DEFAULT_REQUEST_TIMEOUT_SECS, TimeUnit.SECONDS));
    }

    private static TimeValue getExponentialBackoff(int attempts, TimeValue retryInterval) {
        long millis = (long) Math.pow(2, attempts) * retryInterval.toMilliseconds();
        return TimeValue.of(millis, TimeUnit.MILLISECONDS);
    }

    public static void sleepQuietly(TimeValue timeout) {
        try {
            Thread.sleep(timeout.toMilliseconds());
        } catch (InterruptedException e) {
            logger.warn("interrupted while sleeping for {} milliseconds", timeout.toMilliseconds());
            Thread.currentThread().interrupt();
        }
    }

    public static void sleep(int attempts, TimeValue retryInterval) throws InterruptedException {
        Thread.sleep(getExponentialBackoff(attempts, retryInterval).toMilliseconds());
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

    public static void deleteDir(File outDir) {
        if (outDir == null || !outDir.exists()) return;

        try {
            FileUtils.deleteDirectory(outDir);
        } catch (IOException e) {
            logger.error("failed to delete directory: {}", outDir);
        }
    }

    public static File[] getFiles(File outDir, String outFilePrefix) {
        return outDir.listFiles((dir, name) -> name.startsWith(outFilePrefix));
    }

    public static boolean skipInvalidCollection() {
        return getProperties().getNvd().getMerge().isSkipInvalidCollection();
    }

    public static int mergeFiles(File[] files, File outFile, String collectionNodeName) throws NvdException {
        if (files == null || files.length == 0) {
            logger.warn("no files to merge");
            return 0;
        }

        if (outFile == null) {
            throw new NvdException("output file name is null or empty");
        }

        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JsonFactory jsonFactory = mapper.getFactory();

        try (BufferedWriter writer = Files.newBufferedWriter(outFile.toPath())) {
            JsonGenerator generator = jsonFactory.createGenerator(writer);
            generator.useDefaultPrettyPrinter();
            generator.writeStartObject();
            generator.writeFieldName(collectionNodeName);
            generator.writeStartArray();

            JsonNode timestamp = null;
            JsonNode version = null;
            int actualResultsCount;
            JsonNode format = null;
            List<Integer> actualResults = new ArrayList<>();

            for (File file : files) {
                try (BufferedReader reader = Files.newBufferedReader(file.toPath())) {
                    final JsonNode node = mapper.readTree(reader);
                    final JsonNode collectionNode = node.get(collectionNodeName);

                    if (collectionNode == null || !collectionNode.isArray()) {
                        if (skipInvalidCollection()) {
                            logger.warn("Skipped collection node in file: {}", file.getAbsolutePath());
                            continue;
                        } else {
                            throw new NvdException("invalid collection node in file: " + file.getAbsolutePath());
                        }
                    }

                    for (JsonNode child : collectionNode) {
                        generator.writeTree(child);
                    }

                    actualResults.add(node.get("resultsPerPage").asInt());
                    if (file.equals(files[files.length - 1])) {
                        timestamp = node.get("timestamp");
                        version = node.get("version");
                        format = node.get("format");
                    }
                }
            }

            actualResultsCount =
                    actualResults.stream().mapToInt(Integer::intValue).sum();

            generator.writeEndArray();
            generator.writeObjectField("timestamp", timestamp);
            generator.writeObjectField("version", version);
            generator.writeObjectField("format", format);
            generator.writeObjectField("totalResults", actualResultsCount);
            generator.writeEndObject();
            generator.close();
            logger.info(
                    "merged downloaded files into {}, file size {} MB, total results: {}",
                    outFile.getAbsolutePath(),
                    FileUtils.sizeOf(outFile) / (1024 * 1024),
                    actualResultsCount);

            return actualResultsCount;
        } catch (IOException e) {
            throw new NvdException("failed to merge downloaded files: " + e.getMessage(), e);
        }
    }

    public static void compressFile(File outFile, ArchiveType format) {
        if (outFile == null || !outFile.exists()) {
            logger.error("output file is null or does not exist");
            return;
        }

        try {
            format.archive(outFile, outFile.getParentFile());
        } catch (IOException | ArchiveException e) {
            logger.error("failed to compress file: {}, error: {}", outFile.getAbsolutePath(), e.getMessage());
        }
    }

    public static void createDir(Path outDir) throws NvdException {
        if (outDir == null) return;

        try {
            Files.createDirectories(outDir);
        } catch (IOException e) {

            throw new NvdException("failed to create directory: " + outDir, e);
        }
    }

    public static int getLogEveryNProcessedElements() {
        return getProperties()
                .getNvd()
                .getDownload()
                .getUsingApi()
                .getProcessor()
                .getLogEveryNProcessedElements();
    }

    public static int getProducerWaitTimeToFinishInMinutes() {
        return getProperties()
                .getNvd()
                .getDownload()
                .getUsingApi()
                .getProcessor()
                .getProducerWaitTimeToFinishInMinutes();
    }

    public static int getConsumerWaitTimeToFinishInMinutes() {
        return getProperties()
                .getNvd()
                .getDownload()
                .getUsingApi()
                .getProcessor()
                .getConsumerWaitTimeToFinishInMinutes();
    }

    public static String getDefaultUserAgent() {
        return BuildConfig.ARTIFACT_ID + "/" + BuildConfig.VERSION + " (https://github.com/phani-kb/nvd-tool)";
    }
}
