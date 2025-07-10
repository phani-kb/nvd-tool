package com.github.phanikb.nvd.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CveHistoryEventNameTest {

    @Test
    void testAllEnumValues() {
        CveHistoryEventName[] values = CveHistoryEventName.values();
        assertEquals(13, values.length);
        assertEquals(CveHistoryEventName.CVE_RECEIVED, values[0]);
        assertEquals(CveHistoryEventName.INITIAL_ANALYSIS, values[1]);
        assertEquals(CveHistoryEventName.REANALYSIS, values[2]);
        assertEquals(CveHistoryEventName.CVE_MODIFIED, values[3]);
        assertEquals(CveHistoryEventName.MODIFIED_ANALYSIS, values[4]);
        assertEquals(CveHistoryEventName.CVE_TRANSLATED, values[5]);
        assertEquals(CveHistoryEventName.VENDOR_COMMENT, values[6]);
        assertEquals(CveHistoryEventName.CVE_SOURCE_UPDATE, values[7]);
        assertEquals(CveHistoryEventName.CPE_DR, values[8]);
        assertEquals(CveHistoryEventName.CWE_REMAP, values[9]);
        assertEquals(CveHistoryEventName.CVE_REJECTED, values[10]);
        assertEquals(CveHistoryEventName.CVE_UNREJECTED, values[11]);
        assertEquals(CveHistoryEventName.CVE_CISA_KEV_UPDATE, values[12]);
    }

    @Test
    void testEventValues() {
        assertEquals("CVE Received", CveHistoryEventName.CVE_RECEIVED.getValue());
        assertEquals("Initial Analysis", CveHistoryEventName.INITIAL_ANALYSIS.getValue());
        assertEquals("Reanalysis", CveHistoryEventName.REANALYSIS.getValue());
        assertEquals("CVE Modified", CveHistoryEventName.CVE_MODIFIED.getValue());
        assertEquals("Modified Analysis", CveHistoryEventName.MODIFIED_ANALYSIS.getValue());
        assertEquals("CVE Translated", CveHistoryEventName.CVE_TRANSLATED.getValue());
        assertEquals("Vendor Comment", CveHistoryEventName.VENDOR_COMMENT.getValue());
        assertEquals("CVE Source Update", CveHistoryEventName.CVE_SOURCE_UPDATE.getValue());
        assertEquals(" CPE Deprecation Remap", CveHistoryEventName.CPE_DR.getValue());
        assertEquals("CWE Remap", CveHistoryEventName.CWE_REMAP.getValue());
        assertEquals("CVE Rejected", CveHistoryEventName.CVE_REJECTED.getValue());
        assertEquals("CVE Unrejected", CveHistoryEventName.CVE_UNREJECTED.getValue());
        assertEquals("CVE CISA KEV Update", CveHistoryEventName.CVE_CISA_KEV_UPDATE.getValue());
    }

    @Test
    void testValueOf() {
        assertEquals(CveHistoryEventName.CVE_RECEIVED, CveHistoryEventName.valueOf("CVE_RECEIVED"));
        assertEquals(CveHistoryEventName.INITIAL_ANALYSIS, CveHistoryEventName.valueOf("INITIAL_ANALYSIS"));
        assertEquals(CveHistoryEventName.CVE_MODIFIED, CveHistoryEventName.valueOf("CVE_MODIFIED"));
        assertEquals(CveHistoryEventName.CVE_CISA_KEV_UPDATE, CveHistoryEventName.valueOf("CVE_CISA_KEV_UPDATE"));
    }

    @Test
    void testToString() {
        assertEquals("CVE_RECEIVED", CveHistoryEventName.CVE_RECEIVED.toString());
        assertEquals("INITIAL_ANALYSIS", CveHistoryEventName.INITIAL_ANALYSIS.toString());
        assertEquals("CVE_MODIFIED", CveHistoryEventName.CVE_MODIFIED.toString());
        assertEquals("CVE_CISA_KEV_UPDATE", CveHistoryEventName.CVE_CISA_KEV_UPDATE.toString());
    }
}
