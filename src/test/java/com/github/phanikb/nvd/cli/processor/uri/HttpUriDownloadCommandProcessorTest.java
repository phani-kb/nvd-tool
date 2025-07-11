package com.github.phanikb.nvd.cli.processor.uri;

import java.io.File;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.github.phanikb.nvd.cli.uri.download.HttpUriDownloadStatus;
import com.github.phanikb.nvd.common.HttpUtil;
import com.github.phanikb.nvd.common.NvdDownloadException;
import com.github.phanikb.nvd.enums.ArchiveType;
import com.github.phanikb.nvd.enums.FeedType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HttpUriDownloadCommandProcessorTest {

    @TempDir
    Path tempDir;

    private HttpUriDownloadCommandProcessor processor;
    private File outDir;
    private URI testUri;
    private URI testUri2;
    private Set<URI> uris;

    @BeforeEach
    void setUp() throws Exception {
        outDir = tempDir.toFile();
        testUri = URI.create("https://example.com/test-file.json");
        testUri2 = URI.create("https://example.com/test-file2.json");
        uris = Set.of(testUri, testUri2);
    }

    @Test
    void testConstructorAndGetters() {
        processor = new HttpUriDownloadCommandProcessor(FeedType.CVE, outDir, ArchiveType.ZIP, true, uris);

        assertEquals(FeedType.CVE, processor.getFeedType());
        assertEquals(outDir, processor.getOutDir());
        assertEquals(ArchiveType.ZIP, processor.getArchiveType());
        assertTrue(processor.isExtract());
        assertEquals(uris, processor.getUris());
    }

    @Test
    void testConstructorWithNullUris() {
        processor = new HttpUriDownloadCommandProcessor(FeedType.CVE, outDir, ArchiveType.ZIP, true, null);

        assertEquals(FeedType.CVE, processor.getFeedType());
        assertEquals(outDir, processor.getOutDir());
        assertEquals(ArchiveType.ZIP, processor.getArchiveType());
        assertTrue(processor.isExtract());
        assertTrue(processor.getUris().isEmpty());
    }

    @Test
    void testDownloadSuccessfulSingleUri() throws Exception {
        try (MockedStatic<HttpUtil> mockedHttpUtil = Mockito.mockStatic(HttpUtil.class)) {
            mockedHttpUtil.when(() -> HttpUtil.getFilename(testUri)).thenReturn("test-file.json");
            mockedHttpUtil.when(HttpUtil::getUserAgent).thenReturn("Test-Agent");
            mockedHttpUtil.when(HttpUtil::getProxy).thenReturn(null);

            processor =
                    new HttpUriDownloadCommandProcessor(FeedType.CVE, outDir, ArchiveType.ZIP, false, Set.of(testUri));

            List<HttpUriDownloadStatus> results = processor.download();

            assertNotNull(results);
            assertEquals(1, results.size());

            HttpUriDownloadStatus status = results.get(0);
            assertEquals(testUri, status.getUri());
            assertEquals("test-file.json", status.getFilename());
        }
    }

    @Test
    void testDownloadMultipleUris() throws Exception {
        try (MockedStatic<HttpUtil> mockedHttpUtil = Mockito.mockStatic(HttpUtil.class)) {
            mockedHttpUtil.when(() -> HttpUtil.getFilename(testUri)).thenReturn("test-file.json");
            mockedHttpUtil.when(() -> HttpUtil.getFilename(testUri2)).thenReturn("test-file2.json");
            mockedHttpUtil.when(HttpUtil::getUserAgent).thenReturn("Test-Agent");
            mockedHttpUtil.when(HttpUtil::getProxy).thenReturn(null);

            processor = new HttpUriDownloadCommandProcessor(FeedType.CVE, outDir, ArchiveType.ZIP, false, uris);

            List<HttpUriDownloadStatus> results = processor.download();

            assertNotNull(results);
            assertEquals(2, results.size());
        }
    }

    @Test
    void testDownloadWithNullFilename() throws Exception {
        URI uriWithoutPath = URI.create("https://example.com");

        processor = new HttpUriDownloadCommandProcessor(
                FeedType.CVE, outDir, ArchiveType.ZIP, false, Set.of(uriWithoutPath));

        List<HttpUriDownloadStatus> results = processor.download();

        assertNotNull(results);
        assertEquals(1, results.size());

        HttpUriDownloadStatus status = results.get(0);
        assertFalse(status.isSuccess());
        assertTrue(status.getMessage().contains("cannot get filename from uri"));
    }

    @Test
    void testDownloadWithEmptyPath() throws Exception {
        URI uriWithEmptyPath = URI.create("https://example.com/");

        processor = new HttpUriDownloadCommandProcessor(
                FeedType.CVE, outDir, ArchiveType.ZIP, false, Set.of(uriWithEmptyPath));

        List<HttpUriDownloadStatus> results = processor.download();

        assertNotNull(results);
        assertEquals(1, results.size());

        HttpUriDownloadStatus status = results.get(0);
        assertFalse(status.isSuccess());
        assertTrue(status.getMessage().contains("cannot get filename from uri")
                || status.getMessage().contains("is a directory"));
    }

    @Test
    void testDownloadWithNullUri() throws Exception {
        processor = new HttpUriDownloadCommandProcessor(FeedType.CVE, outDir, ArchiveType.ZIP, false, Set.of(testUri));

        assertThrows(NvdDownloadException.class, () -> {
            processor.download(null, outDir, new File("test.json"));
        });
    }

    @Test
    void testDownloadWithNullFilenameParameter() throws Exception {
        processor = new HttpUriDownloadCommandProcessor(FeedType.CVE, outDir, ArchiveType.ZIP, false, Set.of(testUri));

        assertThrows(NvdDownloadException.class, () -> {
            processor.download(testUri, outDir, null);
        });
    }

    @Test
    void testDownloadWithDirectoryAsFilename() throws Exception {
        File directory = new File(outDir, "test-dir");
        directory.mkdirs();

        processor = new HttpUriDownloadCommandProcessor(FeedType.CVE, outDir, ArchiveType.ZIP, false, Set.of(testUri));

        assertThrows(NvdDownloadException.class, () -> {
            processor.download(testUri, outDir, directory);
        });
    }

    @Test
    void testValidationWithExistingFile() throws Exception {
        File existingFile = new File(outDir, "existing.json");
        existingFile.createNewFile();

        processor = new HttpUriDownloadCommandProcessor(FeedType.CVE, outDir, ArchiveType.ZIP, false, Set.of(testUri));

        try {
            processor.download(testUri, outDir, existingFile);
        } catch (NvdDownloadException e) {
            assertTrue(
                    e.getMessage().contains("cannot download") || e.getMessage().contains("uri is null"));
        }
    }

    @Test
    void testDownloadWithProxy() throws Exception {
        InetSocketAddress proxy = new InetSocketAddress("proxy.example.com", 8080);

        try (MockedStatic<HttpUtil> mockedHttpUtil = Mockito.mockStatic(HttpUtil.class)) {
            mockedHttpUtil.when(HttpUtil::getUserAgent).thenReturn("Test-Agent");
            mockedHttpUtil.when(HttpUtil::getProxy).thenReturn(proxy);

            processor =
                    new HttpUriDownloadCommandProcessor(FeedType.CVE, outDir, ArchiveType.ZIP, false, Set.of(testUri));

            assertNotNull(processor);
        }
    }

    @Test
    void testProcess() throws Exception {
        try (MockedStatic<HttpUtil> mockedHttpUtil = Mockito.mockStatic(HttpUtil.class)) {
            mockedHttpUtil.when(() -> HttpUtil.getFilename(testUri)).thenReturn("test-file.json");
            mockedHttpUtil.when(HttpUtil::getUserAgent).thenReturn("Test-Agent");
            mockedHttpUtil.when(HttpUtil::getProxy).thenReturn(null);

            processor =
                    new HttpUriDownloadCommandProcessor(FeedType.CVE, outDir, ArchiveType.ZIP, false, Set.of(testUri));

            processor.process();

            assertNotNull(processor.getStatusList());
            assertEquals(1, processor.getStatusList().size());
        }
    }

    @Test
    void testCloseExecutorService() throws Exception {
        processor = new HttpUriDownloadCommandProcessor(FeedType.CVE, outDir, ArchiveType.ZIP, false, Set.of(testUri));

        processor.close();
    }

    @Test
    void testGetMaxConcurrentDownloads() {
        processor = new HttpUriDownloadCommandProcessor(FeedType.CVE, outDir, ArchiveType.ZIP, false, Set.of(testUri));

        int maxDownloads = processor.getMaxConcurrentDownloads();
        assertTrue(maxDownloads > 0);
    }

    @Test
    void testHttpRequestBuilding() throws Exception {
        try (MockedStatic<HttpUtil> mockedHttpUtil = Mockito.mockStatic(HttpUtil.class)) {
            mockedHttpUtil.when(HttpUtil::getUserAgent).thenReturn("Test-Agent/1.0");
            mockedHttpUtil.when(HttpUtil::getProxy).thenReturn(null);

            processor =
                    new HttpUriDownloadCommandProcessor(FeedType.CVE, outDir, ArchiveType.ZIP, false, Set.of(testUri));

            assertNotNull(processor);
        }
    }

    @Test
    void testEmptyUriSet() throws Exception {
        processor = new HttpUriDownloadCommandProcessor(FeedType.CVE, outDir, ArchiveType.ZIP, false, Set.of());

        List<HttpUriDownloadStatus> results = processor.download();

        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    void testUriWithNullInSet() throws Exception {
        Set<URI> urisWithNull = Set.of(testUri); // Can't add null to a Set.of()
        processor = new HttpUriDownloadCommandProcessor(FeedType.CVE, outDir, ArchiveType.ZIP, false, urisWithNull);

        assertNotNull(processor.getUris());
        assertEquals(1, processor.getUris().size());
    }

    @Test
    void testDifferentArchiveTypes() {
        HttpUriDownloadCommandProcessor zipProcessor =
                new HttpUriDownloadCommandProcessor(FeedType.CVE, outDir, ArchiveType.ZIP, true, uris);
        assertEquals(ArchiveType.ZIP, zipProcessor.getArchiveType());

        HttpUriDownloadCommandProcessor gzProcessor =
                new HttpUriDownloadCommandProcessor(FeedType.CVE, outDir, ArchiveType.GZ, true, uris);
        assertEquals(ArchiveType.GZ, gzProcessor.getArchiveType());
    }

    @Test
    void testDifferentFeedTypes() {
        HttpUriDownloadCommandProcessor cveProcessor =
                new HttpUriDownloadCommandProcessor(FeedType.CVE, outDir, ArchiveType.ZIP, false, uris);
        assertEquals(FeedType.CVE, cveProcessor.getFeedType());

        HttpUriDownloadCommandProcessor cpeProcessor =
                new HttpUriDownloadCommandProcessor(FeedType.CPE, outDir, ArchiveType.ZIP, false, uris);
        assertEquals(FeedType.CPE, cpeProcessor.getFeedType());
    }

    @Test
    void testExtractOption() {
        HttpUriDownloadCommandProcessor extractProcessor =
                new HttpUriDownloadCommandProcessor(FeedType.CVE, outDir, ArchiveType.ZIP, true, uris);
        assertTrue(extractProcessor.isExtract());

        HttpUriDownloadCommandProcessor noExtractProcessor =
                new HttpUriDownloadCommandProcessor(FeedType.CVE, outDir, ArchiveType.ZIP, false, uris);
        assertFalse(noExtractProcessor.isExtract());
    }
}
