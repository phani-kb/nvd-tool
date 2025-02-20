package com.github.phanikb.nvd.common;

import java.util.LinkedHashMap;

import lombok.Getter;

@Getter
public class RequestTracker {
    private final long windowInMillis;
    private final int maxRequests;
    private final LinkedHashMap<String, Long> requestTimes = new LinkedHashMap<>();

    public RequestTracker(int rateLimit, long windowSizeInMillis) {
        this.maxRequests = rateLimit;
        this.windowInMillis = windowSizeInMillis;
    }

    public synchronized boolean addRequest(String key) {
        long now = System.currentTimeMillis();

        // remove expired entries
        requestTimes.entrySet().removeIf(entry -> now - entry.getValue() > windowInMillis);

        if (requestTimes.size() < maxRequests) {
            requestTimes.put(key, now);
            return true;
        }
        return false;
    }

    public int size() {
        return requestTimes.size();
    }
}
