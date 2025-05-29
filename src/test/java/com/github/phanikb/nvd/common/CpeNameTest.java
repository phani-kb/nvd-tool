package com.github.phanikb.nvd.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CpeNameTest {

    private CpeName cpeName;

    @BeforeEach
    public void setUp() {
        cpeName = new CpeName();
        assertNotNull(cpeName, "CpeName instance should be created successfully");
    }

    @Test
    public void testParameterizedConstructor() {
        String testName = "cpe:2.3:a:vendor:product:1.0";
        cpeName = new CpeName(testName);
        assertEquals(testName, cpeName.getName(), "Name should match the value passed to constructor");
    }

    @Test
    public void testConvertMethod() {
        String testName = "cpe:2.3:a:vendor:product:1.0";
        CpeName converted = cpeName.convert(testName);

        assertNotNull(converted, "Converted CpeName should not be null");
        assertEquals(testName, converted.getName(), "Converted name should match input");
        assertNotSame(cpeName, converted, "Convert method should return a new instance");
    }

    @Test
    public void testValidateNameWithValidNames() {
        String[] validNames = {
            "cpe:2.3:a:vendor:product:1.0", "cpe:2.3:o:microsoft:windows:10", "cpe:2.3:a:apache:tomcat:9.0.0.M1"
        };

        for (String validName : validNames) {
            cpeName = new CpeName(validName);
            assertDoesNotThrow(() -> cpeName.validateName(), "Validation should pass for valid CPE name: " + validName);
        }
    }

    @Test
    public void testValidateNameWithNullName() {
        cpeName = new CpeName(null);
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> cpeName.validateName(),
                "Should throw IllegalArgumentException for null name");
        assertEquals("name cannot be null", exception.getMessage());
    }

    @Test
    public void testValidateNameWithEmptyName() {
        cpeName = new CpeName("");
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> cpeName.validateName(),
                "Should throw IllegalArgumentException for empty name");
        assertEquals("name cannot be empty", exception.getMessage());
    }

    @Test
    public void testValidateNameWithInvalidPrefix() {
        String[] invalidPrefixNames = {
            "cpe:3.0:a:vendor:product:1.0", "cpe2.3:a:vendor:product:1.0", "wrongprefix:2.3:a:vendor:product:1.0"
        };

        for (String invalidName : invalidPrefixNames) {
            cpeName = new CpeName(invalidName);
            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> cpeName.validateName(),
                    "Should throw IllegalArgumentException for invalid prefix: " + invalidName);
            assertEquals(
                    "Invalid CPE match string, must start with " + CpeName.CPE_MATCH_PREFIX + ".",
                    exception.getMessage());
        }
    }

    @Test
    public void testValidateNameWithTooFewComponents() {
        String tooFewComponents = "cpe:2.3";
        cpeName = new CpeName(tooFewComponents);
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> cpeName.validateName(),
                "Should throw IllegalArgumentException for too few components");
        assertEquals("Invalid CPE match string, too many/few CPE components.", exception.getMessage());
    }

    @Test
    public void testValidateNameWithTooManyComponents() {
        StringBuilder tooManyComponentsBuilder = new StringBuilder("cpe:2.3");
        for (int i = 0; i < 20; i++) {
            tooManyComponentsBuilder.append(":component").append(i);
        }
        String tooManyComponents = tooManyComponentsBuilder.toString();

        cpeName = new CpeName(tooManyComponents);
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> cpeName.validateName(),
                "Should throw IllegalArgumentException for too many components");
        assertEquals("Invalid CPE match string, too many/few CPE components.", exception.getMessage());
    }

    @Test
    public void testToString() {
        String testName = "cpe:2.3:a:vendor:product:1.0";
        cpeName = new CpeName(testName);
        String toString = cpeName.toString();
        assertTrue(toString.contains(testName), "toString should contain the name");
    }
}
