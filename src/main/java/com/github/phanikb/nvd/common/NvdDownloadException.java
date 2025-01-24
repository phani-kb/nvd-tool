package com.github.phanikb.nvd.common;

import java.io.Serial;

public class NvdDownloadException extends NvdException {
    @Serial
    private static final long serialVersionUID = 1L;

    public NvdDownloadException(String message) {
        super(message);
    }

    public NvdDownloadException(String message, Throwable cause) {
        super(message, cause);
    }
}
