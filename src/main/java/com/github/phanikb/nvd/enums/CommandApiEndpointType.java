package com.github.phanikb.nvd.enums;

import com.github.phanikb.nvd.common.NvdProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommandApiEndpointType {
    CVE("cve", NvdProperties.ApiEndpointType.CVE, FeedType.CVE),
    CPE("cpe", NvdProperties.ApiEndpointType.CPE, FeedType.CPE),
    CPE_MATCH("cpe-match", NvdProperties.ApiEndpointType.CPE_MATCH, FeedType.CPE_MATCH),
    CVE_HISTORY("cve-history", NvdProperties.ApiEndpointType.CVE_HISTORY, FeedType.CVE_HISTORY);

    private final String commandName;
    private final NvdProperties.ApiEndpointType apiEndpointType;
    private final FeedType feedType;
}
