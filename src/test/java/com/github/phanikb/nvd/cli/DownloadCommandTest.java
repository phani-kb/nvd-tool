package com.github.phanikb.nvd.cli;

import org.junit.jupiter.api.Test;

import picocli.CommandLine;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
        assertTrue(command instanceof BaseCommand);
        assertTrue(command instanceof java.util.concurrent.Callable);
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
}
