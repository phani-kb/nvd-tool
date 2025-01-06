package com.github.phanikb.nvd.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CveHistoryEventName {
    CVE_RECEIVED("CVE Received"),
    INITIAL_ANALYSIS("Initial Analysis"),
    REANALYSIS("Reanalysis"),
    CVE_MODIFIED("CVE Modified"),
    MODIFIED_ANALYSIS("Modified Analysis"),
    CVE_TRANSLATED("CVE Translated"),
    VENDOR_COMMENT("Vendor Comment"),
    CVE_SOURCE_UPDATE("CVE Source Update"),
    CPE_DR(" CPE Deprecation Remap"),
    CWE_REMAP("CWE Remap"),
    CVE_REJECTED("CVE Rejected"),
    CVE_UNREJECTED("CVE Unrejected"),
    CVE_CISA_KEV_UPDATE("CVE CISA KEV Update");

    private final String value;

}
