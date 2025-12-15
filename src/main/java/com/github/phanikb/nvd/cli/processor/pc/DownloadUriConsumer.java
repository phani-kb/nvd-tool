package com.github.phanikb.nvd.cli.processor.pc;

import com.github.phanikb.nvd.cli.processor.api.IApiDownloadUriConsumer;

public abstract class DownloadUriConsumer<T> implements Runnable, IApiDownloadUriConsumer {
    protected final T poison;

    protected DownloadUriConsumer(T poison) {
        this.poison = poison;
    }
}
