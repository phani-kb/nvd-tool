package com.github.phanikb.nvd.enums;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApiQueryParamsTest {
    @Test
    void testGetQueryParamsForFeedType() {
        List<String> cveParams = ApiQueryParams.getQueryParams(FeedType.CVE);
        assertTrue(cveParams.contains("cveId"));
        assertTrue(cveParams.contains("lastModStartDate"));
        assertTrue(cveParams.contains("cveTag"));
        assertFalse(cveParams.contains("changeStartDate"));
        assertFalse(cveParams.contains("eventName"));
    }

    @Test
    void testEnumValuesHaveNameAndFeedTypes() {
        for (ApiQueryParams param : ApiQueryParams.values()) {
            assertNotNull(param.getName());
            assertNotNull(param.getFeedTypes());
            assertTrue(param.getFeedTypes().length > 0);
        }
    }
}
