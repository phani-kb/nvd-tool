package com.github.phanikb.nvd.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CweIdTest {

    private CweId cweId;

    @BeforeEach
    void setUp() {
        cweId = new CweId();
        assertNotNull(cweId, "CweId instance should be created successfully");
    }

    @Test
    void testParameterizedConstructor() {
        String testId = "CWE-79";
        cweId = new CweId(testId);
        assertEquals(testId, cweId.getId(), "ID should match the value passed to constructor");
    }

    @Test
    void testGetPattern() {
        assertEquals("CWE-\\d+", cweId.getPattern(), "Pattern should match expected regex format");
        assertEquals("CWE-\\d+", CweId.ID_PATTERN, "Class constant should match the pattern");
    }

    @Test
    void testConvertMethod() {
        String testId = "CWE-79";
        CweId converted = cweId.convert(testId);

        assertNotNull(converted, "Converted CweId should not be null");
        assertEquals(testId, converted.getId(), "Converted ID should match input");
    }

    @Test
    void testValidateIdWithValidIds() {
        String[] validIds = {"CWE-1", "CWE-79", "CWE-1000"};

        for (String validId : validIds) {
            cweId.setId(validId);
            assertDoesNotThrow(() -> cweId.validateId(), "Validation should pass for valid CWE ID: " + validId);
        }
    }

    @Test
    void testValidateIdWithNullId() {
        cweId.setId(null);
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> cweId.validateId(),
                "Should throw IllegalArgumentException for null ID");
        assertEquals("name cannot be null", exception.getMessage());
    }

    @Test
    void testValidateIdWithEmptyId() {
        cweId.setId("");
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> cweId.validateId(),
                "Should throw IllegalArgumentException for empty ID");
        assertEquals("name cannot be empty", exception.getMessage());
    }

    @Test
    void testValidateIdWithInvalidIds() {
        String[] invalidIds = {"cwe-79", "CWE79", "CWE-", "CWE-ABC"};

        for (String invalidId : invalidIds) {
            cweId.setId(invalidId);
            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> cweId.validateId(),
                    "Should throw IllegalArgumentException for invalid ID format: " + invalidId);
            assertEquals("Invalid ID, must match pattern " + cweId.getPattern() + ".", exception.getMessage());
        }
    }

    @Test
    void testToString() {
        String testId = "CWE-79";
        cweId.setId(testId);
        String toString = cweId.toString();
        assertTrue(toString.contains(testId), "toString should contain the ID");
    }

    @Test
    void testGetterAndSetter() {
        String testId = "CWE-123";
        cweId.setId(testId);
        assertEquals(testId, cweId.getId(), "Getter should return the value set by setter");
    }
}
