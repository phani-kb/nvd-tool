package com.github.phanikb.nvd.cli;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import picocli.CommandLine;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MergeCommandTest {

    @Test
    void testDefaultConstructor() {
        MergeCommand command = new MergeCommand();
        assertNotNull(command);
    }

    @Test
    void testCommandLineIntegration() {
        MergeCommand command = new MergeCommand();
        CommandLine cli = new CommandLine(command);
        assertNotNull(cli);
        assertEquals(command, cli.getCommand());
    }

    @Test
    void testInheritance() {
        MergeCommand command = new MergeCommand();
        assertTrue(command instanceof BaseCommand);
        assertTrue(command instanceof java.util.concurrent.Callable);
    }

    @Test
    void testHelpOption() {
        assertDoesNotThrow(() -> {
            CommandLine cli = new CommandLine(new MergeCommand());
            cli.parseArgs("--help");
        });
    }

    @Test
    void testCommandAnnotation() {
        assertTrue(MergeCommand.class.isAnnotationPresent(CommandLine.Command.class));
        CommandLine.Command annotation = MergeCommand.class.getAnnotation(CommandLine.Command.class);
        assertEquals("merge", annotation.name());
    }

    @Test
    void testFieldsInitialization() {
        MergeCommand command = new MergeCommand();
        assertDoesNotThrow(() -> {
            assertNotNull(command);
            assertTrue(command instanceof BaseCommand);
        });
    }

    @Test
    void testMissingOptions() {
        MergeCommand command = new MergeCommand();
        CommandLine cli = new CommandLine(command);
        int exitCode = cli.execute();
        assertEquals(2, exitCode);
    }

    @Test
    void testGetOutFilename(@TempDir java.nio.file.Path tempDir) {
        MergeCommand command = new MergeCommand();
        CommandLine cli = new CommandLine(command);
        cli.parseArgs("-t", "CVE", "-i", tempDir.toString());
        assertEquals("nvd-cve-vulnerabilities.json", command.getOutFilename());
    }
}
