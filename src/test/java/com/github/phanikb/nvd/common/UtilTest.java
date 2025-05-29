package com.github.phanikb.nvd.common;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UtilTest {

    @Test
    public void testIsNullOrEmpty() {
        assertTrue(Util.isNullOrEmpty(null), "null should be considered empty");
        assertTrue(Util.isNullOrEmpty(""), "Empty string should be considered empty");
        assertFalse(Util.isNullOrEmpty("text"), "Non-empty string should not be considered empty");
    }

    @Test
    public void testLoadFileFromClasspath() {
        Optional<InputStream> result = Util.loadFileFromClasspath("cli.defaults.properties");
        assertTrue(result.isPresent(), "Should find cli.defaults.properties in classpath");

        result = Util.loadFileFromClasspath("non-existent-file.txt");
        assertFalse(result.isPresent(), "Should not find non-existent file in classpath");
    }

    @Test
    public void testFindFileFromClasspathWithNullOrEmpty() {
        Optional<File> result = Util.findFileFromClasspath(null);
        assertFalse(result.isPresent(), "Should return empty Optional for null filename");

        result = Util.findFileFromClasspath("");
        assertFalse(result.isPresent(), "Should return empty Optional for empty filename");
    }

    @Test
    public void testFindFileFromClasspath() {
        Optional<File> result = Util.findFileFromClasspath("cli.defaults.properties");

        if (result.isPresent()) {
            assertTrue(result.get().exists(), "File should exist");
            assertEquals("cli.defaults.properties", result.get().getName(), "File name should match");
        }

        result = Util.findFileFromClasspath("non-existent-file.txt");
        assertFalse(result.isPresent(), "Should not find non-existent file in classpath");
    }

    @Test
    public void testGetUsableSpace() throws IOException {
        Path tempDir = Files.createTempDirectory("util-test");
        File dir = tempDir.toFile();

        try {
            int space = Util.getUsableSpace(dir);
            assertTrue(space >= 0, "Usable space should be non-negative");
        } finally {
            Files.deleteIfExists(tempDir);
        }
    }
}
