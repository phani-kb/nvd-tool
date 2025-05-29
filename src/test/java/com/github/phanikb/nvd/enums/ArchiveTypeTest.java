package com.github.phanikb.nvd.enums;

import java.io.File;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArchiveTypeTest {
    @Test
    void testOfReturnsCorrectType() {
        assertEquals(ArchiveType.ZIP, ArchiveType.of(".zip"));
        assertEquals(ArchiveType.GZ, ArchiveType.of(".gz"));
        assertEquals(ArchiveType.UNKNOWN, ArchiveType.of(".rar"));
    }

    @Test
    void testGetExtension() {
        assertEquals(".zip", ArchiveType.ZIP.getExtension());
        assertEquals(".gz", ArchiveType.GZ.getExtension());
        assertEquals("", ArchiveType.UNKNOWN.getExtension());
    }

    @Test
    void testExtractUnknownThrows() {
        File dummy = new File("dummy");
        Exception ex = assertThrows(IllegalArgumentException.class, () -> ArchiveType.UNKNOWN.extract(dummy, dummy));
        assertTrue(ex.getMessage().contains("unsupported file type"));
    }

    @Test
    void testArchiveUnknownThrows() {
        File dummy = new File("dummy");
        Exception ex = assertThrows(IllegalArgumentException.class, () -> ArchiveType.UNKNOWN.archive(dummy, dummy));
        assertTrue(ex.getMessage().contains("unsupported archive type"));
    }
}
