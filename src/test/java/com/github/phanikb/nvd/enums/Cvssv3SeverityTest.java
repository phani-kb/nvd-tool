package com.github.phanikb.nvd.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Cvssv3SeverityTest {

    @Test
    void testAllEnumValues() {
        Cvssv3Severity[] values = Cvssv3Severity.values();
        assertEquals(4, values.length);
        assertEquals(Cvssv3Severity.LOW, values[0]);
        assertEquals(Cvssv3Severity.MEDIUM, values[1]);
        assertEquals(Cvssv3Severity.HIGH, values[2]);
        assertEquals(Cvssv3Severity.CRITICAL, values[3]);
    }

    @Test
    void testLowValue() {
        assertEquals("LOW", Cvssv3Severity.LOW.getValue());
    }

    @Test
    void testMediumValue() {
        assertEquals("MEDIUM", Cvssv3Severity.MEDIUM.getValue());
    }

    @Test
    void testHighValue() {
        assertEquals("HIGH", Cvssv3Severity.HIGH.getValue());
    }

    @Test
    void testCriticalValue() {
        assertEquals("CRITICAL", Cvssv3Severity.CRITICAL.getValue());
    }

    @Test
    void testValueOf() {
        assertEquals(Cvssv3Severity.LOW, Cvssv3Severity.valueOf("LOW"));
        assertEquals(Cvssv3Severity.MEDIUM, Cvssv3Severity.valueOf("MEDIUM"));
        assertEquals(Cvssv3Severity.HIGH, Cvssv3Severity.valueOf("HIGH"));
        assertEquals(Cvssv3Severity.CRITICAL, Cvssv3Severity.valueOf("CRITICAL"));
    }

    @Test
    void testToString() {
        assertEquals("LOW", Cvssv3Severity.LOW.toString());
        assertEquals("MEDIUM", Cvssv3Severity.MEDIUM.toString());
        assertEquals("HIGH", Cvssv3Severity.HIGH.toString());
        assertEquals("CRITICAL", Cvssv3Severity.CRITICAL.toString());
    }
}
