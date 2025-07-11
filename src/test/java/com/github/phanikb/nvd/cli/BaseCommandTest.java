package com.github.phanikb.nvd.cli;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import picocli.CommandLine;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BaseCommandTest {

    @TempDir
    Path tempDir;

    static class TestCommand extends BaseCommand {
        @Override
        public Integer call() {
            return super.call();
        }
    }

    private void setUpCommand(TestCommand command, File outputDir, String filename) throws Exception {
        BaseCommonOptions options = new BaseCommonOptions();

        if (outputDir != null) {
            Field outDirField = BaseCommonOptions.class.getDeclaredField("outDir");
            outDirField.setAccessible(true);
            outDirField.set(options, outputDir);
        }

        if (filename != null) {
            Field outFilenameField = BaseCommonOptions.class.getDeclaredField("outFilename");
            outFilenameField.setAccessible(true);
            outFilenameField.set(options, filename);
        }

        Field baseCommonOptionsField = BaseCommand.class.getDeclaredField("baseCommonOptions");
        baseCommonOptionsField.setAccessible(true);
        baseCommonOptionsField.set(command, options);

        CommandLine.Model.CommandSpec spec = CommandLine.Model.CommandSpec.create();
        Field specField = BaseCommand.class.getDeclaredField("spec");
        specField.setAccessible(true);
        specField.set(command, spec);
    }

    @Test
    void testDefaultConstructor() {
        TestCommand command = new TestCommand();
        assertNotNull(command);
    }

    @Test
    void testCallWithValidSetup() throws Exception {
        TestCommand command = new TestCommand();
        setUpCommand(command, tempDir.toFile(), null);

        Integer result = command.call();
        assertEquals(0, result);
    }

    @Test
    void testGetOutDir() throws Exception {
        TestCommand command = new TestCommand();
        File testDir = tempDir.toFile();
        setUpCommand(command, testDir, null);

        assertEquals(testDir, command.getOutDir());
    }

    @Test
    void testGetOutFilename() throws Exception {
        TestCommand command = new TestCommand();
        String filename = "test-output.json";
        setUpCommand(command, tempDir.toFile(), filename);

        assertEquals(filename, command.getOutFilename());
    }

    @Test
    void testValidateOptions() throws Exception {
        TestCommand command = new TestCommand();
        setUpCommand(command, tempDir.toFile(), null);

        assertDoesNotThrow(() -> command.validateOptions());
    }

    @Test
    void testValidateOptionsWithFilename() throws Exception {
        TestCommand command = new TestCommand();
        setUpCommand(command, tempDir.toFile(), "valid-file.json");

        assertDoesNotThrow(() -> command.validateOptions());
    }

    @Test
    void testBaseCommandInheritance() {
        TestCommand command = new TestCommand();
        assertTrue(command instanceof BaseCommand);
        assertTrue(command instanceof INvdBaseCommand);
    }

    @Test
    void testValidateDirectoryMethod() throws Exception {
        TestCommand command = new TestCommand();
        setUpCommand(command, tempDir.toFile(), null);

        assertDoesNotThrow(() -> {
            try {
                java.lang.reflect.Method method = BaseCommand.class.getDeclaredMethod("validateDirectory", File.class);
                method.setAccessible(true);
                method.invoke(command, (File) null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        assertDoesNotThrow(() -> {
            try {
                java.lang.reflect.Method method = BaseCommand.class.getDeclaredMethod("validateDirectory", File.class);
                method.setAccessible(true);
                method.invoke(command, tempDir.toFile());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
