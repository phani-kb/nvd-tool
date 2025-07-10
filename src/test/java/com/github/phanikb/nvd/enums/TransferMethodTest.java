package com.github.phanikb.nvd.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransferMethodTest {

    @Test
    void testAllEnumValues() {
        TransferMethod[] values = TransferMethod.values();
        assertEquals(8, values.length);
        assertEquals(TransferMethod.HTTP, values[0]);
        assertEquals(TransferMethod.FTP, values[1]);
        assertEquals(TransferMethod.SFTP, values[2]);
        assertEquals(TransferMethod.SCP, values[3]);
        assertEquals(TransferMethod.CURL, values[4]);
        assertEquals(TransferMethod.WGET, values[5]);
        assertEquals(TransferMethod.RSYNC, values[6]);
        assertEquals(TransferMethod.FILE, values[7]);
    }

    @Test
    void testValueOf() {
        assertEquals(TransferMethod.HTTP, TransferMethod.valueOf("HTTP"));
        assertEquals(TransferMethod.FTP, TransferMethod.valueOf("FTP"));
        assertEquals(TransferMethod.SFTP, TransferMethod.valueOf("SFTP"));
        assertEquals(TransferMethod.SCP, TransferMethod.valueOf("SCP"));
        assertEquals(TransferMethod.CURL, TransferMethod.valueOf("CURL"));
        assertEquals(TransferMethod.WGET, TransferMethod.valueOf("WGET"));
        assertEquals(TransferMethod.RSYNC, TransferMethod.valueOf("RSYNC"));
        assertEquals(TransferMethod.FILE, TransferMethod.valueOf("FILE"));
    }

    @Test
    void testToString() {
        assertEquals("HTTP", TransferMethod.HTTP.toString());
        assertEquals("FTP", TransferMethod.FTP.toString());
        assertEquals("SFTP", TransferMethod.SFTP.toString());
        assertEquals("SCP", TransferMethod.SCP.toString());
        assertEquals("CURL", TransferMethod.CURL.toString());
        assertEquals("WGET", TransferMethod.WGET.toString());
        assertEquals("RSYNC", TransferMethod.RSYNC.toString());
        assertEquals("FILE", TransferMethod.FILE.toString());
    }
}
