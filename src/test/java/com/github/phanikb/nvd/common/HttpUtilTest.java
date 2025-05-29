package com.github.phanikb.nvd.common;

import java.net.URI;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class HttpUtilTest {

    @Test
    public void testGetFilenameWithValidPath() {
        URI uri = URI.create("https://example.com/path/to/file.json");
        String filename = HttpUtil.getFilename(uri);
        assertEquals("file.json", filename, "Filename should be correctly extracted from URI");
    }

    @Test
    public void testGetFilenameWithNoPath() {
        URI uri = URI.create("https://example.com");
        String filename = HttpUtil.getFilename(uri);
        assertNull(filename, "Filename should be null when URI has no path");
    }

    @Test
    public void testGetFilenameWithEmptyPath() {
        URI uri = URI.create("https://example.com/");
        String filename = HttpUtil.getFilename(uri);
        assertEquals("", filename, "Filename should be empty when URI path ends with a slash");
    }

    @Test
    public void testGetFilenameWithQueryParams() {
        URI uri = URI.create("https://example.com/path/to/file.json?param=value");
        String filename = HttpUtil.getFilename(uri);
        assertEquals("file.json", filename, "Filename should be extracted correctly ignoring query parameters");
    }

    @Test
    public void testGetFilenameWithFragment() {
        URI uri = URI.create("https://example.com/path/to/file.json#section");
        String filename = HttpUtil.getFilename(uri);
        assertEquals("file.json", filename, "Filename should be extracted correctly ignoring fragments");
    }
}
