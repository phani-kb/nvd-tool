package com.github.phanikb.nvd.cli.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CpeApiOptionsTest {

    @Test
    void testValidateOptionsDoesNotThrow() {
        CpeApiOptions options = new CpeApiOptions();
        assertDoesNotThrow(() -> options.validateOptions());
    }

    @Test
    void testImplementsInterface() {
        CpeApiOptions options = new CpeApiOptions();
        assertInstanceOf(LastModApiOptions.class, options);
    }

    @Test
    void testKeywordSearchInvalid() {
        CpeApiOptions.KeywordSearch keywordSearch = new CpeApiOptions.KeywordSearch();
        keywordSearch.setKeywordExactMatch(true);
        assertTrue(keywordSearch.isInvalid());
    }

    @Test
    void testKeywordSearchValid() {
        CpeApiOptions.KeywordSearch keywordSearch = new CpeApiOptions.KeywordSearch();
        keywordSearch.setKeywordExactMatch(true);
        keywordSearch.setKeywordSearch("test");
        assertFalse(keywordSearch.isInvalid());
    }
}
