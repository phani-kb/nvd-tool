package com.github.phanikb.nvd.cli.processor.pc;

import java.util.concurrent.BlockingDeque;

import org.apache.hc.core5.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lombok.Getter;
import lombok.Setter;

import com.github.phanikb.nvd.common.HttpUtil;
import com.github.phanikb.nvd.common.NvdException;
import com.github.phanikb.nvd.common.QueueElement;
import com.github.phanikb.nvd.common.RequestTracker;
import com.github.phanikb.nvd.common.Util;
import com.github.phanikb.nvd.enums.FeedType;

import static com.github.phanikb.nvd.cli.processor.api.download.NvdHttpClientResponseHandler.getResponseHandler;

@Getter
@Setter
public class ConsumerHelper {
    private static final Logger logger = LogManager.getLogger(ConsumerHelper.class);
    private final RequestTracker requestTracker;
    private final BlockingDeque<QueueElement> downloadQueue;
    private final FeedType feedType;
    private IsPoisonPillFunction isPoisonPillFunction;

    public ConsumerHelper(FeedType feedType, BlockingDeque<QueueElement> downloadQueue) {
        this.feedType = feedType;
        this.downloadQueue = downloadQueue;
        int maxRequests = HttpUtil.getRateLimit();
        long windowSizeInMillis = HttpUtil.getRollingWindowSizeInSecs() * 1000L;
        this.requestTracker = new RequestTracker(maxRequests, windowSizeInMillis);
    }

    public boolean isPoisonPill(QueueElement element) {
        return isPoisonPillFunction.isPoisonPill(element);
    }

    public void processQueue() throws InterruptedException {
        while (true) {
            QueueElement element = downloadQueue.take();
            if (isPoisonPill(element)) {
                logger.debug(
                        "stopping thread {}, request tracker size {}",
                        Thread.currentThread().getName(),
                        requestTracker.size());
                break;
            }
            processElement(element);
        }
    }

    private void processElement(QueueElement element) {
        try {
            consumeElement(element);
            if (downloadQueue.size() % Util.getLogEveryNProcessedElements() == 0) {
                logger.info(
                        "processed element key: {}, attempt: {}, queue size: {}, thread: {}",
                        element.getKey(),
                        element.getAttempts(),
                        downloadQueue.size(),
                        Thread.currentThread().getName());
            }
        } catch (NvdException e) {
            handleException(element, e);
        }
    }

    private void consumeElement(QueueElement element) throws NvdException {
        String key = addRequestToTracker(element);
        logger.trace(
                "queue size: {}, key: {}, attempt: {}, request tracker size: {}, thread: {}",
                downloadQueue.size(),
                key,
                element.getAttempts(),
                requestTracker.size(),
                Thread.currentThread().getName());
        HttpUtil.downloadHttpGetRequest(element.getUri(), element.getOutFile(), getResponseHandler(feedType));
    }

    private String addRequestToTracker(QueueElement element) {
        String key = element.getKey();
        while (!requestTracker.addRequest(key)) {
            logger.info(
                    "waiting for request tracker to have space for key: {}, queue size: {}, request tracker size: {}, thread: {}",
                    key,
                    downloadQueue.size(),
                    requestTracker.size(),
                    Thread.currentThread().getName());
            Util.sleepQuietly(HttpUtil.getDownloadDelay());
        }
        return key;
    }

    private <T extends QueueElement> void handleException(T element, NvdException e) {
        String key = element.getKey();
        String message = e.getMessage();

        if (message.contains(String.valueOf(HttpStatus.SC_FORBIDDEN))) {
            logger.trace(
                    "retrying element: {}, queue size: {}, key: {}, attempt: {}",
                    message,
                    downloadQueue.size(),
                    key,
                    element.getAttempts());
            element.incrementAttempts();
            if (element.getAttempts() > Util.getMaxDownloadAttempts()) {
                logger.error("max attempts reached for element: {}", element);
                return;
            }
            Util.sleepQuietly(element.getAttempts());

            if (!downloadQueue.offerFirst(element)) {
                logger.error("unable to add element to queue for retry");
            }
        } else {
            logger.error(
                    "error processing element: {}, queue size: {}, key: {}, attempt: {}, ",
                    message,
                    downloadQueue.size(),
                    key,
                    element.getAttempts());
        }
    }
}
