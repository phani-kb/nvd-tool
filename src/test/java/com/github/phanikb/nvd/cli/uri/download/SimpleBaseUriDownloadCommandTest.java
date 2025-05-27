package com.github.phanikb.nvd.cli.uri.download;

import java.io.File;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.github.phanikb.nvd.enums.ArchiveType;
import com.github.phanikb.nvd.enums.FeedType;
import com.github.phanikb.nvd.enums.TransferMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SimpleBaseUriDownloadCommandTest {

    private static class TestBaseUriDownloadCommand extends BaseUriDownloadCommand {
        private final String[] testUrls;
        private final FeedType feedType;

        public TestBaseUriDownloadCommand(String[] urls, FeedType feedType) {
            this.testUrls = urls;
            this.feedType = feedType;
        }

        @Override
        public String[] getUrls() {
            return testUrls;
        }

        @Override
        public FeedType getFeedType() {
            return feedType;
        }

        @Override
        public File getOutDir() {
            return new File("/tmp/test");
        }

        @Override
        public boolean isDeleteTempDir() {
            return true;
        }

        @Override
        public String getOutFilename() {
            return "test.zip";
        }

        @Override
        public boolean isExtract() {
            return true;
        }

        @Override
        public ArchiveType getArchiveType() {
            return ArchiveType.ZIP;
        }
    }

    @Test
    public void testGetUris() {
        String[] testUrls = new String[] {"http://test.com/file1.zip", "http://test.com/file2.zip"};
        TestBaseUriDownloadCommand command = new TestBaseUriDownloadCommand(testUrls, FeedType.CVE);

        Set<URI> uris = command.getUris();

        assertNotNull(uris, "URIs should not be null");
        assertEquals(2, uris.size(), "Should have 2 URIs");

        Set<String> urlStrings = new HashSet<>();
        for (URI uri : uris) {
            urlStrings.add(uri.toString());
        }

        assertTrue(urlStrings.contains("http://test.com/file1.zip"), "First URI should be present");
        assertTrue(urlStrings.contains("http://test.com/file2.zip"), "Second URI should be present");
    }

    @Test
    public void testTransferMethodIsHttp() {
        String[] testUrls = new String[] {"http://test.com/file.zip"};
        TestBaseUriDownloadCommand command = new TestBaseUriDownloadCommand(testUrls, FeedType.CVE);

        assertEquals(TransferMethod.HTTP, command.getTransferMethod(), "Transfer method should be HTTP");
    }

    @Test
    public void testGettersDelegate() {
        String[] testUrls = new String[] {"http://test.com/file.zip"};
        TestBaseUriDownloadCommand command = new TestBaseUriDownloadCommand(testUrls, FeedType.CVE);

        File expectedOutDir = new File("/tmp/test");

        assertEquals(expectedOutDir, command.getOutDir(), "Output directory should be the one specified");
        assertTrue(command.isDeleteTempDir(), "Delete temp directory should be true");
        assertEquals("test.zip", command.getOutFilename(), "Output filename should be test.zip");
        assertTrue(command.isExtract(), "Extract should be true");
        assertEquals(ArchiveType.ZIP, command.getArchiveType(), "Archive type should be ZIP");
    }
}
