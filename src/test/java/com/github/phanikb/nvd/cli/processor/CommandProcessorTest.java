package com.github.phanikb.nvd.cli.processor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.github.phanikb.nvd.common.NvdDownloadException;
import com.github.phanikb.nvd.common.NvdException;
import com.github.phanikb.nvd.enums.DownloadMode;
import com.github.phanikb.nvd.enums.FeedType;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommandProcessorTest {

    @TempDir
    Path tempDir;

    private TestCommandProcessor commandProcessor;
    private File validOutDir;
    private File nonExistentDir;
    private File fileNotDir;

    /** Concrete test implementation of the abstract CommandProcessor class */
    static class TestCommandProcessor extends CommandProcessor {
        public TestCommandProcessor(DownloadMode dwnMode, FeedType feedType, File outDir) {
            super(dwnMode, feedType, outDir);
        }

        @Override
        public void process() {
            // No-op for testing purposes
        }
    }

    @BeforeEach
    void setUp() throws IOException {
        validOutDir = tempDir.toFile();
        nonExistentDir = new File(tempDir.toFile(), "non-existent");
        fileNotDir = new File(tempDir.toFile(), "test-file.txt");
        fileNotDir.createNewFile(); // Create a file to test directory validation
    }

    @Test
    void testConstructorAndGetters() {
        commandProcessor = new TestCommandProcessor(DownloadMode.API, FeedType.CVE, validOutDir);

        assertEquals(DownloadMode.API, commandProcessor.getDwnMode());
        assertEquals(FeedType.CVE, commandProcessor.getFeedType());
        assertEquals(validOutDir, commandProcessor.getOutDir());
    }

    @Test
    void testConstructorWithAllParameters() {
        commandProcessor = new TestCommandProcessor(DownloadMode.URI, FeedType.CPE, validOutDir);

        assertEquals(DownloadMode.URI, commandProcessor.getDwnMode());
        assertEquals(FeedType.CPE, commandProcessor.getFeedType());
        assertEquals(validOutDir, commandProcessor.getOutDir());
    }

    @Test
    void testPreProcessWithValidDirectory() {
        commandProcessor = new TestCommandProcessor(DownloadMode.API, FeedType.CVE, validOutDir);

        assertDoesNotThrow(() -> commandProcessor.preProcess());
    }

    @Test
    void testPreProcessWithNullOutputDir() {
        commandProcessor = new TestCommandProcessor(DownloadMode.API, FeedType.CVE, null);

        NvdDownloadException exception = assertThrows(NvdDownloadException.class, () -> commandProcessor.preProcess());
        assertEquals("outputDir is null", exception.getMessage());
    }

    @Test
    void testPreProcessWithNonExistentDirectory() {
        commandProcessor = new TestCommandProcessor(DownloadMode.API, FeedType.CVE, nonExistentDir);

        NvdDownloadException exception = assertThrows(NvdDownloadException.class, () -> commandProcessor.preProcess());
        assertTrue(exception.getMessage().contains("does not exist"));
        assertTrue(exception.getMessage().contains(nonExistentDir.toString()));
    }

    @Test
    void testPreProcessWithFileInsteadOfDirectory() {
        commandProcessor = new TestCommandProcessor(DownloadMode.API, FeedType.CVE, fileNotDir);

        NvdDownloadException exception = assertThrows(NvdDownloadException.class, () -> commandProcessor.preProcess());
        assertTrue(exception.getMessage().contains("is not a directory"));
        assertTrue(exception.getMessage().contains(fileNotDir.toString()));
    }

    @Test
    void testPostProcessDoesNotThrow() {
        commandProcessor = new TestCommandProcessor(DownloadMode.API, FeedType.CVE, validOutDir);

        assertDoesNotThrow(() -> commandProcessor.postProcess());
    }

    @Test
    void testPostProcessWithNullOutputDir() {
        commandProcessor = new TestCommandProcessor(DownloadMode.API, FeedType.CVE, null);

        assertDoesNotThrow(() -> commandProcessor.postProcess());
    }

    @Test
    void testLoggerIsNotNull() {
        commandProcessor = new TestCommandProcessor(DownloadMode.API, FeedType.CVE, validOutDir);

        assertNotNull(CommandProcessor.logger);
        assertEquals("com.github.phanikb.nvd.cli.processor.CommandProcessor", CommandProcessor.logger.getName());
    }

    @Test
    void testWithAllDownloadModes() {
        for (DownloadMode mode : DownloadMode.values()) {
            commandProcessor = new TestCommandProcessor(mode, FeedType.CVE, validOutDir);
            assertEquals(mode, commandProcessor.getDwnMode());
            assertDoesNotThrow(() -> commandProcessor.preProcess());
        }
    }

    @Test
    void testWithAllFeedTypes() {
        for (FeedType feedType : FeedType.values()) {
            commandProcessor = new TestCommandProcessor(DownloadMode.API, feedType, validOutDir);
            assertEquals(feedType, commandProcessor.getFeedType());
            assertDoesNotThrow(() -> commandProcessor.preProcess());
        }
    }

    @Test
    void testICommandProcessorInterfaceImplementation() {
        commandProcessor = new TestCommandProcessor(DownloadMode.API, FeedType.CVE, validOutDir);

        assertInstanceOf(ICommandProcessor.class, commandProcessor);

        assertEquals(FeedType.CVE, commandProcessor.getFeedType());
        assertEquals(validOutDir, commandProcessor.getOutDir());
        assertDoesNotThrow(() -> commandProcessor.preProcess());
        assertDoesNotThrow(() -> commandProcessor.process());
        assertDoesNotThrow(() -> commandProcessor.postProcess());
    }

    @Test
    void testPreProcessExceptionInheritance() {
        commandProcessor = new TestCommandProcessor(DownloadMode.API, FeedType.CVE, null);

        Exception exception = assertThrows(NvdDownloadException.class, () -> commandProcessor.preProcess());

        assertInstanceOf(NvdException.class, exception);
    }

    @Test
    void testFieldsAreImmutableAfterConstruction() {
        DownloadMode originalMode = DownloadMode.API;
        FeedType originalFeedType = FeedType.CVE;
        File originalOutDir = validOutDir;

        commandProcessor = new TestCommandProcessor(originalMode, originalFeedType, originalOutDir);

        assertEquals(originalMode, commandProcessor.getDwnMode());
        assertEquals(originalFeedType, commandProcessor.getFeedType());
        assertEquals(originalOutDir, commandProcessor.getOutDir());
    }

    @Test
    void testDirectoryValidationOrder() {
        commandProcessor = new TestCommandProcessor(DownloadMode.API, FeedType.CVE, null);

        NvdDownloadException exception = assertThrows(NvdDownloadException.class, () -> commandProcessor.preProcess());
        assertEquals("outputDir is null", exception.getMessage());

        commandProcessor = new TestCommandProcessor(DownloadMode.API, FeedType.CVE, nonExistentDir);

        exception = assertThrows(NvdDownloadException.class, () -> commandProcessor.preProcess());
        assertTrue(exception.getMessage().contains("does not exist"));
    }

    @Test
    void testMultiplePreProcessCalls() {
        commandProcessor = new TestCommandProcessor(DownloadMode.API, FeedType.CVE, validOutDir);

        assertDoesNotThrow(() -> commandProcessor.preProcess());
        assertDoesNotThrow(() -> commandProcessor.preProcess());
        assertDoesNotThrow(() -> commandProcessor.preProcess());
    }

    @Test
    void testMultiplePostProcessCalls() {
        commandProcessor = new TestCommandProcessor(DownloadMode.API, FeedType.CVE, validOutDir);

        assertDoesNotThrow(() -> commandProcessor.postProcess());
        assertDoesNotThrow(() -> commandProcessor.postProcess());
        assertDoesNotThrow(() -> commandProcessor.postProcess());
    }

    @Test
    void testConstructorWithNullParameters() {
        assertDoesNotThrow(() -> new TestCommandProcessor(null, null, null));

        commandProcessor = new TestCommandProcessor(null, null, null);
        assertNull(commandProcessor.getDwnMode());
        assertNull(commandProcessor.getFeedType());
        assertNull(commandProcessor.getOutDir());
    }

    @Test
    void testPreProcessErrorMessagesAreDescriptive() {
        commandProcessor = new TestCommandProcessor(DownloadMode.API, FeedType.CVE, null);
        NvdDownloadException exception = assertThrows(NvdDownloadException.class, () -> commandProcessor.preProcess());
        assertEquals("outputDir is null", exception.getMessage());

        commandProcessor = new TestCommandProcessor(DownloadMode.API, FeedType.CVE, nonExistentDir);
        exception = assertThrows(NvdDownloadException.class, () -> commandProcessor.preProcess());
        String expectedMessage = "outputDir " + nonExistentDir + " does not exist";
        assertEquals(expectedMessage, exception.getMessage());

        commandProcessor = new TestCommandProcessor(DownloadMode.API, FeedType.CVE, fileNotDir);
        exception = assertThrows(NvdDownloadException.class, () -> commandProcessor.preProcess());
        String expectedFileMessage = "outputDir " + fileNotDir + " is not a directory";
        assertEquals(expectedFileMessage, exception.getMessage());
    }

    @Test
    void testCompleteWorkflow() {
        commandProcessor = new TestCommandProcessor(DownloadMode.URI, FeedType.CPE_MATCH, validOutDir);

        assertDoesNotThrow(() -> {
            commandProcessor.preProcess();
            commandProcessor.process();
            commandProcessor.postProcess();
        });
    }
}
