package com.github.phanikb.nvd.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CveIdTest {

    private CveId cveId = new CveId();

    @BeforeEach
    void setUp() {
        cveId = new CveId();
        assertNotNull(cveId, "CveId instance should be created successfully");
    }

    @Test
    void testDefaultConstructor() {
        assertNull(cveId.getId(), "ID should be null when using default constructor");
    }

    @Test
    void testGetPattern() {
        assertEquals("CVE-\\d{4}-\\d{4,}", cveId.getPattern(), "Pattern should match expected regex format");
        assertEquals("CVE-\\d{4}-\\d{4,}", CveId.ID_PATTERN, "Class constant should match the pattern");
    }

    @Test
    void testConvertMethod() {
        String testId = "CVE-2022-1234";
        CveId converted = cveId.convert(testId);

        assertNotNull(converted, "Converted CveId should not be null");
        assertEquals(testId, converted.getId(), "Converted ID should match input");
        assertSame(cveId, converted, "Convert method should return the same instance");
    }

    @Test
    void testValidateIdWithValidIds() {
        String[] validIds = {"CVE-2022-1234", "CVE-2021-12345", "CVE-2020-98765"};

        for (String validId : validIds) {
            cveId.setId(validId);
            assertDoesNotThrow(() -> cveId.validateId(), "Validation should pass for valid CVE ID: " + validId);
        }
    }

    @Test
    void testValidateIdWithNullId() {
        cveId.setId(null);
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> cveId.validateId(),
                "Should throw IllegalArgumentException for null ID");
        assertEquals("name cannot be null", exception.getMessage());
    }

    @Test
    void testValidateIdWithEmptyId() {
        cveId.setId("");
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> cveId.validateId(),
                "Should throw IllegalArgumentException for empty ID");
        assertEquals("name cannot be empty", exception.getMessage());
    }

    @Test
    void testValidateIdWithInvalidIds() {
        String[] invalidIds = {"cve-2022-1234", "CVE2022-1234", "CVE-202-1234", "CVE-2022-123", "CVE-20221234"};

        for (String invalidId : invalidIds) {
            cveId.setId(invalidId);
            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> cveId.validateId(),
                    "Should throw IllegalArgumentException for invalid ID format: " + invalidId);
            assertEquals("Invalid ID, must match pattern " + cveId.getPattern() + ".", exception.getMessage());
        }
    }

    @Test
    void testToString() {
        String testId = "CVE-2022-1234";
        cveId.setId(testId);
        String toString = cveId.toString();
        assertTrue(toString.contains(testId), "toString should contain the ID");
    }

    @Test
    void testGetterAndSetter() {
        String testId = "CVE-2022-5678";
        cveId.setId(testId);
        assertEquals(testId, cveId.getId(), "Getter should return the value set by setter");
    }
}
