package com.github.phanikb.nvd.common;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.github.phanikb.nvd.enums.NvdApiDateType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NvdApiDateTest {

    @Test
    public void testConstructorAndGetters() {
        String name = "testDate";
        LocalDateTime now = LocalDateTime.now();
        NvdApiDateType type = NvdApiDateType.START_DATE;

        NvdApiDate nvdApiDate = new NvdApiDate(name, now, type);

        assertEquals(name, nvdApiDate.name(), "Name should match the constructor parameter");
        assertEquals(now, nvdApiDate.value(), "Value should match the constructor parameter");
        assertEquals(type, nvdApiDate.type(), "Type should match the constructor parameter");
    }

    @Test
    public void testEquality() {
        String name = "testDate";
        LocalDateTime now = LocalDateTime.now();
        NvdApiDateType type = NvdApiDateType.START_DATE;

        NvdApiDate date1 = new NvdApiDate(name, now, type);
        NvdApiDate date2 = new NvdApiDate(name, now, type);
        NvdApiDate date3 = new NvdApiDate("differentName", now, type);

        assertEquals(date1, date2, "Equal NvdApiDate instances should be equal");
        assertNotEquals(date1, date3, "NvdApiDate instances with different names should not be equal");
        assertEquals(date1.hashCode(), date2.hashCode(), "Equal NvdApiDate instances should have same hashcode");
    }

    @Test
    public void testToString() {
        String name = "testDate";
        LocalDateTime now = LocalDateTime.now();
        NvdApiDateType type = NvdApiDateType.END_DATE;

        NvdApiDate nvdApiDate = new NvdApiDate(name, now, type);
        String toString = nvdApiDate.toString();

        assertTrue(toString.contains(name), "toString should contain the name");
        assertTrue(toString.contains(type.toString()), "toString should contain the type");
    }

    @Test
    public void testWithStartDateType() {
        String name = "startDate";
        LocalDateTime now = LocalDateTime.now();
        NvdApiDateType type = NvdApiDateType.START_DATE;

        NvdApiDate nvdApiDate = new NvdApiDate(name, now, type);
        assertEquals(NvdApiDateType.START_DATE, nvdApiDate.type(), "Type should be START_DATE");
    }

    @Test
    public void testWithEndDateType() {
        String name = "endDate";
        LocalDateTime now = LocalDateTime.now();
        NvdApiDateType type = NvdApiDateType.END_DATE;

        NvdApiDate nvdApiDate = new NvdApiDate(name, now, type);
        assertEquals(NvdApiDateType.END_DATE, nvdApiDate.type(), "Type should be END_DATE");
    }
}
