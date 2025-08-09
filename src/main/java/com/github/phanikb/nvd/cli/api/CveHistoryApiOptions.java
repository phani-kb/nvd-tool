package com.github.phanikb.nvd.cli.api;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import picocli.CommandLine;

import com.github.phanikb.nvd.common.DateFormats;
import com.github.phanikb.nvd.enums.CveHistoryEventName;

@Getter
@Setter
@ToString
public class CveHistoryApiOptions extends BaseApiOptions {
    @CommandLine.ArgGroup(exclusive = false)
    private ChangeDateRange changeDateRange;

    @CommandLine.Option(
            names = {"--cve-id"},
            paramLabel = "ID",
            description = "Filter by CVE ID.")
    private String cveId;

    @CommandLine.Option(
            names = {"-e", "--event-name"},
            type = CveHistoryEventName.class,
            description =
                    "Returns all CVE associated with a specific type of change event. Valid values: ${COMPLETION-CANDIDATES}",
            paramLabel = "EVENT_NAME")
    private CveHistoryEventName eventName;

    @Override
    public void validateOptions() {
        super.validateOptions();
        validateCveId(cveId);
        if (changeDateRange != null) {
            validateDateRange(changeDateRange.changeStartDate, changeDateRange.changeEndDate);
        }
    }

    @Getter
    @Setter
    @ToString
    public static class ChangeDateRange {
        public ChangeDateRange() {}

        public ChangeDateRange(LocalDateTime changeStartDate, LocalDateTime changeEndDate) {
            this.changeStartDate = changeStartDate;
            this.changeEndDate = changeEndDate;
        }

        @CommandLine.Option(
                names = {"--csd", "--change-start-date"},
                paramLabel = "DATE",
                required = true,
                converter = LocalDateTimeConverter.class,
                description = "Filter by change start date (yyyy-MM-dd HH:mm:ss).")
        private LocalDateTime changeStartDate;

        @CommandLine.Option(
                names = {"--ced", "--change-end-date"},
                paramLabel = "DATE",
                converter = LocalDateTimeConverter.class,
                description = "Filter by change end date (yyyy-MM-dd HH:mm:ss).")
        private LocalDateTime changeEndDate = DateFormats.TODAY_DATETIME;
    }
}
