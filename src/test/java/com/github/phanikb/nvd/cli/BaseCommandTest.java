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
import static org.junit.jupiter.api.Assertions.assertThrows;
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

        CommandLine cmdLine = new CommandLine(command);
        Field specField = BaseCommand.class.getDeclaredField("spec");
        specField.setAccessible(true);
        specField.set(command, cmdLine.getCommandSpec());
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

    @Test
    void testValidateDirectoryThrowsIfNotExists() throws Exception {
        TestCommand command = new TestCommand();
        File nonExistentDir = new File(tempDir.toFile(), "doesnotexist");
        setUpCommand(command, nonExistentDir, null);
        CommandLine.ParameterException ex =
                assertThrows(CommandLine.ParameterException.class, () -> command.validateDirectory(nonExistentDir));
        assertTrue(ex.getMessage().contains("directory does not exist"));
    }

    @Test
    void testValidateDirectoryThrowsIfNotDirectory() throws Exception {
        TestCommand command = new TestCommand();
        File file = new File(tempDir.toFile(), "notadir.txt");
        file.createNewFile();
        setUpCommand(command, file, null);
        CommandLine.ParameterException ex =
                assertThrows(CommandLine.ParameterException.class, () -> command.validateDirectory(file));
        assertTrue(ex.getMessage().contains("is not a directory"));
    }

    @Test
    void testValidateOutDirectoryThrowsIfNotWritable() throws Exception {
        TestCommand command = new TestCommand();
        File dir = tempDir.toFile();
        dir.setWritable(false);
        setUpCommand(command, dir, null);
        CommandLine.ParameterException ex =
                assertThrows(CommandLine.ParameterException.class, () -> command.validateOutDirectory(dir));
        assertTrue(ex.getMessage().contains("is not writable"));
        dir.setWritable(true);
    }

    @Test
    void testValidateOutputFileThrowsIfContainsSeparator() throws Exception {
        TestCommand command = new TestCommand();
        setUpCommand(command, tempDir.toFile(), "invalid/path.txt");
        CommandLine.ParameterException ex = assertThrows(
                CommandLine.ParameterException.class,
                () -> command.validateOutputFile("invalid" + File.separator + "path.txt"));
        assertTrue(ex.getMessage().contains("contains path separator"));
    }
}
