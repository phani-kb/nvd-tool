package com.github.phanikb.nvd.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CveDownloadTypeTest {

    @Test
    void testAllEnumValues() {
        CveDownloadType[] values = CveDownloadType.values();
        assertEquals(3, values.length);
        assertEquals(CveDownloadType.FULL, values[0]);
        assertEquals(CveDownloadType.MODIFIED, values[1]);
        assertEquals(CveDownloadType.RECENT, values[2]);
    }

    @Test
    void testFullValue() {
        assertEquals("full", CveDownloadType.FULL.getValue());
    }

    @Test
    void testModifiedValue() {
        assertEquals("modified", CveDownloadType.MODIFIED.getValue());
    }

    @Test
    void testRecentValue() {
        assertEquals("recent", CveDownloadType.RECENT.getValue());
    }

    @Test
    void testValueOf() {
        assertEquals(CveDownloadType.FULL, CveDownloadType.valueOf("FULL"));
        assertEquals(CveDownloadType.MODIFIED, CveDownloadType.valueOf("MODIFIED"));
        assertEquals(CveDownloadType.RECENT, CveDownloadType.valueOf("RECENT"));
    }

    @Test
    void testToString() {
        assertEquals("FULL", CveDownloadType.FULL.toString());
        assertEquals("MODIFIED", CveDownloadType.MODIFIED.toString());
        assertEquals("RECENT", CveDownloadType.RECENT.toString());
    }
}
