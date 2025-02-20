package com.github.phanikb.nvd.cli.processor.api;

import java.util.concurrent.ExecutorService;

import com.github.phanikb.nvd.common.QueueElement;

public interface IApiDownloadUriConsumer {
    void downloadUris(ExecutorService executorService, int numberOfConsumers, long delay);

    boolean isPoisonPill(QueueElement element);
}
