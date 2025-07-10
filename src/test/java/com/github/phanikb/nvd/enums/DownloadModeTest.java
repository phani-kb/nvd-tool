package com.github.phanikb.nvd.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DownloadModeTest {

    @Test
    void testAllEnumValues() {
        DownloadMode[] values = DownloadMode.values();
        assertEquals(2, values.length);
        assertEquals(DownloadMode.API, values[0]);
        assertEquals(DownloadMode.URI, values[1]);
    }

    @Test
    void testApiValue() {
        assertEquals("api", DownloadMode.API.getName());
    }

    @Test
    void testUriValue() {
        assertEquals("uri", DownloadMode.URI.getName());
    }

    @Test
    void testValueOf() {
        assertEquals(DownloadMode.API, DownloadMode.valueOf("API"));
        assertEquals(DownloadMode.URI, DownloadMode.valueOf("URI"));
    }

    @Test
    void testToString() {
        assertEquals("API", DownloadMode.API.toString());
        assertEquals("URI", DownloadMode.URI.toString());
    }
}
