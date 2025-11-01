package com.github.phanikb.nvd.cli.processor.pc;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutorService;

import org.apache.hc.core5.util.TimeValue;

import com.github.phanikb.nvd.cli.processor.api.IApiDownloadUriConsumer;
import com.github.phanikb.nvd.common.QueueElement;
import com.github.phanikb.nvd.common.Util;
import com.github.phanikb.nvd.enums.FeedType;

public class DatesConsumer extends DatesProcessor<LocalDateTime> implements IApiDownloadUriConsumer {
    private final ConsumerHelper consumerHelper;

    private DatesConsumer(
            FeedType feedType,
            LocalDateTime poison,
            Path outDir,
            String outFilePrefix,
            BlockingDeque<QueueElement> downloadQueue,
            ConsumerHelper consumerHelper) {
        super(feedType, poison, outDir, outFilePrefix, downloadQueue);
        this.consumerHelper = consumerHelper;
    }

    public static DatesConsumer create(
            FeedType feedType,
            LocalDateTime poison,
            Path outDir,
            String outFilePrefix,
            BlockingDeque<QueueElement> downloadQueue) {
        ConsumerHelper helper = new ConsumerHelper(feedType, downloadQueue);
        DatesConsumer consumer = new DatesConsumer(feedType, poison, outDir, outFilePrefix, downloadQueue, helper);
        helper.setIsPoisonPillFunction(consumer::isPoisonPill);
        return consumer;
    }

    @Override
    public void run() {
        try {
            consumerHelper.processQueue();
        } catch (InterruptedException e) {
            logger.error("thread interrupted: {}", e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public boolean isPoisonPill(QueueElement element) {
        ChangeDatesQE changeDatesQE = (ChangeDatesQE) element;
        return changeDatesQE.getStartDate() == poison || changeDatesQE.getEndDate() == poison;
    }

    @Override
    public void downloadUris(ExecutorService executorService, int numberOfConsumers, long delay) {
        for (int i = 0; i < numberOfConsumers; i++) {
            executorService.submit(
                    new DatesConsumer(feedType, poison, outDir, outFilePrefix, downloadQueue, consumerHelper));
            Util.sleepQuietly(TimeValue.ofMilliseconds(delay));
        }
    }
}
