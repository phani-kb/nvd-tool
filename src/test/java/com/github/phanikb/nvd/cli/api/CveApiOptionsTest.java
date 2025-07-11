package com.github.phanikb.nvd.cli.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
}
