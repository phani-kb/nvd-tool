package com.github.phanikb.nvd.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FeedTypeTest {

    @Test
    void testAllEnumValues() {
        FeedType[] values = FeedType.values();
        assertEquals(5, values.length);
        assertEquals(FeedType.CVE, values[0]);
        assertEquals(FeedType.CVE_HISTORY, values[1]);
        assertEquals(FeedType.CPE, values[2]);
        assertEquals(FeedType.CPE_MATCH, values[3]);
        assertEquals(FeedType.CWE, values[4]);
    }

    @Test
    void testCveEnumValues() {
        assertEquals("cve", FeedType.CVE.getName());
        assertEquals("vulnerabilities", FeedType.CVE.getCollectionNodeName());
    }

    @Test
    void testCveHistoryEnumValues() {
        assertEquals("cve-history", FeedType.CVE_HISTORY.getName());
        assertEquals("cveChanges", FeedType.CVE_HISTORY.getCollectionNodeName());
    }

    @Test
    void testCpeEnumValues() {
        assertEquals("cpe", FeedType.CPE.getName());
        assertEquals("products", FeedType.CPE.getCollectionNodeName());
    }

    @Test
    void testCpeMatchEnumValues() {
        assertEquals("cpe-match", FeedType.CPE_MATCH.getName());
        assertEquals("matchStrings", FeedType.CPE_MATCH.getCollectionNodeName());
    }

    @Test
    void testCweEnumValues() {
        assertEquals("cwe", FeedType.CWE.getName());
        assertNull(FeedType.CWE.getCollectionNodeName());
    }

    @Test
    void testValueOf() {
        assertEquals(FeedType.CVE, FeedType.valueOf("CVE"));
        assertEquals(FeedType.CVE_HISTORY, FeedType.valueOf("CVE_HISTORY"));
        assertEquals(FeedType.CPE, FeedType.valueOf("CPE"));
        assertEquals(FeedType.CPE_MATCH, FeedType.valueOf("CPE_MATCH"));
        assertEquals(FeedType.CWE, FeedType.valueOf("CWE"));
    }
}
