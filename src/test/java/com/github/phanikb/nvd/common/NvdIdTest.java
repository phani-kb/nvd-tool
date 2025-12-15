package com.github.phanikb.nvd.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NvdIdTest {

    private static class TestNvdId extends NvdId {
        private final String id;
        private final String pattern;

        TestNvdId(String id, String pattern) {
            this.id = id;
            this.pattern = pattern;
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public String getPattern() {
            return pattern;
        }
    }

    @Test
    void testValidateIdWithValidId() {
        TestNvdId nvdId = new TestNvdId("CVE-2023-1234", "CVE-\\d{4}-\\d{4,}");

        nvdId.validateId();
    }

    @Test
    void testValidateIdWithNullId() {
        TestNvdId nvdId = new TestNvdId(null, "CVE-\\d{4}-\\d{4,}");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, nvdId::validateId);
        assertEquals("name cannot be null", exception.getMessage());
    }

    @Test
    void testValidateIdWithEmptyId() {
        TestNvdId nvdId = new TestNvdId("", "CVE-\\d{4}-\\d{4,}");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, nvdId::validateId);
        assertEquals("name cannot be empty", exception.getMessage());
    }

    @Test
    void testValidateIdWithInvalidId() {
        TestNvdId nvdId = new TestNvdId("INVALID-123", "CVE-\\d{4}-\\d{4,}");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, nvdId::validateId);
        assertEquals("Invalid ID, must match pattern CVE-\\d{4}-\\d{4,}.", exception.getMessage());
    }

    @Test
    void testValidateIdWithDifferentPattern() {
        TestNvdId nvdId = new TestNvdId("CWE-123", "CWE-\\d+");

        nvdId.validateId();
    }

    @Test
    void testGetIdAndGetPattern() {
        String testId = "TEST-123";
        String testPattern = "TEST-\\d+";
        TestNvdId nvdId = new TestNvdId(testId, testPattern);

        assertEquals(testId, nvdId.getId());
        assertEquals(testPattern, nvdId.getPattern());
    }
}
