package com.github.phanikb.nvd.cli.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LastModApiOptionsTest {

    private static class TestableLastModApiOptions extends LastModApiOptions {}

    @Test
    void testValidateOptionsDoesNotThrow() {
        TestableLastModApiOptions options = new TestableLastModApiOptions();
        assertDoesNotThrow(() -> options.validateOptions());
    }

    @Test
    void testImplementsInterface() {
        TestableLastModApiOptions options = new TestableLastModApiOptions();
        assertInstanceOf(BaseApiOptions.class, options);
    }
}
