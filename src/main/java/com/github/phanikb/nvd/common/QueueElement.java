package com.github.phanikb.nvd.common;

import java.io.File;
import java.net.URI;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class QueueElement {
    private final URI uri;
    private final File outFile;
    private final int startIndex;
    private final int endIndex;
    private final AtomicInteger attempts = new AtomicInteger(1);

    public void incrementAttempts() {
        attempts.incrementAndGet();
    }

    public int getAttempts() {
        return attempts.get();
    }

    public abstract String getKey();
}
