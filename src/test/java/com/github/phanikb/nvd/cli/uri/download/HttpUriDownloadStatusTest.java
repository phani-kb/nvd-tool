package com.github.phanikb.nvd.cli.uri.download;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HttpUriDownloadStatusTest {

    @Test
    void testConstructorWithValidUri() throws URISyntaxException {
        URI uri = new URI("https://example.com/test");
        HttpUriDownloadStatus status = new HttpUriDownloadStatus(uri, true);

        assertEquals(uri, status.getUri());
        assertTrue(status.isSuccess());
        assertNull(status.getFilename());
        assertEquals(0, status.getSize());
        assertNull(status.getMessage());
        assertEquals(0, status.getStatusCode());
    }

    @Test
    void testConstructorWithNullUri() {
        assertThrows(NullPointerException.class, () -> new HttpUriDownloadStatus(null, true));
    }

    @Test
    void testSettersAndGetters() throws URISyntaxException {
        URI uri = new URI("https://example.com/test");
        HttpUriDownloadStatus status = new HttpUriDownloadStatus(uri, false);

        status.setFilename("test.json");
        status.setSize(1024L);
        status.setMessage("Download failed");
        status.setStatusCode(404);
        status.setSuccess(true);

        assertEquals("test.json", status.getFilename());
        assertEquals(1024L, status.getSize());
        assertEquals("Download failed", status.getMessage());
        assertEquals(404, status.getStatusCode());
        assertTrue(status.isSuccess());
    }

    @Test
    void testHashCode() throws URISyntaxException {
        URI uri1 = new URI("https://example.com/test");
        URI uri2 = new URI("https://example.com/test");
        URI uri3 = new URI("https://example.com/different");

        HttpUriDownloadStatus status1 = new HttpUriDownloadStatus(uri1, true);
        HttpUriDownloadStatus status2 = new HttpUriDownloadStatus(uri2, false);
        HttpUriDownloadStatus status3 = new HttpUriDownloadStatus(uri3, true);

        assertEquals(status1.hashCode(), status2.hashCode());
        assertNotEquals(status1.hashCode(), status3.hashCode());
        assertEquals(uri1.hashCode(), status1.hashCode());
    }

    @Test
    void testEquals() throws URISyntaxException {
        URI uri1 = new URI("https://example.com/test");
        URI uri2 = new URI("https://example.com/test");
        URI uri3 = new URI("https://example.com/different");

        HttpUriDownloadStatus status1 = new HttpUriDownloadStatus(uri1, true);
        HttpUriDownloadStatus status2 = new HttpUriDownloadStatus(uri2, false);
        HttpUriDownloadStatus status3 = new HttpUriDownloadStatus(uri3, true);

        assertEquals(status1, status2);
        assertEquals(status2, status1);
        assertNotEquals(status1, status3);
        assertNotEquals(status3, status1);
        assertNotEquals(null, status1);
        assertNotEquals("not a status object", status1.toString());
    }

    @Test
    void testToString() throws URISyntaxException {
        URI uri = new URI("https://example.com/test");
        HttpUriDownloadStatus status = new HttpUriDownloadStatus(uri, true);
        status.setFilename("test.json");
        status.setSize(1024L);

        String toString = status.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("HttpUriDownloadStatus"));
    }

    @Test
    void testLombokAnnotations() throws URISyntaxException {
        URI uri = new URI("https://example.com/test");
        HttpUriDownloadStatus status = new HttpUriDownloadStatus(uri, true);

        // Test @Getter annotations work
        assertDoesNotThrow(status::getUri);
        assertDoesNotThrow(status::getFilename);
        assertDoesNotThrow(status::getSize);
        assertDoesNotThrow(status::getMessage);
        assertDoesNotThrow(status::getStatusCode);
        assertDoesNotThrow(status::isSuccess);

        // Test @Setter annotations work
        assertDoesNotThrow(() -> status.setFilename("test"));
        assertDoesNotThrow(() -> status.setSize(100));
        assertDoesNotThrow(() -> status.setMessage("test"));
        assertDoesNotThrow(() -> status.setStatusCode(200));
        assertDoesNotThrow(() -> status.setSuccess(false));

        // Test @ToString annotation works
        assertDoesNotThrow(status::toString);
    }

    @Test
    void testBooleanSuccess() throws URISyntaxException {
        URI uri = new URI("https://example.com/test");

        HttpUriDownloadStatus successStatus = new HttpUriDownloadStatus(uri, true);
        assertTrue(successStatus.isSuccess());

        HttpUriDownloadStatus failureStatus = new HttpUriDownloadStatus(uri, false);
        assertFalse(failureStatus.isSuccess());

        // Test that success can be changed
        failureStatus.setSuccess(true);
        assertTrue(failureStatus.isSuccess());
    }
}
