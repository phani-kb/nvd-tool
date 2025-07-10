package com.github.phanikb.nvd.common;

import java.net.InetSocketAddress;
import java.net.URI;
import java.util.List;

import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicHeader;
import org.apache.hc.core5.util.TimeValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    public void testGetUserAgent() {
        String userAgent = HttpUtil.getUserAgent();
        assertNotNull(userAgent, "User agent should not be null");
        assertTrue(userAgent.contains("nvd-tool"), "User agent should contain project name");
        assertTrue(userAgent.contains("1.0-SNAPSHOT"), "User agent should contain version");
    }

    @Test
    public void testGetDownloadDelay() {
        TimeValue delay = HttpUtil.getDownloadDelay();
        assertNotNull(delay, "Download delay should not be null");
        assertTrue(delay.toMilliseconds() > 0, "Download delay should be positive");
    }

    @Test
    public void testGetRateLimit() {
        int rateLimit = HttpUtil.getRateLimit();
        assertTrue(rateLimit > 0, "Rate limit should be positive");
    }

    @Test
    public void testGetRollingWindowSizeInSecs() {
        int windowSize = HttpUtil.getRollingWindowSizeInSecs();
        assertTrue(windowSize > 0, "Rolling window size should be positive");
    }

    @Test
    public void testGetMaxRetries() {
        int maxRetries = HttpUtil.getMaxRetries();
        assertTrue(maxRetries >= 0, "Max retries should be non-negative");
    }

    @Test
    public void testGetRetryIntervalInSecs() {
        int retryInterval = HttpUtil.getRetryIntervalInSecs();
        assertTrue(retryInterval > 0, "Retry interval should be positive");
    }

    @Test
    public void testGetNvdDefaultHeaders() {
        List<BasicHeader> headers = HttpUtil.getNvdDefaultHeaders();
        assertNotNull(headers, "Headers list should not be null");
        assertTrue(headers.size() >= 3, "Should have at least 3 default headers");

        boolean hasAccept = headers.stream().anyMatch(h -> "Accept".equals(h.getName()));
        boolean hasUserAgent = headers.stream().anyMatch(h -> "User-Agent".equals(h.getName()));
        boolean hasContentType = headers.stream().anyMatch(h -> "Content-Type".equals(h.getName()));

        assertTrue(hasAccept, "Should have Accept header");
        assertTrue(hasUserAgent, "Should have User-Agent header");
        assertTrue(hasContentType, "Should have Content-Type header");
    }

    @Test
    public void testGetNvdDefaultParams() {
        List<NameValuePair> params = HttpUtil.getNvdDefaultParams(100);
        assertNotNull(params, "Params list should not be null");
        assertTrue(params.size() >= 1, "Should have at least 1 default parameter");

        boolean hasResultsPerPage = params.stream().anyMatch(p -> "resultsPerPage".equals(p.getName()));
        assertTrue(hasResultsPerPage, "Should have resultsPerPage parameter");

        String rppValue = params.stream()
                .filter(p -> "resultsPerPage".equals(p.getName()))
                .findFirst()
                .map(NameValuePair::getValue)
                .orElse("");
        assertEquals("100", rppValue, "resultsPerPage should be set to 100");
    }

    @Test
    public void testGetProxy() {
        InetSocketAddress proxy = HttpUtil.getProxy();
        if (proxy != null) {
            assertNotNull(proxy.getHostString(), "Proxy host should not be null if proxy is configured");
            assertTrue(proxy.getPort() > 0, "Proxy port should be positive if proxy is configured");
        }
    }
}
