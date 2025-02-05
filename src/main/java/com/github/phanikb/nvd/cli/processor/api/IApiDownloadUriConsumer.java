package com.github.phanikb.nvd.cli.processor.api;

import java.util.concurrent.ExecutorService;

public interface IApiDownloadUriConsumer {
    void downloadUris(ExecutorService executorService, int numberOfConsumers, long delay);
}
