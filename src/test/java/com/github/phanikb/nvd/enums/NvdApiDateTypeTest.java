package com.github.phanikb.nvd.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NvdApiDateTypeTest {

    @Test
    void testAllEnumValues() {
        NvdApiDateType[] values = NvdApiDateType.values();
        assertEquals(2, values.length);
        assertEquals(NvdApiDateType.START_DATE, values[0]);
        assertEquals(NvdApiDateType.END_DATE, values[1]);
    }

    @Test
    void testStartDateValue() {
        assertEquals("startDate", NvdApiDateType.START_DATE.getName());
    }

    @Test
    void testEndDateValue() {
        assertEquals("endDate", NvdApiDateType.END_DATE.getName());
    }

    @Test
    void testValueOf() {
        assertEquals(NvdApiDateType.START_DATE, NvdApiDateType.valueOf("START_DATE"));
        assertEquals(NvdApiDateType.END_DATE, NvdApiDateType.valueOf("END_DATE"));
    }

    @Test
    void testToString() {
        assertEquals("START_DATE", NvdApiDateType.START_DATE.toString());
        assertEquals("END_DATE", NvdApiDateType.END_DATE.toString());
    }
}
