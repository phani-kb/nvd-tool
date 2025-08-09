package com.github.phanikb.nvd.cli.api;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import picocli.CommandLine;

import com.github.phanikb.nvd.common.Constants;
import com.github.phanikb.nvd.common.CpeName;
import com.github.phanikb.nvd.common.CveId;
import com.github.phanikb.nvd.common.CweId;
import com.github.phanikb.nvd.common.DateFormats;
import com.github.phanikb.nvd.enums.CveTagType;
import com.github.phanikb.nvd.enums.Cvssv2Severity;
import com.github.phanikb.nvd.enums.Cvssv3Severity;
import com.github.phanikb.nvd.enums.Cvssv4Severity;
import com.github.phanikb.nvd.enums.VersionType;

@Getter
@Setter
@ToString
public class CveApiOptions extends LastModApiOptions {
    @CommandLine.ArgGroup(exclusive = false)
    private PubDateRange pubDateRange;

    @CommandLine.ArgGroup(exclusive = false)
    private CpeVulnerable cpeVulnerable;

    @CommandLine.ArgGroup()
    private CvssMetrics cvssMetrics;

    @CommandLine.ArgGroup()
    private CvssSeverity cvssSeverity;

    @CommandLine.ArgGroup(exclusive = false)
    private KeywordSearch keywordSearch;

    @CommandLine.ArgGroup(exclusive = false)
    private VersionEnd versionEnd;

    @CommandLine.ArgGroup(exclusive = false)
    private VersionStart versionStart;

    @CommandLine.Option(
            names = {"--cpeName"},
            paramLabel = "STRING",
            converter = CpeName.class,
            description = "returns all CVE associated with a specific CPE.")
    private CpeName cpeName;

    @CommandLine.Option(
            names = {"--cve-id"},
            paramLabel = "ID",
            converter = CveId.class,
            description = "Filter by CVE ID.")
    private CveId cveId;

    @CommandLine.Option(
            names = {"--cve-tag"},
            paramLabel = "TAG",
            description =
                    "Returns only the CVE that include a tag of the specified type. Valid values: ${COMPLETION-CANDIDATES}")
    private CveTagType cveTag;

    @CommandLine.Option(
            names = {"--cwe-id"},
            paramLabel = "ID",
            converter = CweId.class,
            description =
                    "Returns only the CVE that include a weakness identified by CWE ID. Note: The NVD also makes use of two placeholder CWE-ID values NVD-CWE-Other and NVD-CWE-noinfo which can also be used.")
    private CweId cweId;

    @CommandLine.Option(
            names = {"--cert-alerts"},
            description = "Returns the CVE that contain a Technical Alert from US-CERT.")
    private boolean hasCertAlerts;

    @CommandLine.Option(
            names = {"--cert-notes"},
            description = "Returns the CVE that contain a Vulnerability Note from CERT/CC.")
    private boolean hasCertNotes;

    @CommandLine.Option(
            names = {"--kev"},
            description = "Returns the CVE that appear in CISA's Known Exploited Vulnerabilities (KEV) Catalog.")
    private boolean hasKev;

    @CommandLine.Option(
            names = {"--oval"},
            description =
                    "Returns the CVE that contain information from MITRE's Open Vulnerability and Assessment Language (OVAL) before this transitioned to the Center for Internet Security (CIS).")
    private boolean hasOval;

    @CommandLine.Option(
            names = {"--no-rejected"},
            description = "Excludes CVE records with the REJECT or Rejected status from API response.")
    private boolean noRejected;

    @CommandLine.Option(
            names = {"--source-id"},
            paramLabel = "ID",
            description =
                    "Returns CVE where the exact value of {sourceIdentifier} appears as a data source in the CVE record.")
    private String sourceIdentifier;

    @CommandLine.Option(
            names = {"--virtual-ms"},
            paramLabel = "STRING",
            description =
                    "Filters CVE more broadly than cpeName. The exact value of {cpe match string} is compared against the CPE Match Criteria present on CVE applicability statements.")
    private String virtualMatchString;

    @Override
    public void validateOptions() {
        super.validateOptions();

        if (pubDateRange != null) {
            validateDateRange(pubDateRange.getPubStartDate(), pubDateRange.getPubEndDate());
        }

        if (pubDateRange != null && getLastModDateRange() != null) {
            if (!getPubDateRange().isWithinAllowableRange()
                    && !getLastModDateRange().isWithinAllowableRange()) {
                throw new IllegalArgumentException("Date ranges are outside the allowable range.");
            }
        }

        CveApiOptions.KeywordSearch keywordSearch = getKeywordSearch();
        if (keywordSearch != null && keywordSearch.isInvalid()) {
            throw new IllegalArgumentException("Invalid keyword search options, exact match requires keyword.");
        }

        CveApiOptions.CpeVulnerable cpeVulnerable = getCpeVulnerable();
        if (cpeVulnerable != null) {
            if (cpeVulnerable.isInvalid()) {
                throw new IllegalArgumentException("Missing required option '--cpe-name'");
            } else if (cpeVulnerable.isVulnerable() && virtualMatchString != null) {
                throw new IllegalArgumentException("Option '--virtual-ms' cannot be used with '--vulnerable'");
            }
        }

        CveApiOptions.VersionEnd versionEnd = getVersionEnd();
        if (versionEnd != null && virtualMatchString == null) {
            throw new IllegalArgumentException("Option '--version-end' requires '--virtual-ms'");
        }

        CveApiOptions.VersionStart versionStart = getVersionStart();
        if (versionStart != null && virtualMatchString == null) {
            throw new IllegalArgumentException("Option '--version-start' requires '--virtual-ms'");
        }

        if (virtualMatchString != null && (versionEnd == null || versionStart == null)) {
            throw new IllegalArgumentException(
                    "Options '--version-end' and '--version-start' are required with '--virtual-ms'");
        }

        if (cveId != null) {
            cveId.validateId();
        }

        if (cweId != null) {
            cweId.validateId();
        }

        if (cpeName != null) {
            cpeName.validateName();
        }
    }

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    public static class PubDateRange {
        @CommandLine.Option(
                names = {"--psd", "--pub-start-date"},
                paramLabel = "DATE",
                converter = LocalDateTimeConverter.class,
                required = true,
                description = "Filter by published start date (yyyy-MM-dd HH:mm:ss).")
        private LocalDateTime pubStartDate;

        @CommandLine.Option(
                names = {"--ped", "--pub-end-date"},
                paramLabel = "DATE",
                converter = LocalDateTimeConverter.class,
                description = "Filter by published end date (yyyy-MM-dd HH:mm:ss).")
        private LocalDateTime pubEndDate = DateFormats.TODAY_DATETIME;

        public boolean isWithinAllowableRange() {
            return pubStartDate != null
                    && pubEndDate != null
                    && pubEndDate.isBefore(pubStartDate.plusDays(Constants.DEFAULT_MAX_RANGE_IN_DAYS));
        }
    }

    @Getter
    @Setter
    @ToString
    public static class CpeVulnerable {
        @CommandLine.Option(
                names = {"--cpe-name"},
                paramLabel = "NAME",
                description = "Returns all CVE associated with a specific CPE.")
        private String cpeName;

        @CommandLine.Option(
                names = {"--vulnerable"},
                description =
                        "Returns only CVE associated with a specific CPE, where the CPE is also considered vulnerable.")
        private boolean isVulnerable;

        public boolean isInvalid() {
            return cpeName == null && isVulnerable;
        }
    }

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    public static class VersionEnd {
        @CommandLine.Option(
                names = {"--vet", "--ve-type"},
                paramLabel = "TYPE",
                required = true,
                description = "Valid values are ${COMPLETION-CANDIDATES}.")
        private VersionType versionEndType;

        @CommandLine.Option(
                names = {"--ve", "--version-end"},
                paramLabel = "VERSION",
                required = true,
                description = "Ending version.")
        private String versionEnd;
    }

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    public static class VersionStart {
        @CommandLine.Option(
                names = {"--vst", "--vs-type"},
                paramLabel = "TYPE",
                required = true,
                description = "Valid values are ${COMPLETION-CANDIDATES}.")
        private VersionType versionStartType;

        @CommandLine.Option(
                names = {"--vs", "--version-start"},
                paramLabel = "VERSION",
                required = true,
                description = "Starting version.")
        private String versionStart;
    }

    @Getter
    @Setter
    @ToString
    public static class KeywordSearch {
        @CommandLine.Option(
                names = {"--kw-em"},
                description = "Returns only the CVE matching the word or phrase exactly in the current description.")
        private boolean keywordExactMatch;

        @CommandLine.Option(
                names = {"--kw-search"},
                paramLabel = "STRING",
                description = "Returns only the CVEs where a word or phrase is found in the current description.")
        private String keywordSearch;

        public boolean isInvalid() {
            return keywordExactMatch && keywordSearch == null;
        }
    }

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    public static class CvssSeverity {
        @CommandLine.Option(
                names = {"--cvssv2-severity"},
                paramLabel = "SEVERITY",
                type = Cvssv2Severity.class,
                description = "Filter by CVSSv2 severity. Valid values: ${COMPLETION-CANDIDATES}")
        private String cvssV2Severity;

        @CommandLine.Option(
                names = {"--cvssv3-severity"},
                paramLabel = "SEVERITY",
                type = Cvssv3Severity.class,
                description = "Filter by CVSSv3 severity. Valid values: ${COMPLETION-CANDIDATES}")
        private String cvssV3Severity;

        @CommandLine.Option(
                names = {"--cvssv4-severity"},
                paramLabel = "SEVERITY",
                type = Cvssv4Severity.class,
                description =
                        "Filter by CVSSv4 severity. Valid values: ${COMPLETION-CANDIDATES}. Note: The NVD enrichment data will not contain CVSS v4 vector strings with a severity of NONE.")
        private String cvssV4Severity;
    }

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    public static class CvssMetrics {
        @CommandLine.Option(
                names = {"--cvssv2-metrics"},
                paramLabel = "VECTOR",
                description = "Returns only the CVEs that match the provided {CVSSv2 vector string}.")
        private String cvssV2Metrics;

        @CommandLine.Option(
                names = {"--cvssv3-metrics"},
                paramLabel = "VECTOR",
                description = "Returns only the CVEs that match the provided {CVSSv3 vector string}.")
        private String cvssV3Metrics;

        @CommandLine.Option(
                names = {"--cvssv4-metrics"},
                paramLabel = "VECTOR",
                description = "Returns only the CVEs that match the provided {CVSSv4 vector string}.")
        private String cvssV4Metrics;
    }
}
