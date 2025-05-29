package com.github.phanikb.nvd.common;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DateFormatsTest {

    @Test
    public void testConstants() {
        assertNotNull(DateFormats.TODAY, "TODAY should not be null");
        assertNotNull(DateFormats.TODAY_DATETIME, "TODAY_DATETIME should not be null");
        assertTrue(DateFormats.CURRENT_YEAR >= 2002, "CURRENT_YEAR should be at least 2002");
        assertEquals(2002, DateFormats.START_YEAR, "START_YEAR should be 2002");
        assertEquals(2024, DateFormats.END_YEAR, "END_YEAR should be 2024");
        assertNotNull(DateFormats.ISO_DATE_TIME_EXT_FORMATTER, "ISO_DATE_TIME_EXT_FORMATTER should not be null");
    }

    @Test
    public void testDateFormatDefault() {
        DateFormats.DateFormat format = DateFormats.DateFormat.DEFAULT;
        assertEquals("yyyy-MM-dd", format.getPattern(), "DEFAULT pattern should be yyyy-MM-dd");

        LocalDateTime testDateTime = LocalDateTime.of(2023, 5, 15, 10, 30);
        String formattedDate = format.format(testDateTime);
        assertEquals("2023-05-15", formattedDate, "Formatting should match the expected pattern");
    }

    @Test
    public void testDateFormatOutputFileNameSuffix() {
        DateFormats.DateFormat format = DateFormats.DateFormat.OUTPUT_FILE_NAME_SUFFIX;
        assertEquals("yyyyMMdd", format.getPattern(), "OUTPUT_FILE_NAME_SUFFIX pattern should be yyyyMMdd");

        LocalDateTime testDateTime = LocalDateTime.of(2023, 5, 15, 10, 30);
        String formattedDate = format.format(testDateTime);
        assertEquals("20230515", formattedDate, "Formatting should match the expected pattern");
    }

    @Test
    public void testDateFormatIsoDateTimeExt() {
        DateFormats.DateFormat format = DateFormats.DateFormat.ISO_DATE_TIME_EXT;
        assertEquals(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                format.getPattern(),
                "ISO_DATE_TIME_EXT pattern should be yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        LocalDateTime testDateTime = LocalDateTime.of(2023, 5, 15, 10, 30, 45, 123000000);
        String formattedDate = format.format(testDateTime);
        assertEquals("2023-05-15T10:30:45.123Z", formattedDate, "Formatting should match the expected pattern");
    }

    @Test
    public void testDateFormatWithEndOfDay() {
        DateFormats.DateFormat format = DateFormats.DateFormat.DEFAULT;

        LocalDateTime testDateTime = LocalDateTime.of(2023, 5, 15, 10, 30);
        String formattedDateEndOfDay = format.format(testDateTime, true);
        assertEquals("2023-05-15", formattedDateEndOfDay, "End of day formatting should match the expected pattern");

        format = DateFormats.DateFormat.ISO_DATE_TIME_EXT;
        testDateTime = LocalDateTime.of(2023, 5, 15, 10, 30);
        formattedDateEndOfDay = format.format(testDateTime, true);
        assertTrue(
                formattedDateEndOfDay.startsWith("2023-05-15T23:59:59"),
                "End of day formatting should set time to 23:59:59");
    }

    @Test
    public void testDateTimeFormatter() {
        DateFormats.DateFormat format = DateFormats.DateFormat.DEFAULT;
        DateTimeFormatter formatter = format.getFormatter();
        assertNotNull(formatter, "Formatter should not be null");

        LocalDate date = LocalDate.of(2023, 5, 15);
        assertEquals("2023-05-15", date.format(formatter), "Formatter should correctly format date");
    }
}
