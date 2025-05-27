package com.github.phanikb.nvd.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class NvdPropertiesTest {

    @Test
    public void testLoadProperties() {
        NvdProperties properties = NvdProperties.getInstance();
        assertNotNull(properties, "NvdProperties instance should not be null");
        assertNotNull(properties.getNvd(), "Nvd property should not be null");
        assertNotNull(properties.getNvd().getApi(), "Nvd API property should not be null");
        assertNotNull(properties.getNvd().getApi().getKeyUrl(), "Nvd API KeyUrl property should not be null");
    }
}
