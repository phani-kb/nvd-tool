package com.github.phanikb.nvd.common;

import java.io.Serial;

import org.apache.hc.client5.http.ClientProtocolException;

public class NvdForbiddenRequestException extends ClientProtocolException {
    @Serial
    private static final long serialVersionUID = 1L;

    public NvdForbiddenRequestException(String s) {
        super(s);
    }
}
