package com.github.phanikb.nvd.common;

import java.io.File;
import java.net.URI;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QueueElementTest {

    private static class TestQueueElement extends QueueElement {
        private final String key;

        public TestQueueElement(URI uri, File outFile, int startIndex, int endIndex, String key) {
            super(uri, outFile, startIndex, endIndex);
            this.key = key;
        }

        @Override
        public String getKey() {
            return key;
        }
    }

    @Test
    public void testConstructorAndGetters() {
        URI uri = URI.create("https://example.com/test");
        File outFile = new File("test.json");
        int startIndex = 1;
        int endIndex = 10;
        String key = "test-key";

        TestQueueElement element = new TestQueueElement(uri, outFile, startIndex, endIndex, key);

        assertEquals(uri, element.getUri(), "URI should match constructor parameter");
        assertEquals(outFile, element.getOutFile(), "Output file should match constructor parameter");
        assertEquals(startIndex, element.getStartIndex(), "Start index should match constructor parameter");
        assertEquals(endIndex, element.getEndIndex(), "End index should match constructor parameter");
        assertEquals(key, element.getKey(), "Key should match constructor parameter");
    }

    @Test
    public void testAttemptsTracking() {
        URI uri = URI.create("https://example.com/test");
        File outFile = new File("test.json");
        TestQueueElement element = new TestQueueElement(uri, outFile, 1, 10, "test-key");

        assertEquals(1, element.getAttempts(), "Initial attempts should be 1");

        element.incrementAttempts();
        assertEquals(2, element.getAttempts(), "Attempts should be 2 after incrementing once");

        element.incrementAttempts();
        element.incrementAttempts();
        assertEquals(4, element.getAttempts(), "Attempts should be 4 after incrementing 3 times total");
    }
}
