package com.github.phanikb.nvd.common;

import java.io.Serial;

public class NvdException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    public NvdException(String message) {
        super(message);
    }

    public NvdException(String message, Throwable cause) {
        super(message, cause);
    }
}
