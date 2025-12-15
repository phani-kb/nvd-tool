package com.github.phanikb.nvd.cli.api;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BaseApiOptionsTest {

    private static class TestableBaseApiOptions extends BaseApiOptions {}

    @Test
    void testValidateOptionsDoesNotThrow() {
        TestableBaseApiOptions options = new TestableBaseApiOptions();
        assertDoesNotThrow(options::validateOptions);
    }

    @Test
    void testImplementsInterface() {
        TestableBaseApiOptions options = new TestableBaseApiOptions();
        assertInstanceOf(IApiOptions.class, options);
    }

    @Test
    void testValidateCveIdWithValidIds() {
        TestableBaseApiOptions options = new TestableBaseApiOptions();
        assertDoesNotThrow(() -> options.validateCveId("CVE-2023-1234"));
        assertDoesNotThrow(() -> options.validateCveId("CVE-2021-12345"));
        assertDoesNotThrow(() -> options.validateCveId("CVE-1999-9999"));
        assertDoesNotThrow(() -> options.validateCveId("CVE-2024-123456"));
        assertDoesNotThrow(() -> options.validateCveId(null)); // Null should be allowed
    }

    @Test
    void testValidateCveIdWithInvalidIds() {
        TestableBaseApiOptions options = new TestableBaseApiOptions();
        Exception ex1 = assertThrows(
                IllegalArgumentException.class, () -> options.validateCveId("CVE-23-1234")); // Invalid year format
        assertNotNull(ex1.getMessage());

        Exception ex2 = assertThrows(
                IllegalArgumentException.class,
                () -> options.validateCveId("CVE-2023-123")); // Too few digits in number
        assertNotNull(ex2.getMessage());

        Exception ex3 = assertThrows(
                IllegalArgumentException.class, () -> options.validateCveId("CVE2023-1234")); // Missing dash after CVE
        assertNotNull(ex3.getMessage());

        Exception ex4 = assertThrows(
                IllegalArgumentException.class,
                () -> options.validateCveId("CVE-2023_1234")); // Underscore instead of dash
        assertNotNull(ex4.getMessage());

        Exception ex5 = assertThrows(
                IllegalArgumentException.class, () -> options.validateCveId("INVALID-2023-1234")); // Wrong prefix
        assertNotNull(ex5.getMessage());

        Exception ex6 = assertThrows(IllegalArgumentException.class, () -> options.validateCveId("")); // Empty string
        assertNotNull(ex6.getMessage());
    }

    @Test
    void testValidateDateRangeWithValidRanges() {
        TestableBaseApiOptions options = new TestableBaseApiOptions();
        LocalDateTime start = LocalDateTime.of(2023, 1, 1, 0, 0, 0);
        LocalDateTime end = LocalDateTime.of(2023, 12, 31, 23, 59, 59);

        assertDoesNotThrow(() -> options.validateDateRange(start, end));

        assertDoesNotThrow(() -> options.validateDateRange(start, start));
    }

    @Test
    void testValidateDateRangeWithInvalidRange() {
        TestableBaseApiOptions options = new TestableBaseApiOptions();
        LocalDateTime start = LocalDateTime.of(2023, 12, 31, 23, 59, 59);
        LocalDateTime end = LocalDateTime.of(2023, 1, 1, 0, 0, 0);

        Exception ex = assertThrows(Exception.class, () -> options.validateDateRange(start, end));
        assertNotNull(ex);
    }

    @Test
    void testLocalDateTimeConverter() {
        BaseApiOptions.LocalDateTimeConverter converter = new BaseApiOptions.LocalDateTimeConverter();

        LocalDateTime result1 = converter.convert("2023-12-25 14:30:45");
        assertEquals(LocalDateTime.of(2023, 12, 25, 14, 30, 45), result1);

        LocalDateTime result2 = converter.convert("2021-01-01 00:00:00");
        assertEquals(LocalDateTime.of(2021, 1, 1, 0, 0, 0), result2);

        LocalDateTime result3 = converter.convert("2024-06-15 23:59:59");
        assertEquals(LocalDateTime.of(2024, 6, 15, 23, 59, 59), result3);
    }

    @Test
    void testLocalDateTimeConverterWithInvalidFormats() {
        BaseApiOptions.LocalDateTimeConverter converter = new BaseApiOptions.LocalDateTimeConverter();

        Exception ex1 = assertThrows(Exception.class, () -> converter.convert("2023-12-25")); // Missing time
        assertNotNull(ex1);

        Exception ex2 =
                assertThrows(Exception.class, () -> converter.convert("12-25-2023 14:30:45")); // Wrong date format
        assertNotNull(ex2);

        Exception ex3 = assertThrows(Exception.class, () -> converter.convert("invalid")); // Completely invalid
        assertNotNull(ex3);
    }

    @Test
    void testLocalDateTimeConverterImplementsInterface() {
        BaseApiOptions.LocalDateTimeConverter converter = new BaseApiOptions.LocalDateTimeConverter();
        assertInstanceOf(picocli.CommandLine.ITypeConverter.class, converter);
    }

    @Test
    void testCveIdPatternEdgeCases() {
        TestableBaseApiOptions options = new TestableBaseApiOptions();
        assertDoesNotThrow(() -> options.validateCveId("CVE-1999-0001"));

        assertDoesNotThrow(() -> options.validateCveId("CVE-2099-1234"));

        assertDoesNotThrow(() -> options.validateCveId("CVE-2023-123456789"));

        assertDoesNotThrow(() -> options.validateCveId("CVE-2023-1000"));

        Exception ex = assertThrows(IllegalArgumentException.class, () -> options.validateCveId("CVE-2023-999"));
        assertNotNull(ex.getMessage());
    }
}
