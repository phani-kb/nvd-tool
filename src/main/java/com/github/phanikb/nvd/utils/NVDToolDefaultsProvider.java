package com.github.phanikb.nvd.utils;

import java.io.File;
import java.util.Optional;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import picocli.CommandLine;

import com.github.phanikb.nvd.common.Util;

import static com.github.phanikb.nvd.common.Constants.CLI_PROPERTIES_FILE;

public class NVDToolDefaultsProvider implements CommandLine.IDefaultValueProvider {
    private static final String PROPERTY_SEPARATOR = ".";
    private static final Logger logger = LogManager.getLogger(NVDToolDefaultsProvider.class);

    @Override
    public String defaultValue(CommandLine.Model.ArgSpec argSpec) {
        Properties defaultProperties = loadDefaultProperties();
        String key = argSpec.isOption() ? ((CommandLine.Model.OptionSpec) argSpec).longestName() : argSpec.paramLabel();
        key = key.replaceFirst("^-+", "");
        CommandLine.Model.CommandSpec command = argSpec.command();
        StringBuilder keyBuilder = new StringBuilder(command.name() + PROPERTY_SEPARATOR + key);
        while (command.parent() != null) {
            command = command.parent();
            keyBuilder.insert(0, command.name() + PROPERTY_SEPARATOR);
        }
        key = keyBuilder.toString();
        return defaultProperties.getProperty(key);
    }

    private Properties loadDefaultProperties() {
        Optional<File> optionalFile = Util.findFileFromClasspath(CLI_PROPERTIES_FILE);
        File defaultPropertiesFile = optionalFile.orElse(null);
        if (defaultPropertiesFile == null) {
            logger.error("Default properties file {} not found", CLI_PROPERTIES_FILE);
            return new Properties();
        }
        return Util.loadProperties(defaultPropertiesFile);
    }
}
