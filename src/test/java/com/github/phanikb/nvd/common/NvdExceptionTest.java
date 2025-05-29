package com.github.phanikb.nvd.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

public class NvdExceptionTest {

    @Test
    public void testConstructorWithMessage() {
        String errorMessage = "Test error message";
        NvdException exception = new NvdException(errorMessage);

        assertEquals(errorMessage, exception.getMessage(), "Message should match constructor parameter");
        assertNull(exception.getCause(), "Cause should be null when not specified");
    }

    @Test
    public void testConstructorWithMessageAndCause() {
        String errorMessage = "Test error message";
        Throwable cause = new RuntimeException("Original cause");
        NvdException exception = new NvdException(errorMessage, cause);

        assertEquals(errorMessage, exception.getMessage(), "Message should match constructor parameter");
        assertSame(cause, exception.getCause(), "Cause should match constructor parameter");
    }
}
