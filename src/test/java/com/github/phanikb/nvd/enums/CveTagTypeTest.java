package com.github.phanikb.nvd.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CveTagTypeTest {

    @Test
    void testAllEnumValues() {
        CveTagType[] values = CveTagType.values();
        assertEquals(3, values.length);
        assertEquals(CveTagType.DISPUTED, values[0]);
        assertEquals(CveTagType.UWA, values[1]);
        assertEquals(CveTagType.EHS, values[2]);
    }

    @Test
    void testDisputedValue() {
        assertEquals("disputed", CveTagType.DISPUTED.getValue());
    }

    @Test
    void testUwaValue() {
        assertEquals("unsupported-when-assigned", CveTagType.UWA.getValue());
    }

    @Test
    void testEhsValue() {
        assertEquals("exclusively-hosted-service", CveTagType.EHS.getValue());
    }

    @Test
    void testValueOf() {
        assertEquals(CveTagType.DISPUTED, CveTagType.valueOf("DISPUTED"));
        assertEquals(CveTagType.UWA, CveTagType.valueOf("UWA"));
        assertEquals(CveTagType.EHS, CveTagType.valueOf("EHS"));
    }

    @Test
    void testToString() {
        assertEquals("DISPUTED", CveTagType.DISPUTED.toString());
        assertEquals("UWA", CveTagType.UWA.toString());
        assertEquals("EHS", CveTagType.EHS.toString());
    }
}
