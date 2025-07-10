package com.github.phanikb.nvd.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Cvssv4SeverityTest {

    @Test
    void testAllEnumValues() {
        Cvssv4Severity[] values = Cvssv4Severity.values();
        assertEquals(4, values.length);
        assertEquals(Cvssv4Severity.LOW, values[0]);
        assertEquals(Cvssv4Severity.MEDIUM, values[1]);
        assertEquals(Cvssv4Severity.HIGH, values[2]);
        assertEquals(Cvssv4Severity.CRITICAL, values[3]);
    }

    @Test
    void testLowValue() {
        assertEquals("LOW", Cvssv4Severity.LOW.getValue());
    }

    @Test
    void testMediumValue() {
        assertEquals("MEDIUM", Cvssv4Severity.MEDIUM.getValue());
    }

    @Test
    void testHighValue() {
        assertEquals("HIGH", Cvssv4Severity.HIGH.getValue());
    }

    @Test
    void testCriticalValue() {
        assertEquals("CRITICAL", Cvssv4Severity.CRITICAL.getValue());
    }

    @Test
    void testValueOf() {
        assertEquals(Cvssv4Severity.LOW, Cvssv4Severity.valueOf("LOW"));
        assertEquals(Cvssv4Severity.MEDIUM, Cvssv4Severity.valueOf("MEDIUM"));
        assertEquals(Cvssv4Severity.HIGH, Cvssv4Severity.valueOf("HIGH"));
        assertEquals(Cvssv4Severity.CRITICAL, Cvssv4Severity.valueOf("CRITICAL"));
    }

    @Test
    void testToString() {
        assertEquals("LOW", Cvssv4Severity.LOW.toString());
        assertEquals("MEDIUM", Cvssv4Severity.MEDIUM.toString());
        assertEquals("HIGH", Cvssv4Severity.HIGH.toString());
        assertEquals("CRITICAL", Cvssv4Severity.CRITICAL.toString());
    }
}
