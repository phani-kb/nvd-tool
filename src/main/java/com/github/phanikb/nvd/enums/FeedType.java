package com.github.phanikb.nvd.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FeedType {
    CVE("cve", "vulnerabilities"),
    CVE_HISTORY("cve-history", "cveChanges"),
    CPE("cpe", "products"),
    CPE_MATCH("cpe-match", "matchStrings"),
    CWE("cwe", null);

    private final String name;
    private final String collectionNodeName;
}
