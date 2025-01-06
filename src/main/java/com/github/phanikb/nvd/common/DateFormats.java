package com.github.phanikb.nvd.common;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Getter;

public final class DateFormats {
    public static final LocalDate TODAY = LocalDate.now();
    public static final LocalDateTime TODAY_DATETIME = LocalDateTime.now();
    public static final DateTimeFormatter ISO_DATE_TIME_EXT_FORMATTER = DateFormat.ISO_DATE_TIME_EXT.getFormatter();

    @Getter
    public enum DateFormat {
        DEFAULT("yyyy-MM-dd"), // ISO date
        OUTPUT_FILE_NAME_SUFFIX("yyyyMMdd"),
        ISO_DATE_TIME_EXT("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"); // extended ISO-8601 date/time format

        private final String pattern;

        DateFormat(String pattern) {
            this.pattern = pattern;
        }

        public DateTimeFormatter getFormatter() {
            return DateTimeFormatter.ofPattern(pattern);
        }

        public String format(LocalDateTime dateTime) {
            return dateTime.format(getFormatter());
        }

        public String format(LocalDateTime dateTime, boolean endOfDay) {
            return endOfDay ? dateTime.toLocalDate().atTime(23, 59, 59).format(getFormatter()) : format(dateTime);
        }
    }
}