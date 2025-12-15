package com.github.phanikb.nvd.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RequestTrackerTest {

    private final int rateLimit = 5;
    private final long windowInMillis = 1000;
    private RequestTracker tracker;

    @BeforeEach
    void setUp() {
        tracker = new RequestTracker(rateLimit, windowInMillis);
    }

    @Test
    void testConstructor() {
        assertEquals(rateLimit, tracker.getMaxRequests(), "Max requests should match constructor parameter");
        assertEquals(windowInMillis, tracker.getWindowInMillis(), "Window size should match constructor parameter");
        assertNotNull(tracker.getRequestTimes(), "Request times map should not be null");
        assertEquals(0, tracker.size(), "Initial size should be 0");
    }

    @Test
    void testAddRequestBelowLimit() {
        for (int i = 0; i < rateLimit; i++) {
            boolean result = tracker.addRequest("request-" + i);
            assertTrue(result, "Adding request below rate limit should return true");
            assertEquals(i + 1, tracker.size(), "Size should increase after adding a request");
        }
    }

    @Test
    void testAddRequestAtLimit() {
        for (int i = 0; i < rateLimit; i++) {
            tracker.addRequest("request-" + i);
        }

        // Try to add one more
        boolean result = tracker.addRequest("one-too-many");
        assertFalse(result, "Adding request at rate limit should return false");
        assertEquals(rateLimit, tracker.size(), "Size should remain at rate limit");
    }

    @Test
    void testExpiredRequestsRemoval() throws InterruptedException {
        // Add one request
        tracker.addRequest("first-request");
        assertEquals(1, tracker.size(), "Size should be 1 after adding a request");

        // Wait for the window to expire
        Thread.sleep(windowInMillis + 100);

        // Add a new request, which should trigger removal of the expired one
        tracker.addRequest("new-request");
        assertEquals(1, tracker.size(), "Size should still be 1 after expired request is removed");
    }

    @Test
    void testMultipleRequestsSameKey() {
        String key = "same-key";
        for (int i = 0; i < 3; i++) {
            tracker.addRequest(key);
        }

        assertEquals(1, tracker.size(), "Size should be 1 when adding the same key multiple times");
    }

    @Test
    void testAddingRequestsAfterReset() throws InterruptedException {
        for (int i = 0; i < rateLimit; i++) {
            tracker.addRequest("request-" + i);
        }
        assertEquals(rateLimit, tracker.size(), "Size should be at rate limit");

        Thread.sleep(windowInMillis + 100);

        boolean result = tracker.addRequest("new-request");
        assertTrue(result, "Adding request after all expired should return true");
        assertEquals(1, tracker.size(), "Size should be 1 after all expired requests are removed");
    }
}
