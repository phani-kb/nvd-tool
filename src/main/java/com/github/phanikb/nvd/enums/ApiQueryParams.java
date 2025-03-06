package com.github.phanikb.nvd.enums;

import lombok.Getter;

@Getter
public enum ApiQueryParams {
    START_INDEX("startIndex", new FeedType[] {FeedType.CVE_HISTORY, FeedType.CPE_MATCH, FeedType.CPE, FeedType.CVE}),
    RESULTS_PER_PAGE(
            "resultsPerPage", new FeedType[] {FeedType.CVE_HISTORY, FeedType.CPE_MATCH, FeedType.CPE, FeedType.CVE}),
    CVE_ID("cveId", new FeedType[] {FeedType.CVE_HISTORY, FeedType.CPE_MATCH, FeedType.CVE}),
    CHANGE_START_DATE("changeStartDate", new FeedType[] {FeedType.CVE_HISTORY}),
    CHANGE_END_DATE("changeEndDate", new FeedType[] {FeedType.CVE_HISTORY}),
    EVENT_NAME("eventName", new FeedType[] {FeedType.CVE_HISTORY}),
    LAST_MODIFIED_START_DATE("lastModStartDate", new FeedType[] {FeedType.CPE_MATCH, FeedType.CPE, FeedType.CVE}),
    LAST_MODIFIED_END_DATE("lastModEndDate", new FeedType[] {FeedType.CPE_MATCH, FeedType.CPE, FeedType.CVE}),
    MATCH_CRITERIA_ID("matchCriteriaId", new FeedType[] {FeedType.CPE_MATCH, FeedType.CPE}),
    MATCH_STRING_SEARCH("matchStringSearch", new FeedType[] {FeedType.CPE_MATCH}),
    CPE_NAME_ID("cpeNameId", new FeedType[] {FeedType.CPE}),
    CPE_MATCH_STRING("cpeMatchString", new FeedType[] {FeedType.CPE}),
    KW_EXACT_MATCH("keywordExactMatch", new FeedType[] {FeedType.CPE, FeedType.CVE}),
    KW_SEARCH("keywordSearch", new FeedType[] {FeedType.CPE, FeedType.CVE}),
    CPE_NAME("cpeName", new FeedType[] {FeedType.CVE}),
    CVE_TAG("cveTag", new FeedType[] {FeedType.CVE}),
    CVSS_V2_METRICS("cvssV2Metrics", new FeedType[] {FeedType.CVE}),
    CVSS_V2_SEVERITY("cvssV2Severity", new FeedType[] {FeedType.CVE}),
    CVSS_V3_METRICS("cvssV3Metrics", new FeedType[] {FeedType.CVE}),
    CVSS_V3_SEVERITY("cvssV3Severity", new FeedType[] {FeedType.CVE}),
    CVSS_V4_METRICS("cvssV4Metrics", new FeedType[] {FeedType.CVE}),
    CVSS_V4_SEVERITY("cvssV4Severity", new FeedType[] {FeedType.CVE}),
    CWE_ID("cweId", new FeedType[] {FeedType.CVE}),
    HAS_CERT_ALERTS("hasCertAlerts", new FeedType[] {FeedType.CVE}),
    HAS_CERT_NOTES("hasCertNotes", new FeedType[] {FeedType.CVE}),
    HAS_KEV("hasKev", new FeedType[] {FeedType.CVE}),
    HAS_OVAL("hasOval", new FeedType[] {FeedType.CVE}),
    IS_VULNERABLE("isVulnerable", new FeedType[] {FeedType.CVE}),
    NO_REJECTED("noRejected", new FeedType[] {FeedType.CVE}),
    PUB_START_DATE("pubStartDate", new FeedType[] {FeedType.CVE}),
    PUB_END_DATE("pubEndDate", new FeedType[] {FeedType.CVE}),
    SOURCE_IDENTIFIER("sourceIdentifier", new FeedType[] {FeedType.CVE}),
    VERSION_END("versionEnd", new FeedType[] {FeedType.CVE}),
    VERSION_END_TYPE("versionEndType", new FeedType[] {FeedType.CVE}),
    VERSION_START("versionStart", new FeedType[] {FeedType.CVE}),
    VERSION_START_TYPE("versionStartType", new FeedType[] {FeedType.CVE}),
    VIRTUAL_MATCH_STRING("virtualMatchString", new FeedType[] {FeedType.CVE});

    private final String name;
    private final FeedType[] feedTypes;

    ApiQueryParams(String name, FeedType[] feedTypes) {
        this.name = name;
        this.feedTypes = feedTypes;
        if (feedTypes == null || feedTypes.length == 0) {
            throw new IllegalArgumentException("Feed types cannot be empty");
        }
    }
}
