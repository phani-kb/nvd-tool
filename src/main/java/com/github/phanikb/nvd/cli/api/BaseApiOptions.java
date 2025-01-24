package com.github.phanikb.nvd.cli.api;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import picocli.CommandLine;

import com.github.phanikb.nvd.common.Util;

public abstract class BaseApiOptions implements IApiOptions {
    @Override
    public void validateOptions() {}

    protected void validateCveId(String cveId) {
        if (cveId != null && !cveId.matches("CVE-\\d{4}-\\d{4,}")) {
            throw new IllegalArgumentException("Invalid CVE ID, must match pattern CVE-YYYY-NNNNN.");
        }
    }

    protected void validateDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        Util.validateDateRange(startDate, endDate, true);
    }

    public static class LocalDateTimeConverter implements CommandLine.ITypeConverter<LocalDateTime> {
        @Override
        public LocalDateTime convert(String value) {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            return LocalDateTime.parse(value, df);
        }
    }
}
