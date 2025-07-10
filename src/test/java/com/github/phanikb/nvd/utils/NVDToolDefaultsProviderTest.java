package com.github.phanikb.nvd.utils;

import java.io.File;
import java.util.Optional;
import java.util.Properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import picocli.CommandLine;

import com.github.phanikb.nvd.common.Util;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NVDToolDefaultsProviderTest {

    private NVDToolDefaultsProvider provider;
    private CommandLine.Model.OptionSpec mockOptionSpec;
    private CommandLine.Model.CommandSpec mockCommandSpec;

    @BeforeEach
    void setUp() {
        provider = new NVDToolDefaultsProvider();
        mockOptionSpec = mock(CommandLine.Model.OptionSpec.class);
        mockCommandSpec = mock(CommandLine.Model.CommandSpec.class);
    }

    @Test
    void testProviderImplementsInterface() {
        assertInstanceOf(CommandLine.IDefaultValueProvider.class, provider);
    }

    @Test
    void testProviderCanBeInstantiated() {
        assertNotNull(provider);
    }

    @Test
    void testDefaultValueWithNullArgSpec() {
        // The method doesn't handle null, so it should throw a NullPointerException
        assertThrows(NullPointerException.class, () -> provider.defaultValue(null));
    }

    @Test
    void testDefaultValueWithValidOptionSpec() {
        // Setup mocks
        when(mockOptionSpec.isOption()).thenReturn(true);
        when(mockOptionSpec.longestName()).thenReturn("--test-option");
        when(mockOptionSpec.command()).thenReturn(mockCommandSpec);
        when(mockCommandSpec.name()).thenReturn("testCommand");
        when(mockCommandSpec.parent()).thenReturn(null);

        try (MockedStatic<Util> utilMock = Mockito.mockStatic(Util.class)) {
            Properties testProps = new Properties();
            testProps.setProperty("testCommand.test-option", "testValue");

            utilMock.when(() -> Util.findFileFromClasspath(anyString()))
                    .thenReturn(Optional.of(new File("test.properties")));
            utilMock.when(() -> Util.loadProperties(any(File.class))).thenReturn(testProps);

            String result = provider.defaultValue(mockOptionSpec);
            assertEquals("testValue", result);
        }
    }

    @Test
    void testDefaultValueWithNonOptionArgSpec() {
        CommandLine.Model.PositionalParamSpec mockPositionalSpec = mock(CommandLine.Model.PositionalParamSpec.class);

        when(mockPositionalSpec.isOption()).thenReturn(false);
        when(mockPositionalSpec.paramLabel()).thenReturn("FILE");
        when(mockPositionalSpec.command()).thenReturn(mockCommandSpec);
        when(mockCommandSpec.name()).thenReturn("testCommand");
        when(mockCommandSpec.parent()).thenReturn(null);

        try (MockedStatic<Util> utilMock = Mockito.mockStatic(Util.class)) {
            Properties testProps = new Properties();
            testProps.setProperty("testCommand.FILE", "defaultFile.txt");

            utilMock.when(() -> Util.findFileFromClasspath(anyString()))
                    .thenReturn(Optional.of(new File("test.properties")));
            utilMock.when(() -> Util.loadProperties(any(File.class))).thenReturn(testProps);

            String result = provider.defaultValue(mockPositionalSpec);
            assertEquals("defaultFile.txt", result);
        }
    }

    @Test
    void testDefaultValueWithNestedCommands() {
        CommandLine.Model.CommandSpec parentCommandSpec = mock(CommandLine.Model.CommandSpec.class);

        when(mockOptionSpec.isOption()).thenReturn(true);
        when(mockOptionSpec.longestName()).thenReturn("--nested-option");
        when(mockOptionSpec.command()).thenReturn(mockCommandSpec);
        when(mockCommandSpec.name()).thenReturn("subCommand");
        when(mockCommandSpec.parent()).thenReturn(parentCommandSpec);
        when(parentCommandSpec.name()).thenReturn("parentCommand");
        when(parentCommandSpec.parent()).thenReturn(null);

        try (MockedStatic<Util> utilMock = Mockito.mockStatic(Util.class)) {
            Properties testProps = new Properties();
            testProps.setProperty("parentCommand.subCommand.nested-option", "nestedValue");

            utilMock.when(() -> Util.findFileFromClasspath(anyString()))
                    .thenReturn(Optional.of(new File("test.properties")));
            utilMock.when(() -> Util.loadProperties(any(File.class))).thenReturn(testProps);

            String result = provider.defaultValue(mockOptionSpec);
            assertEquals("nestedValue", result);
        }
    }

    @Test
    void testDefaultValueWithMissingPropertiesFile() {
        when(mockOptionSpec.isOption()).thenReturn(true);
        when(mockOptionSpec.longestName()).thenReturn("--test-option");
        when(mockOptionSpec.command()).thenReturn(mockCommandSpec);
        when(mockCommandSpec.name()).thenReturn("testCommand");
        when(mockCommandSpec.parent()).thenReturn(null);

        try (MockedStatic<Util> utilMock = Mockito.mockStatic(Util.class)) {
            utilMock.when(() -> Util.findFileFromClasspath(anyString())).thenReturn(Optional.empty());

            String result = provider.defaultValue(mockOptionSpec);
            assertNull(result);
        }
    }

    @Test
    void testDefaultValueWithMissingProperty() {
        when(mockOptionSpec.isOption()).thenReturn(true);
        when(mockOptionSpec.longestName()).thenReturn("--missing-option");
        when(mockOptionSpec.command()).thenReturn(mockCommandSpec);
        when(mockCommandSpec.name()).thenReturn("testCommand");
        when(mockCommandSpec.parent()).thenReturn(null);

        try (MockedStatic<Util> utilMock = Mockito.mockStatic(Util.class)) {
            Properties emptyProps = new Properties();

            utilMock.when(() -> Util.findFileFromClasspath(anyString()))
                    .thenReturn(Optional.of(new File("test.properties")));
            utilMock.when(() -> Util.loadProperties(any(File.class))).thenReturn(emptyProps);

            String result = provider.defaultValue(mockOptionSpec);
            assertNull(result);
        }
    }

    @Test
    void testOptionNameCleaning() {
        when(mockOptionSpec.isOption()).thenReturn(true);
        when(mockOptionSpec.longestName()).thenReturn("--with-dashes-option");
        when(mockOptionSpec.command()).thenReturn(mockCommandSpec);
        when(mockCommandSpec.name()).thenReturn("testCommand");
        when(mockCommandSpec.parent()).thenReturn(null);

        try (MockedStatic<Util> utilMock = Mockito.mockStatic(Util.class)) {
            Properties testProps = new Properties();
            testProps.setProperty("testCommand.with-dashes-option", "cleanedValue");

            utilMock.when(() -> Util.findFileFromClasspath(anyString()))
                    .thenReturn(Optional.of(new File("test.properties")));
            utilMock.when(() -> Util.loadProperties(any(File.class))).thenReturn(testProps);

            String result = provider.defaultValue(mockOptionSpec);
            assertEquals("cleanedValue", result);
        }
    }
}
