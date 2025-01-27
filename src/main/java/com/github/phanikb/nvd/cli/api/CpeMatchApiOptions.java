package com.github.phanikb.nvd.cli.api;

import java.util.UUID;

import lombok.Getter;
import lombok.ToString;
import picocli.CommandLine;

import com.github.phanikb.nvd.common.CpeName;

@Getter
@ToString
public class CpeMatchApiOptions extends LastModApiOptions {
    @CommandLine.Option(
            names = {"--mc-id", "--match-criteria-id"},
            paramLabel = "ID",
            description = "Returns all CPE records associated with a match string identified by its " + "{uuid}.")
    private UUID matchCriteriaId;

    @CommandLine.Option(
            names = {"--ms-search", "--match-str-search"},
            paramLabel = "STRING",
            converter = CpeName.class,
            description = "Returns all CPE Match Strings that conform to the pattern of the {cpe match " + "string}.")
    private CpeName matchStringSearch;

    @CommandLine.Option(
            names = {"--cve-id"},
            paramLabel = "ID",
            description = "Filter by CVE ID.")
    private String cveId;

    @Override
    public void validateOptions() {
        super.validateOptions();
        validateCveId(cveId);
        if (matchStringSearch != null) {
            matchStringSearch.validateName();
        }
    }
}
