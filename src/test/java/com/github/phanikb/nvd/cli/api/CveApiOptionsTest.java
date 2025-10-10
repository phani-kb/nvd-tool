package com.github.phanikb.nvd.cli.api;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.github.phanikb.nvd.common.CveId;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CveApiOptionsTest {

    @Test
    void testValidateOptionsDoesNotThrow() {
        CveApiOptions options = new CveApiOptions();
        assertDoesNotThrow(() -> options.validateOptions());
    }

    @Test
    void testImplementsInterface() {
        CveApiOptions options = new CveApiOptions();
        assertInstanceOf(LastModApiOptions.class, options);
    }

    @Test
    void testKeywordSearchInvalid() {
        CveApiOptions.KeywordSearch keywordSearch = new CveApiOptions.KeywordSearch();
        keywordSearch.setKeywordExactMatch(true);
        assertTrue(keywordSearch.isInvalid());
    }

    @Test
    void testKeywordSearchValid() {
        CveApiOptions.KeywordSearch keywordSearch = new CveApiOptions.KeywordSearch();
        keywordSearch.setKeywordExactMatch(true);
        keywordSearch.setKeywordSearch("test");
        assertFalse(keywordSearch.isInvalid());
    }

    @Test
    void testCpeVulnerableInvalid() {
        CveApiOptions.CpeVulnerable cpeVulnerable = new CveApiOptions.CpeVulnerable();
        cpeVulnerable.setVulnerable(true);
        assertTrue(cpeVulnerable.isInvalid());
    }

    @Test
    void testCpeVulnerableValid() {
        CveApiOptions.CpeVulnerable cpeVulnerable = new CveApiOptions.CpeVulnerable();
        cpeVulnerable.setCpeName("cpe:2.3:o:linux:linux_kernel:2.6.0:*:*:*:*:*:*:*");
        cpeVulnerable.setVulnerable(true);
        assertFalse(cpeVulnerable.isInvalid());
    }

    @Test
    void testPubDateRangeConstructorAndFields() {
        LocalDateTime start = LocalDateTime.of(2025, 8, 1, 0, 0, 0);
        LocalDateTime end = LocalDateTime.of(2025, 8, 10, 0, 0, 0);
        CveApiOptions.PubDateRange range = new CveApiOptions.PubDateRange(start, end);
        assertEquals(start, range.getPubStartDate());
        assertEquals(end, range.getPubEndDate());
    }

    @Test
    void testPubDateRangeSetters() {
        LocalDateTime start = LocalDateTime.of(2025, 8, 2, 0, 0, 0);
        LocalDateTime end = LocalDateTime.of(2025, 8, 12, 0, 0, 0);
        CveApiOptions.PubDateRange range = new CveApiOptions.PubDateRange(start, end);
        assertEquals(start, range.getPubStartDate());
        assertEquals(end, range.getPubEndDate());
    }

    @Test
    void testIsWithinAllowableRangeTrue() {
        LocalDateTime start = LocalDateTime.of(2025, 8, 1, 0, 0, 0);
        LocalDateTime end = start.plusDays(10);
        CveApiOptions.PubDateRange range = new CveApiOptions.PubDateRange(start, end);
        assertTrue(range.isWithinAllowableRange());
    }

    @Test
    void testIsWithinAllowableRangeFalse() {
        LocalDateTime start = LocalDateTime.of(2025, 8, 1, 0, 0, 0);
        LocalDateTime end = start.plusDays(130); // exceeds DEFAULT_MAX_RANGE_IN_DAYS
        CveApiOptions.PubDateRange range = new CveApiOptions.PubDateRange(start, end);
        assertFalse(range.isWithinAllowableRange());
    }

    @Test
    void testValidateOptionsThrowsForInvalidPubDateRange() {
        CveApiOptions options = new CveApiOptions();
        LocalDateTime start = LocalDateTime.of(2025, 8, 1, 0, 0, 0);
        LocalDateTime end = LocalDateTime.of(2025, 7, 10, 0, 0, 0);
        CveApiOptions.PubDateRange range = new CveApiOptions.PubDateRange(start, end);
        options.setPubDateRange(range);
        assertThrows(IllegalArgumentException.class, options::validateOptions);
    }

    @Test
    void testThrowsIfBothDateRangesInvalid() {
        CveApiOptions options = new CveApiOptions();
        options.setPubDateRange(
                new CveApiOptions.PubDateRange(LocalDateTime.of(2025, 1, 1, 0, 0), LocalDateTime.of(2025, 8, 1, 0, 0)));
        LastModApiOptions.LastModDateRange lastModRange = new LastModApiOptions.LastModDateRange(
                LocalDateTime.of(2025, 1, 1, 0, 0), LocalDateTime.of(2025, 8, 1, 0, 0));
        options.setLastModDateRange(lastModRange);
        assertThrows(IllegalArgumentException.class, options::validateOptions);
    }

    @Test
    void testValidateOptionsThrowsForInvalidKeywordSearch() {
        CveApiOptions options = new CveApiOptions();
        CveApiOptions.KeywordSearch keywordSearch = new CveApiOptions.KeywordSearch();
        keywordSearch.setKeywordExactMatch(true);
        options.setKeywordSearch(keywordSearch);
        assertThrows(IllegalArgumentException.class, options::validateOptions);
    }

    @Test
    void testThrowsIfKeywordSearchInvalid() {
        CveApiOptions options = new CveApiOptions();
        CveApiOptions.KeywordSearch keywordSearch = new CveApiOptions.KeywordSearch();
        keywordSearch.setKeywordExactMatch(true);
        options.setKeywordSearch(keywordSearch);
        assertThrows(IllegalArgumentException.class, options::validateOptions);
    }

    @Test
    void testValidateOptionsThrowsForInvalidCpeVulnerable() {
        CveApiOptions options = new CveApiOptions();
        CveApiOptions.CpeVulnerable cpeVulnerable = new CveApiOptions.CpeVulnerable();
        cpeVulnerable.setVulnerable(true);
        options.setCpeVulnerable(cpeVulnerable);
        assertThrows(IllegalArgumentException.class, options::validateOptions);
    }

    @Test
    void testThrowsIfCpeVulnerableInvalid() {
        CveApiOptions options = new CveApiOptions();
        CveApiOptions.CpeVulnerable cpeVulnerable = new CveApiOptions.CpeVulnerable();
        cpeVulnerable.setVulnerable(true);
        options.setCpeVulnerable(cpeVulnerable);
        assertThrows(IllegalArgumentException.class, options::validateOptions);
    }

    @Test
    void testThrowsIfCpeVulnerableAndVirtualMatchString() {
        CveApiOptions options = new CveApiOptions();
        CveApiOptions.CpeVulnerable cpeVulnerable = new CveApiOptions.CpeVulnerable();
        cpeVulnerable.setCpeName("cpe:2.3:o:linux:linux_kernel:2.6.0:*:*:*:*:*:*:*");
        cpeVulnerable.setVulnerable(true);
        options.setCpeVulnerable(cpeVulnerable);
        options.setVirtualMatchString("test-match");
        assertThrows(IllegalArgumentException.class, options::validateOptions);
    }

    @Test
    void testThrowsIfVersionEndWithoutVirtualMatchString() {
        CveApiOptions options = new CveApiOptions();
        CveApiOptions.VersionEnd versionEnd = new CveApiOptions.VersionEnd(null, "1.0.0");
        options.setVersionEnd(versionEnd);
        assertThrows(IllegalArgumentException.class, options::validateOptions);
    }

    @Test
    void testThrowsIfVersionStartWithoutVirtualMatchString() {
        CveApiOptions options = new CveApiOptions();
        CveApiOptions.VersionStart versionStart = new CveApiOptions.VersionStart(null, "1.0.0");
        options.setVersionStart(versionStart);
        assertThrows(IllegalArgumentException.class, options::validateOptions);
    }

    @Test
    void testThrowsIfVirtualMatchStringWithoutVersionEndOrStart() {
        CveApiOptions options = new CveApiOptions();
        options.setVirtualMatchString("test-match");
        // Only one of versionEnd/versionStart set
        CveApiOptions.VersionEnd versionEnd = new CveApiOptions.VersionEnd(null, "1.0.0");
        options.setVersionEnd(versionEnd);
        assertThrows(IllegalArgumentException.class, options::validateOptions);
    }

    @Test
    void testValidateIdCalledOnCveId() {
        CveApiOptions options = new CveApiOptions();
        CveId cveId = new CveId("CVE-2025-1234");
        options.setCveId(cveId);
        assertDoesNotThrow(options::validateOptions);
    }

    @Test
    void testValidateOptionsDoesNotThrowForValidOptions() {
        CveApiOptions options = new CveApiOptions();
        CveApiOptions.PubDateRange range = new CveApiOptions.PubDateRange(
                LocalDateTime.of(2025, 8, 1, 0, 0, 0), LocalDateTime.of(2025, 8, 9, 0, 0, 0));
        options.setPubDateRange(range);
        CveApiOptions.KeywordSearch keywordSearch = new CveApiOptions.KeywordSearch();
        keywordSearch.setKeywordExactMatch(false);
        keywordSearch.setKeywordSearch("test");
        options.setKeywordSearch(keywordSearch);
        CveApiOptions.CpeVulnerable cpeVulnerable = new CveApiOptions.CpeVulnerable();
        cpeVulnerable.setCpeName("cpe:2.3:o:linux:linux_kernel:2.6.0:*:*:*:*:*:*:*");
        cpeVulnerable.setVulnerable(true);
        options.setCpeVulnerable(cpeVulnerable);
        assertDoesNotThrow(options::validateOptions);
    }
}
