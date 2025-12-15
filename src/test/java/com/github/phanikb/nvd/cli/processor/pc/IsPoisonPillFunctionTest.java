package com.github.phanikb.nvd.cli.processor.pc;

import java.io.File;
import java.net.URI;
import java.util.Objects;

import org.junit.jupiter.api.Test;

import com.github.phanikb.nvd.common.QueueElement;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IsPoisonPillFunctionTest {

    @Test
    void testFunctionalInterfaceBasicUsage() {
        IsPoisonPillFunction isPoisonPill = element -> element.getStartIndex() == -1;

        URI uri = URI.create("https://example.com/test");
        File outFile = new File("test.json");

        QueueElement poisonElement = new TestQueueElement(uri, -1, -1, outFile);
        assertTrue(isPoisonPill.isPoisonPill(poisonElement));

        QueueElement normalElement = new TestQueueElement(uri, 0, 100, outFile);
        assertFalse(isPoisonPill.isPoisonPill(normalElement));
    }

    @Test
    void testFunctionalInterfaceWithLambda() {
        IsPoisonPillFunction isPoisonPill = element -> element.getStartIndex() < 0 && element.getEndIndex() < 0;

        URI uri = URI.create("https://example.com/test");
        File outFile = new File("test.json");

        QueueElement poisonElement = new TestQueueElement(uri, -1, -1, outFile);
        QueueElement normalElement = new TestQueueElement(uri, 0, 100, outFile);
        QueueElement partialPoisonElement = new TestQueueElement(uri, -1, 100, outFile);

        assertTrue(isPoisonPill.isPoisonPill(poisonElement));
        assertFalse(isPoisonPill.isPoisonPill(normalElement));
        assertFalse(isPoisonPill.isPoisonPill(partialPoisonElement));
    }

    @Test
    void testFunctionalInterfaceWithMethodReference() {
        IsPoisonPillFunction isPoisonPill = this::isElementPoisonPill;

        URI uri = URI.create("https://example.com/test");
        File outFile = new File("test.json");

        QueueElement poisonElement = new TestQueueElement(uri, -999, -999, outFile);
        QueueElement normalElement = new TestQueueElement(uri, 0, 100, outFile);

        assertTrue(isPoisonPill.isPoisonPill(poisonElement));
        assertFalse(isPoisonPill.isPoisonPill(normalElement));
    }

    @Test
    void testFunctionalInterfaceWithNullElement() {
        IsPoisonPillFunction isPoisonPill = Objects::isNull;

        assertTrue(isPoisonPill.isPoisonPill(null));

        URI uri = URI.create("https://example.com/test");
        File outFile = new File("test.json");
        QueueElement normalElement = new TestQueueElement(uri, 0, 100, outFile);

        assertFalse(isPoisonPill.isPoisonPill(normalElement));
    }

    @Test
    void testFunctionalInterfaceComposition() {
        IsPoisonPillFunction isNegativeIndex = element -> element.getStartIndex() < 0;
        IsPoisonPillFunction isNullFile = element -> element.getOutFile() == null;

        IsPoisonPillFunction isPoisonPill =
                element -> isNegativeIndex.isPoisonPill(element) || isNullFile.isPoisonPill(element);

        URI uri = URI.create("https://example.com/test");

        QueueElement negativeIndexElement = new TestQueueElement(uri, -1, 100, new File("test.json"));
        QueueElement nullFileElement = new TestQueueElement(uri, 0, 100, null);
        QueueElement normalElement = new TestQueueElement(uri, 0, 100, new File("test.json"));

        assertTrue(isPoisonPill.isPoisonPill(negativeIndexElement));
        assertTrue(isPoisonPill.isPoisonPill(nullFileElement));
        assertFalse(isPoisonPill.isPoisonPill(normalElement));
    }

    private boolean isElementPoisonPill(QueueElement element) {
        return element.getStartIndex() == -999 && element.getEndIndex() == -999;
    }

    private static class TestQueueElement extends QueueElement {
        public TestQueueElement(URI uri, int startIndex, int endIndex, File outFile) {
            super(uri, outFile, startIndex, endIndex);
        }

        @Override
        public String getKey() {
            return String.valueOf(getStartIndex());
        }
    }
}
