package com.github.phanikb.nvd.cli.processor.api.download;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.hc.client5.http.HttpResponseException;
import org.apache.hc.client5.http.impl.DefaultHttpRequestRetryStrategy;
import org.apache.hc.core5.http.HttpRequest;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.apache.hc.core5.util.TimeValue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.phanikb.nvd.common.Util;

public class CustomHttpRequestRetryStrategy extends DefaultHttpRequestRetryStrategy {
    private static final Logger logger = LogManager.getLogger(CustomHttpRequestRetryStrategy.class);
    private final int maxRetries;
    private final TimeValue retryInterval;

    public CustomHttpRequestRetryStrategy(int maxRetries, TimeValue retryInterval) {
        super(maxRetries, retryInterval);
        this.maxRetries = maxRetries;
        this.retryInterval = retryInterval;
    }

    @Override
    public boolean retryRequest(HttpRequest request, IOException exception, int execCount, HttpContext context) {
        logger.warn("retrying request: {} ", request.getRequestUri());
        if (execCount > maxRetries) {
            return false;
        }

        if (exception instanceof HttpResponseException
                && ((HttpResponseException) exception).getStatusCode() == HttpStatus.SC_FORBIDDEN) {
            try {
                boolean isTestMode = Boolean.parseBoolean(System.getProperty("nvd.test.mode", "false"));
                if (isTestMode) {
                    TimeValue testDelay = TimeValue.of(Math.min(100, execCount * 10), TimeUnit.MILLISECONDS);
                    logger.info("retrying request (test mode) after {} ms", testDelay.toMilliseconds());
                    Thread.sleep(testDelay.toMilliseconds());
                } else {
                    logger.info("retrying request after {} seconds", retryInterval.toSeconds());
                    Util.sleep(execCount, retryInterval);
                }
                return true;
            } catch (InterruptedException e) {
                logger.error("sleep interrupted", e);
                Thread.currentThread().interrupt();
                return false;
            }
        }

        return super.retryRequest(request, exception, execCount, context);
    }

    @Override
    public TimeValue getRetryInterval(HttpResponse response, int execCount, HttpContext context) {
        return retryInterval;
    }
}
