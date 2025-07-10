package com.github.phanikb.nvd.enums;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public enum ApiQueryParams {
    START_INDEX("startIndex", FeedType.CVE_HISTORY, FeedType.CPE_MATCH, FeedType.CPE, FeedType.CVE),
    RESULTS_PER_PAGE("resultsPerPage", FeedType.CVE_HISTORY, FeedType.CPE_MATCH, FeedType.CPE, FeedType.CVE),
    CVE_ID("cveId", FeedType.CVE_HISTORY, FeedType.CPE_MATCH, FeedType.CVE),
    CHANGE_START_DATE("changeStartDate", FeedType.CVE_HISTORY),
    CHANGE_END_DATE("changeEndDate", FeedType.CVE_HISTORY),
    EVENT_NAME("eventName", FeedType.CVE_HISTORY),
    LAST_MODIFIED_START_DATE("lastModStartDate", FeedType.CPE_MATCH, FeedType.CPE, FeedType.CVE),
    LAST_MODIFIED_END_DATE("lastModEndDate", FeedType.CPE_MATCH, FeedType.CPE, FeedType.CVE),
    MATCH_CRITERIA_ID("matchCriteriaId", FeedType.CPE_MATCH, FeedType.CPE),
    MATCH_STRING_SEARCH("matchStringSearch", FeedType.CPE_MATCH),
    CPE_NAME_ID("cpeNameId", FeedType.CPE),
    CPE_MATCH_STRING("cpeMatchString", FeedType.CPE),
    KW_EXACT_MATCH("keywordExactMatch", FeedType.CPE, FeedType.CVE),
    KW_SEARCH("keywordSearch", FeedType.CPE, FeedType.CVE),
    CPE_NAME("cpeName", FeedType.CVE),
    CVE_TAG("cveTag", FeedType.CVE),
    CVSS_V2_METRICS("cvssV2Metrics", FeedType.CVE),
    CVSS_V2_SEVERITY("cvssV2Severity", FeedType.CVE),
    CVSS_V3_METRICS("cvssV3Metrics", FeedType.CVE),
    CVSS_V3_SEVERITY("cvssV3Severity", FeedType.CVE),
    CVSS_V4_METRICS("cvssV4Metrics", FeedType.CVE),
    CVSS_V4_SEVERITY("cvssV4Severity", FeedType.CVE),
    CWE_ID("cweId", FeedType.CVE),
    HAS_CERT_ALERTS("hasCertAlerts", FeedType.CVE),
    HAS_CERT_NOTES("hasCertNotes", FeedType.CVE),
    HAS_KEV("hasKev", FeedType.CVE),
    HAS_OVAL("hasOval", FeedType.CVE),
    IS_VULNERABLE("isVulnerable", FeedType.CVE),
    NO_REJECTED("noRejected", FeedType.CVE),
    PUB_START_DATE("pubStartDate", FeedType.CVE),
    PUB_END_DATE("pubEndDate", FeedType.CVE),
    SOURCE_IDENTIFIER("sourceIdentifier", FeedType.CVE),
    VERSION_END("versionEnd", FeedType.CVE),
    VERSION_END_TYPE("versionEndType", FeedType.CVE),
    VERSION_START("versionStart", FeedType.CVE),
    VERSION_START_TYPE("versionStartType", FeedType.CVE),
    VIRTUAL_MATCH_STRING("virtualMatchString", FeedType.CVE);

    private final String name;
    private final FeedType[] feedTypes;

    ApiQueryParams(String name, FeedType... feedTypes) {
        this.name = name;
        this.feedTypes = feedTypes;
        if (feedTypes == null || feedTypes.length == 0) {
            throw new IllegalArgumentException("Feed types cannot be empty");
        }
    }

    public static List<String> getQueryParams(FeedType feedType) {
        List<String> possibleQueryParams = new ArrayList<>();
        for (ApiQueryParams apiQueryParam : ApiQueryParams.values()) {
            for (FeedType type : apiQueryParam.feedTypes) {
                if (type == feedType) {
                    possibleQueryParams.add(apiQueryParam.name);
                }
            }
        }
        return possibleQueryParams;
    }
}
