package com.github.phanikb.nvd.common;

import org.apache.hc.client5.http.ClientProtocolException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NvdForbiddenRequestExceptionTest {

    @Test
    public void testConstructorWithMessage() {
        String errorMessage = "Forbidden request: API key invalid";
        NvdForbiddenRequestException exception = new NvdForbiddenRequestException(errorMessage);

        assertEquals(errorMessage, exception.getMessage(), "Message should match constructor parameter");
        assertNull(exception.getCause(), "Cause should be null when not specified");
        assertTrue(
                exception instanceof ClientProtocolException,
                "NvdForbiddenRequestException should be an instance of ClientProtocolException");
    }
}
