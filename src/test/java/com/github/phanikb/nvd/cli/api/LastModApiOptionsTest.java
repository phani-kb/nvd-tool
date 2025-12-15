package com.github.phanikb.nvd.cli.api;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LastModApiOptionsTest {

    private static class TestableLastModApiOptions extends LastModApiOptions {}

    @Test
    void testValidateOptionsDoesNotThrow() {
        TestableLastModApiOptions options = new TestableLastModApiOptions();
        assertDoesNotThrow(options::validateOptions);
    }

    @Test
    void testImplementsInterface() {
        TestableLastModApiOptions options = new TestableLastModApiOptions();
        assertInstanceOf(BaseApiOptions.class, options);
    }

    @Test
    void testLastModDateRangeConstructorAndFields() {
        LocalDateTime start = LocalDateTime.of(2025, 8, 1, 0, 0, 0);
        LocalDateTime end = LocalDateTime.of(2025, 8, 10, 0, 0, 0);
        LastModApiOptions.LastModDateRange range = new LastModApiOptions.LastModDateRange(start, end);
        assertEquals(start, range.getLastModStartDate());
        assertEquals(end, range.getLastModEndDate());
    }

    @Test
    void testLastModDateRangeSetters() {
        LocalDateTime start = LocalDateTime.of(2025, 8, 2, 0, 0, 0);
        LocalDateTime end = LocalDateTime.of(2025, 8, 12, 0, 0, 0);
        LastModApiOptions.LastModDateRange range = new LastModApiOptions.LastModDateRange();
        range.setLastModStartDate(start);
        range.setLastModEndDate(end);
        assertEquals(start, range.getLastModStartDate());
        assertEquals(end, range.getLastModEndDate());
    }

    @Test
    void testIsWithinAllowableRangeTrue() {
        LocalDateTime start = LocalDateTime.of(2025, 8, 1, 0, 0, 0);
        LocalDateTime end = start.plusDays(10);
        LastModApiOptions.LastModDateRange range = new LastModApiOptions.LastModDateRange(start, end);
        assertTrue(range.isWithinAllowableRange());
    }

    @Test
    void testIsWithinAllowableRangeFalse() {
        LocalDateTime start = LocalDateTime.of(2025, 8, 1, 0, 0, 0);
        LocalDateTime end = start.plusDays(130); // exceeds DEFAULT_MAX_RANGE_IN_DAYS
        LastModApiOptions.LastModDateRange range = new LastModApiOptions.LastModDateRange(start, end);
        assertFalse(range.isWithinAllowableRange());
    }
}
