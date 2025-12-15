package com.github.phanikb.nvd.utils;

import java.util.concurrent.Callable;

import org.junit.jupiter.api.Test;

import picocli.CommandLine;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NVDToolTest {

    @Test
    void testDefaultConstructor() {
        NVDTool tool = new NVDTool();
        assertNotNull(tool);
    }

    @Test
    void testCommandLineIntegration() {
        NVDTool tool = new NVDTool();
        CommandLine cli = new CommandLine(tool);
        assertNotNull(cli);
        assertEquals(tool, cli.getCommand());
    }

    @Test
    void testHelpCommand() {
        assertDoesNotThrow(() -> {
            CommandLine cli = new CommandLine(new NVDTool());
            cli.parseArgs("--help");
        });
    }

    @Test
    void testVersionCommand() {
        assertDoesNotThrow(() -> {
            CommandLine cli = new CommandLine(new NVDTool());
            cli.parseArgs("--version");
        });
    }

    @Test
    void testSubcommands() {
        CommandLine cli = new CommandLine(new NVDTool());
        assertTrue(cli.getSubcommands().containsKey("download"));
        assertTrue(cli.getSubcommands().containsKey("merge"));
        assertTrue(cli.getSubcommands().containsKey("help"));
    }

    @Test
    void testImplementsCallable() {
        NVDTool tool = new NVDTool();
        assertInstanceOf(Callable.class, tool);
    }

    @Test
    void testExecutionStrategyMethod() {
        assertDoesNotThrow(() -> {
            java.lang.reflect.Method method =
                    NVDTool.class.getDeclaredMethod("executionStrategy", CommandLine.ParseResult.class);
            assertNotNull(method);
            assertEquals(int.class, method.getReturnType());
        });
    }

    @Test
    void testClassAnnotations() {
        assertTrue(NVDTool.class.isAnnotationPresent(CommandLine.Command.class));
        CommandLine.Command annotation = NVDTool.class.getAnnotation(CommandLine.Command.class);
        assertEquals("NVDTool", annotation.name());
        assertTrue(annotation.mixinStandardHelpOptions());
    }
}
