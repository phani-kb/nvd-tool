package com.github.phanikb.nvd.cli.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CveHistoryApiOptionsTest {

    @Test
    void testValidateOptionsDoesNotThrow() {
        CveHistoryApiOptions options = new CveHistoryApiOptions();
        assertDoesNotThrow(() -> options.validateOptions());
    }

    @Test
    void testImplementsInterface() {
        CveHistoryApiOptions options = new CveHistoryApiOptions();
        assertInstanceOf(BaseApiOptions.class, options);
    }
}
