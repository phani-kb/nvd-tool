package com.github.phanikb.nvd.cli.processor.pc;

import java.io.File;
import java.net.URI;

import org.junit.jupiter.api.Test;

import com.github.phanikb.nvd.common.QueueElement;

import static org.junit.jupiter.api.Assertions.*;

class StartIndexQETest {

    @Test
    void testConstructorAndGetters() {
        URI uri = URI.create("https://example.com/test");
        File outFile = new File("test-output.json");
        int startIndex = 100;
        int endIndex = 200;

        StartIndexQE queueElement = new StartIndexQE(uri, startIndex, endIndex, outFile);

        assertEquals(uri, queueElement.getUri());
        assertEquals(outFile, queueElement.getOutFile());
        assertEquals(startIndex, queueElement.getStartIndex());
        assertEquals(endIndex, queueElement.getEndIndex());
    }

    @Test
    void testToString() {
        URI uri = URI.create("https://example.com/test");
        File outFile = new File("test-output.json");
        StartIndexQE queueElement = new StartIndexQE(uri, 0, 100, outFile);

        assertEquals("test-output.json", queueElement.toString());
    }

    @Test
    void testGetKey() {
        URI uri = URI.create("https://example.com/test");
        File outFile = new File("test-output.json");
        int startIndex = 250;
        StartIndexQE queueElement = new StartIndexQE(uri, startIndex, 350, outFile);

        assertEquals("250", queueElement.getKey());
    }

    @Test
    void testInheritanceFromQueueElement() {
        URI uri = URI.create("https://example.com/test");
        File outFile = new File("test-output.json");
        StartIndexQE queueElement = new StartIndexQE(uri, 0, 100, outFile);

        assertInstanceOf(QueueElement.class, queueElement);
    }

    @Test
    void testObjectProperties() {
        URI uri = URI.create("https://example.com/test");
        File outFile = new File("test-output.json");

        StartIndexQE queueElement1 = new StartIndexQE(uri, 100, 200, outFile);
        StartIndexQE queueElement2 = new StartIndexQE(uri, 100, 200, outFile);
        StartIndexQE queueElement3 = new StartIndexQE(uri, 150, 250, outFile);

        // Test that objects with same properties have same key and toString
        assertEquals(queueElement1.getKey(), queueElement2.getKey());
        assertEquals(queueElement1.toString(), queueElement2.toString());

        // Test that objects with different properties have different keys
        assertNotEquals(queueElement1.getKey(), queueElement3.getKey());

        // Test that objects are not null and of correct type
        assertNotNull(queueElement1);
        assertInstanceOf(StartIndexQE.class, queueElement1);
    }

    @Test
    void testDifferentOutputFiles() {
        URI uri = URI.create("https://example.com/test");
        File outFile1 = new File("output1.json");
        File outFile2 = new File("output2.json");

        StartIndexQE queueElement1 = new StartIndexQE(uri, 0, 100, outFile1);
        StartIndexQE queueElement2 = new StartIndexQE(uri, 0, 100, outFile2);

        assertEquals("output1.json", queueElement1.toString());
        assertEquals("output2.json", queueElement2.toString());
        assertEquals(queueElement1.getKey(), queueElement2.getKey()); // Key is based on startIndex only
    }
}
