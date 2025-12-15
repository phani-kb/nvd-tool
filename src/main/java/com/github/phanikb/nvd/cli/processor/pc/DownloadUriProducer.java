package com.github.phanikb.nvd.cli.processor.pc;

import com.github.phanikb.nvd.cli.processor.api.IApiDownloadUriProducer;

public abstract class DownloadUriProducer<T> implements Runnable, IApiDownloadUriProducer {
    protected final T poison;
    protected final int poisonPerCreator;

    protected DownloadUriProducer(T poison, int poisonPerCreator) {
        this.poison = poison;
        this.poisonPerCreator = poisonPerCreator;
    }
}
