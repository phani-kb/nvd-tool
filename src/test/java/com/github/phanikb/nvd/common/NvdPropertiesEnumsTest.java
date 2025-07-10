package com.github.phanikb.nvd.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class NvdPropertiesEnumsTest {

    @Test
    public void testApiEndpointVersion() {
        NvdProperties.ApiEndpointVersion[] values = NvdProperties.ApiEndpointVersion.values();
        assertNotNull(values, "ApiEndpointVersion values should not be null");
        assertEquals(1, values.length, "Should have exactly 1 ApiEndpointVersion value");
        assertEquals(NvdProperties.ApiEndpointVersion.V2, values[0], "Should have V2 version");
        assertEquals("V2", NvdProperties.ApiEndpointVersion.V2.name(), "V2 name should be 'V2'");
    }

    @Test
    public void testApiEndpointType() {
        NvdProperties.ApiEndpointType[] values = NvdProperties.ApiEndpointType.values();
        assertNotNull(values, "ApiEndpointType values should not be null");
        assertEquals(4, values.length, "Should have exactly 4 ApiEndpointType values");

        // Test all enum values exist
        assertEquals(NvdProperties.ApiEndpointType.CVE, NvdProperties.ApiEndpointType.valueOf("CVE"));
        assertEquals(NvdProperties.ApiEndpointType.CPE, NvdProperties.ApiEndpointType.valueOf("CPE"));
        assertEquals(NvdProperties.ApiEndpointType.CPE_MATCH, NvdProperties.ApiEndpointType.valueOf("CPE_MATCH"));
        assertEquals(NvdProperties.ApiEndpointType.CVE_HISTORY, NvdProperties.ApiEndpointType.valueOf("CVE_HISTORY"));
    }

    @Test
    public void testApiEndpointVersionValueOf() {
        assertEquals(NvdProperties.ApiEndpointVersion.V2, NvdProperties.ApiEndpointVersion.valueOf("V2"));
    }

    @Test
    public void testApiEndpointTypeNames() {
        assertEquals("CVE", NvdProperties.ApiEndpointType.CVE.name());
        assertEquals("CPE", NvdProperties.ApiEndpointType.CPE.name());
        assertEquals("CPE_MATCH", NvdProperties.ApiEndpointType.CPE_MATCH.name());
        assertEquals("CVE_HISTORY", NvdProperties.ApiEndpointType.CVE_HISTORY.name());
    }

    @Test
    public void testEnumToString() {
        assertEquals("V2", NvdProperties.ApiEndpointVersion.V2.toString());
        assertEquals("CVE", NvdProperties.ApiEndpointType.CVE.toString());
        assertEquals("CPE", NvdProperties.ApiEndpointType.CPE.toString());
        assertEquals("CPE_MATCH", NvdProperties.ApiEndpointType.CPE_MATCH.toString());
        assertEquals("CVE_HISTORY", NvdProperties.ApiEndpointType.CVE_HISTORY.toString());
    }
}
