package com.github.phanikb.nvd.cli.api.download;

import lombok.Getter;
import lombok.ToString;
import picocli.CommandLine;

@Getter
@ToString
public class ApiDownloadCommonOptions {
    @CommandLine.Option(
            names = {"--rpp", "--results-per-page"},
            scope = CommandLine.ScopeType.INHERIT,
            description =
                    "Maximum number of records to be returned in a single API response. Takes precedence over sub command option. min: 1, max: 1000",
            paramLabel = "NUMBER")
    private Integer resultsPerPage;

    @CommandLine.Option(
            names = {"--si", "--start-index"},
            scope = CommandLine.ScopeType.INHERIT,
            description = "Index of the first record to be returned in the response data. The index is zero-based.",
            paramLabel = "NUMBER")
    private Integer startIndex;
}
