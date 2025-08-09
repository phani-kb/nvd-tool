package com.github.phanikb.nvd.cli.api;

import org.junit.jupiter.api.Test;

import com.github.phanikb.nvd.common.CpeName;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    void testThrowsIfKeywordSearchInvalid() {
        CpeApiOptions options = new CpeApiOptions();
        CpeApiOptions.KeywordSearch keywordSearch = new CpeApiOptions.KeywordSearch();
        keywordSearch.setKeywordExactMatch(true);
        options.setKeywordSearch(keywordSearch);
        assertThrows(IllegalArgumentException.class, options::validateOptions);
    }

    @Test
    void testCpeMatchNameValid() {
        CpeApiOptions options = new CpeApiOptions();
        CpeApiOptions.KeywordSearch keywordSearch = new CpeApiOptions.KeywordSearch();
        keywordSearch.setKeywordExactMatch(true);
        keywordSearch.setKeywordSearch("test");
        options.setKeywordSearch(keywordSearch);
        CpeName cpeName = new CpeName("cpe:2.3:o:linux:linux_kernel:2.6.0:*:*:*:*:*:*:*");
        options.setCpeMatchString(cpeName);
        assertDoesNotThrow(options::validateOptions);
    }
}
