package com.github.phanikb.nvd.cli.processor.pc;

import java.io.File;
import java.net.URI;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.github.phanikb.nvd.common.QueueElement;

import static org.junit.jupiter.api.Assertions.*;

class ChangeDatesQETest {

    @Test
    void testConstructorAndGetters() {
        URI uri = URI.create("https://api.example.com/cve");
        LocalDateTime startDate = LocalDateTime.of(2023, 1, 1, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2023, 12, 31, 23, 59, 59);
        int startIndex = 100;
        int endIndex = 200;
        File outFile = new File("cve-changes.json");

        ChangeDatesQE queueElement = new ChangeDatesQE(uri, startDate, endDate, startIndex, endIndex, outFile);

        assertEquals(uri, queueElement.getUri());
        assertEquals(startDate, queueElement.getStartDate());
        assertEquals(endDate, queueElement.getEndDate());
        assertEquals(startIndex, queueElement.getStartIndex());
        assertEquals(endIndex, queueElement.getEndIndex());
        assertEquals(outFile, queueElement.getOutFile());
    }

    @Test
    void testInheritanceFromDatesQE() {
        URI uri = URI.create("https://api.example.com/cve");
        LocalDateTime startDate = LocalDateTime.now().minusDays(30);
        LocalDateTime endDate = LocalDateTime.now();
        File outFile = new File("test-output.json");

        ChangeDatesQE queueElement = new ChangeDatesQE(uri, startDate, endDate, 0, 100, outFile);

        assertInstanceOf(DatesQE.class, queueElement);
        assertInstanceOf(QueueElement.class, queueElement);
    }

    @Test
    void testToString() {
        URI uri = URI.create("https://api.example.com/cve");
        LocalDateTime startDate = LocalDateTime.of(2023, 6, 1, 12, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2023, 6, 30, 12, 0, 0);
        File outFile = new File("june-changes.json");

        ChangeDatesQE queueElement = new ChangeDatesQE(uri, startDate, endDate, 0, 50, outFile);

        assertEquals("june-changes.json", queueElement.toString());
    }

    @Test
    void testToStringWithNullFile() {
        URI uri = URI.create("https://api.example.com/cve");
        LocalDateTime startDate = LocalDateTime.of(2023, 6, 1, 12, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2023, 6, 30, 12, 0, 0);

        ChangeDatesQE queueElement = new ChangeDatesQE(uri, startDate, endDate, 0, 50, null);

        assertEquals("No output file", queueElement.toString());
    }

    @Test
    void testGetKey() {
        URI uri = URI.create("https://api.example.com/cve");
        LocalDateTime startDate = LocalDateTime.of(2023, 6, 1, 12, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2023, 6, 30, 12, 0, 0);
        int startIndex = 250;
        File outFile = new File("test.json");

        ChangeDatesQE queueElement = new ChangeDatesQE(uri, startDate, endDate, startIndex, 350, outFile);

        String expectedKey = String.format("%s:%s:%d", startDate, endDate, startIndex);
        assertEquals(expectedKey, queueElement.getKey());
    }

    @Test
    void testObjectProperties() {
        URI uri = URI.create("https://api.example.com/cve");
        LocalDateTime startDate = LocalDateTime.of(2023, 6, 1, 12, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2023, 6, 30, 12, 0, 0);
        File outFile = new File("test.json");

        ChangeDatesQE queueElement1 = new ChangeDatesQE(uri, startDate, endDate, 100, 200, outFile);
        ChangeDatesQE queueElement2 = new ChangeDatesQE(uri, startDate, endDate, 100, 200, outFile);
        ChangeDatesQE queueElement3 = new ChangeDatesQE(uri, startDate, endDate, 150, 250, outFile);

        // Test that objects with same properties have same key and toString
        assertEquals(queueElement1.getKey(), queueElement2.getKey());
        assertEquals(queueElement1.toString(), queueElement2.toString());

        // Test that objects with different properties have different keys
        assertNotEquals(queueElement1.getKey(), queueElement3.getKey());

        // Test that objects are not null and of correct type
        assertNotNull(queueElement1);
        assertInstanceOf(ChangeDatesQE.class, queueElement1);
    }

    @Test
    void testWithDifferentDateRanges() {
        URI uri = URI.create("https://api.example.com/cve");
        LocalDateTime startDate1 = LocalDateTime.of(2023, 1, 1, 0, 0, 0);
        LocalDateTime endDate1 = LocalDateTime.of(2023, 6, 30, 23, 59, 59);
        LocalDateTime startDate2 = LocalDateTime.of(2023, 7, 1, 0, 0, 0);
        LocalDateTime endDate2 = LocalDateTime.of(2023, 12, 31, 23, 59, 59);
        File outFile = new File("test.json");

        ChangeDatesQE queueElement1 = new ChangeDatesQE(uri, startDate1, endDate1, 0, 100, outFile);
        ChangeDatesQE queueElement2 = new ChangeDatesQE(uri, startDate2, endDate2, 0, 100, outFile);

        assertNotEquals(queueElement1.getKey(), queueElement2.getKey());
        assertEquals(startDate1, queueElement1.getStartDate());
        assertEquals(endDate1, queueElement1.getEndDate());
        assertEquals(startDate2, queueElement2.getStartDate());
        assertEquals(endDate2, queueElement2.getEndDate());
    }

    @Test
    void testWithSameDateRange() {
        URI uri = URI.create("https://api.example.com/cve");
        LocalDateTime dateTime = LocalDateTime.of(2023, 6, 15, 12, 0, 0);
        File outFile = new File("single-day.json");

        ChangeDatesQE queueElement = new ChangeDatesQE(uri, dateTime, dateTime, 0, 50, outFile);

        assertEquals(dateTime, queueElement.getStartDate());
        assertEquals(dateTime, queueElement.getEndDate());

        String expectedKey = String.format("%s:%s:%d", dateTime, dateTime, 0);
        assertEquals(expectedKey, queueElement.getKey());
    }
}
