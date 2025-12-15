package com.github.phanikb.nvd.cli.api;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CveHistoryApiOptionsTest {

    @Test
    void testValidateOptionsDoesNotThrow() {
        CveHistoryApiOptions options = new CveHistoryApiOptions();
        assertDoesNotThrow(options::validateOptions);
    }

    @Test
    void testImplementsInterface() {
        CveHistoryApiOptions options = new CveHistoryApiOptions();
        assertInstanceOf(BaseApiOptions.class, options);
    }

    @Test
    void testChangeDateRangeConstructorAndFields() {
        LocalDateTime start = LocalDateTime.of(2025, 8, 1, 0, 0, 0);
        LocalDateTime end = LocalDateTime.of(2025, 8, 10, 0, 0, 0);
        CveHistoryApiOptions.ChangeDateRange range = new CveHistoryApiOptions.ChangeDateRange(start, end);
        assertEquals(start, range.getChangeStartDate());
        assertEquals(end, range.getChangeEndDate());
    }

    @Test
    void testChangeDateRangeSetters() {
        LocalDateTime start = LocalDateTime.of(2025, 8, 2, 0, 0, 0);
        LocalDateTime end = LocalDateTime.of(2025, 8, 12, 0, 0, 0);
        CveHistoryApiOptions.ChangeDateRange range = new CveHistoryApiOptions.ChangeDateRange();
        range.setChangeStartDate(start);
        range.setChangeEndDate(end);
        assertEquals(start, range.getChangeStartDate());
        assertEquals(end, range.getChangeEndDate());
    }

    @Test
    void testValidateOptionsThrowsForInvalidChangeDateRange() {
        CveHistoryApiOptions options = new CveHistoryApiOptions();
        CveHistoryApiOptions.ChangeDateRange range = new CveHistoryApiOptions.ChangeDateRange(
                LocalDateTime.of(2025, 8, 1, 0, 0, 0), LocalDateTime.of(2026, 8, 1, 0, 0, 0)); // invalid range
        options.setChangeDateRange(range);
        assertThrows(IllegalArgumentException.class, options::validateOptions);
    }
}
