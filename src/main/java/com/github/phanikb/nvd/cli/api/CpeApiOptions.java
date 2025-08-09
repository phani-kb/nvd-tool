package com.github.phanikb.nvd.cli.api;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import picocli.CommandLine;

import com.github.phanikb.nvd.common.CpeName;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CpeApiOptions extends LastModApiOptions {
    @CommandLine.ArgGroup(exclusive = false)
    private KeywordSearch keywordSearch;

    @CommandLine.Option(
            names = {"--cpe-name-id"},
            paramLabel = "ID",
            description = "Returns a specific CPE record identified by a Universal Unique Identifier (uuid).")
    private UUID cpeNameId;

    @CommandLine.Option(
            names = {"--cpe-ms"},
            paramLabel = "STRING",
            converter = CpeName.class,
            description =
                    "Returns CPE Names that exist in the Official CPE Dictionary, based on the value of {match string}.")
    private CpeName cpeMatchString;

    @CommandLine.Option(
            names = {"--mc-id", "--match-criteria-id"},
            paramLabel = "ID",
            description = "Returns all CPE records associated with a match string identified by its {uuid}.")
    private UUID matchCriteriaId;

    @Override
    public void validateOptions() {
        super.validateOptions();
        if (keywordSearch != null && keywordSearch.isInvalid()) {
            throw new IllegalArgumentException("Invalid keyword search options, exact match requires keyword.");
        }
        if (cpeMatchString != null) {
            cpeMatchString.validateName();
        }
    }

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class KeywordSearch {
        @CommandLine.Option(
                names = {"--kw-em"},
                description = "Returns only the CPE matching the phrase exactly.")
        private boolean keywordExactMatch;

        @CommandLine.Option(
                names = {"--kw-search"},
                paramLabel = "STRING",
                description =
                        "Returns any CPE record where a word or phrase is found in the metadata title or reference links.")
        private String keywordSearch;

        public boolean isInvalid() {
            return keywordExactMatch && keywordSearch == null;
        }
    }
}
