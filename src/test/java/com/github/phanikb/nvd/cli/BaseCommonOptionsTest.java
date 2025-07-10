package com.github.phanikb.nvd.cli;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BaseCommonOptionsTest {

    @Test
    void testDefaultValues() {
        BaseCommonOptions options = new BaseCommonOptions();
        assertNull(options.getOutDir());
        assertNull(options.getOutFilename());
    }

    @Test
    void testGettersAndSetters() {
        BaseCommonOptions options = new BaseCommonOptions();

        assertNull(options.getOutDir());
        assertNull(options.getOutFilename());

        assertNotNull(options.toString()); // toString should not throw NPE
    }

    @Test
    void testToString() {
        BaseCommonOptions options = new BaseCommonOptions();
        String toString = options.toString();

        assertNotNull(toString);
        assertTrue(toString.contains("BaseCommonOptions"));
        assertTrue(toString.contains("outDir"));
        assertTrue(toString.contains("outFilename"));
    }

    @Test
    void testClassAnnotations() {
        BaseCommonOptions options = new BaseCommonOptions();

        assertDoesNotThrow(() -> options.getOutDir());
        assertDoesNotThrow(() -> options.getOutFilename());

        assertDoesNotThrow(() -> options.toString());
    }

    @Test
    void testInheritance() {
        BaseCommonOptions options = new BaseCommonOptions();
        assertInstanceOf(Object.class, options);
        assertEquals(BaseCommonOptions.class, options.getClass());
    }
}
