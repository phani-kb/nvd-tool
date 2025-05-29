package com.github.phanikb.nvd.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NvdDownloadExceptionTest {

    @Test
    public void testConstructorWithMessage() {
        String errorMessage = "Download failed";
        NvdDownloadException exception = new NvdDownloadException(errorMessage);

        assertEquals(errorMessage, exception.getMessage(), "Message should match constructor parameter");
        assertNull(exception.getCause(), "Cause should be null when not specified");
        assertTrue(exception instanceof NvdException, "NvdDownloadException should be an instance of NvdException");
    }

    @Test
    public void testConstructorWithMessageAndCause() {
        String errorMessage = "Download failed";
        Throwable cause = new RuntimeException("Connection reset");
        NvdDownloadException exception = new NvdDownloadException(errorMessage, cause);

        assertEquals(errorMessage, exception.getMessage(), "Message should match constructor parameter");
        assertSame(cause, exception.getCause(), "Cause should match constructor parameter");
        assertTrue(exception instanceof NvdException, "NvdDownloadException should be an instance of NvdException");
    }
}
