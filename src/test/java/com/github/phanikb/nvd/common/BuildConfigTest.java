package com.github.phanikb.nvd.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BuildConfigTest {

    @Test
    public void testArtifactId() {
        assertNotNull(BuildConfig.ARTIFACT_ID, "ARTIFACT_ID should not be null");
        assertEquals("nvd-tool", BuildConfig.ARTIFACT_ID, "ARTIFACT_ID should be 'nvd-tool'");
    }

    @Test
    public void testVersion() {
        assertNotNull(BuildConfig.VERSION, "VERSION should not be null");
        assertEquals("1.0-SNAPSHOT", BuildConfig.VERSION, "VERSION should be '1.0-SNAPSHOT'");
    }

    @Test
    public void testConstantsAreNotEmpty() {
        assertNotNull(BuildConfig.ARTIFACT_ID, "ARTIFACT_ID should not be null");
        assertNotNull(BuildConfig.VERSION, "VERSION should not be null");
        assertEquals("nvd-tool", BuildConfig.ARTIFACT_ID);
        assertEquals("1.0-SNAPSHOT", BuildConfig.VERSION);
    }
}
