package com.github.phanikb.nvd.common;

import org.apache.hc.client5.http.ClientProtocolException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;

class NvdForbiddenRequestExceptionTest {

    @Test
    void testConstructorWithMessage() {
        String errorMessage = "Forbidden request: API key invalid";
        NvdForbiddenRequestException exception = new NvdForbiddenRequestException(errorMessage);

        assertEquals(errorMessage, exception.getMessage(), "Message should match constructor parameter");
        assertNull(exception.getCause(), "Cause should be null when not specified");
        assertInstanceOf(
                ClientProtocolException.class,
                exception,
                "NvdForbiddenRequestException should be an instance of ClientProtocolException");
    }
}
