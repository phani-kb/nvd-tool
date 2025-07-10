package com.github.phanikb.nvd.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Cvssv2SeverityTest {

    @Test
    void testAllEnumValues() {
        Cvssv2Severity[] values = Cvssv2Severity.values();
        assertEquals(3, values.length);
        assertEquals(Cvssv2Severity.LOW, values[0]);
        assertEquals(Cvssv2Severity.MEDIUM, values[1]);
        assertEquals(Cvssv2Severity.HIGH, values[2]);
    }

    @Test
    void testLowValue() {
        assertEquals("LOW", Cvssv2Severity.LOW.getValue());
    }

    @Test
    void testMediumValue() {
        assertEquals("MEDIUM", Cvssv2Severity.MEDIUM.getValue());
    }

    @Test
    void testHighValue() {
        assertEquals("HIGH", Cvssv2Severity.HIGH.getValue());
    }

    @Test
    void testValueOf() {
        assertEquals(Cvssv2Severity.LOW, Cvssv2Severity.valueOf("LOW"));
        assertEquals(Cvssv2Severity.MEDIUM, Cvssv2Severity.valueOf("MEDIUM"));
        assertEquals(Cvssv2Severity.HIGH, Cvssv2Severity.valueOf("HIGH"));
    }

    @Test
    void testToString() {
        assertEquals("LOW", Cvssv2Severity.LOW.toString());
        assertEquals("MEDIUM", Cvssv2Severity.MEDIUM.toString());
        assertEquals("HIGH", Cvssv2Severity.HIGH.toString());
    }
}
