package com.github.phanikb.nvd.cli.api;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.ToString;
import picocli.CommandLine;

import com.github.phanikb.nvd.common.Constants;
import com.github.phanikb.nvd.common.DateFormats;

@Getter
@ToString
public abstract class LastModApiOptions extends BaseApiOptions {
    @CommandLine.ArgGroup(exclusive = false)
    private LastModDateRange lastModDateRange;

    @Override
    public void validateOptions() {
        super.validateOptions();
        if (lastModDateRange != null) {
            validateDateRange(lastModDateRange.lastModStartDate, lastModDateRange.lastModEndDate);
        }
    }

    @Getter
    @ToString
    public static class LastModDateRange {
        @CommandLine.Option(
                names = {"--lmsd", "--last-mod-start-date"},
                paramLabel = "DATE",
                converter = LocalDateTimeConverter.class,
                required = true,
                scope = CommandLine.ScopeType.INHERIT,
                description = "Filter by last modified start date (yyyy-MM-dd HH:mm:ss).")
        private LocalDateTime lastModStartDate;

        @CommandLine.Option(
                names = {"--lmed", "--last-mod-end-date"},
                paramLabel = "DATE",
                converter = LocalDateTimeConverter.class,
                scope = CommandLine.ScopeType.INHERIT,
                description = "Filter by last modified end date (yyyy-MM-dd HH:mm:ss).")
        private LocalDateTime lastModEndDate = DateFormats.TODAY_DATETIME;

        public boolean isWithinAllowableRange() {
            return lastModStartDate != null
                    && lastModEndDate != null
                    && lastModEndDate.isBefore(lastModStartDate.plusDays(Constants.DEFAULT_MAX_RANGE_IN_DAYS));
        }
    }
}
