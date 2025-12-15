package com.github.phanikb.nvd.cli.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class CpeMatchApiOptionsTest {

    @Test
    void testValidateOptionsDoesNotThrow() {
        CpeMatchApiOptions options = new CpeMatchApiOptions();
        assertDoesNotThrow(options::validateOptions);
    }

    @Test
    void testImplementsInterface() {
        CpeMatchApiOptions options = new CpeMatchApiOptions();
        assertInstanceOf(LastModApiOptions.class, options);
    }
}
