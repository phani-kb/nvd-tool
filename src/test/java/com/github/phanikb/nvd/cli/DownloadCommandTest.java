package com.github.phanikb.nvd.cli;

import java.util.concurrent.Callable;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import picocli.CommandLine;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DownloadCommandTest {

    @Test
    void testDefaultConstructor() {
        DownloadCommand command = new DownloadCommand();
        assertNotNull(command);
    }

    @Test
    void testCommandLineIntegration() {
        DownloadCommand command = new DownloadCommand();
        CommandLine cli = new CommandLine(command);
        assertNotNull(cli);
        assertEquals(command, cli.getCommand());
    }

    @Test
    void testInheritance() {
        DownloadCommand command = new DownloadCommand();
        assertInstanceOf(BaseCommand.class, command);
        assertInstanceOf(Callable.class, command);
    }

    @Test
    void testHelpOption() {
        assertDoesNotThrow(() -> {
            CommandLine cli = new CommandLine(new DownloadCommand());
            cli.parseArgs("--help");
        });
    }

    @Test
    void testSubcommandSetup() {
        CommandLine cli = new CommandLine(new DownloadCommand());
        assertNotNull(cli.getCommandSpec());
        assertNotNull(cli.getCommandSpec().subcommands());
    }

    @Test
    void testCommandAnnotation() {
        assertTrue(DownloadCommand.class.isAnnotationPresent(CommandLine.Command.class));
        CommandLine.Command annotation = DownloadCommand.class.getAnnotation(CommandLine.Command.class);
        assertEquals("download", annotation.name());
    }

    @Test
    void testMissingSubcommand() {
        DownloadCommand command = new DownloadCommand();
        CommandLine cli = new CommandLine(command);
        int exitCode = cli.execute();
        assertEquals(2, exitCode);
    }

    @Test
    void testValidateOptions(@TempDir java.nio.file.Path tempDir) throws IllegalAccessException, NoSuchFieldException {
        DownloadCommand command = new DownloadCommand();

        // Set up baseCommonOptions using reflection (similar to BaseCommandTest)
        BaseCommonOptions options = new BaseCommonOptions();
        options.setOutDir(tempDir.toFile());

        // Use reflection to set the baseCommonOptions field
        java.lang.reflect.Field baseCommonOptionsField = BaseCommand.class.getDeclaredField("baseCommonOptions");
        baseCommonOptionsField.setAccessible(true);
        baseCommonOptionsField.set(command, options);

        // Also set up the spec field to avoid NPE
        CommandLine.Model.CommandSpec spec = CommandLine.Model.CommandSpec.create();
        java.lang.reflect.Field specField = BaseCommand.class.getDeclaredField("spec");
        specField.setAccessible(true);
        specField.set(command, spec);

        assertDoesNotThrow(command::validateOptions);
    }
}
