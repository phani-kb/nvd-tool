package com.github.phanikb.nvd.cli.api.download;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.concurrent.Callable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lombok.Getter;
import picocli.CommandLine;

import com.github.phanikb.nvd.cli.DownloadCommand;
import com.github.phanikb.nvd.cli.INvdCommand;
import com.github.phanikb.nvd.common.NvdProperties;

import static com.github.phanikb.nvd.common.Constants.NVD_PROPERTIES_FILE;

@Getter
@CommandLine.Command(
        name = "using-api",
        mixinStandardHelpOptions = true,
        subcommands = {CommandLine.HelpCommand.class},
        description = "Download data using the new APIs.")
public class ApiDownloadCommand implements Callable<Integer>, INvdCommand {
    private static final Logger logger = LogManager.getLogger(ApiDownloadCommand.class);
    private final String uuid;

    @CommandLine.ParentCommand
    protected DownloadCommand parent;

    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;

    @CommandLine.Option(
            names = {"-z", "--zip"},
            description = "Compress the output files. Default: ${DEFAULT-VALUE}",
            scope = CommandLine.ScopeType.LOCAL,
            defaultValue = "false")
    private boolean zip;

    public ApiDownloadCommand() {
        this.uuid = UUID.randomUUID().toString();
    }

    @Override
    public Integer call() {
        throw new CommandLine.ParameterException(spec.commandLine(), "Missing required subcommand");
    }

    @Override
    public void validateOptions() {
        parent.validateOptions();
        // check if api key set
        NvdProperties properties = NvdProperties.getInstance();
        String key = properties.getNvd().getApi().getKey();
        if (key == null || key.isEmpty()) {
            logger.warn("API key is not set in {}. Using public key with limited access.", NVD_PROPERTIES_FILE);
            logger.info(
                    "API key can be obtained from {}",
                    properties.getNvd().getApi().getKeyUrl());
        } else {
            logger.info("API key is set");
        }

        logger.info("compress output file(s): {}", zip);

        // check if version is set
        NvdProperties.ApiEndpointVersion version = properties.getNvd().getApi().getVersion();
        logger.info("API version: {}", version);
    }

    @Override
    public File getOutDir() {
        File parentOutDir = parent.getOutDir();
        Path subDirPath = Paths.get(parentOutDir.getAbsolutePath(), uuid);
        return subDirPath.toFile();
    }

    @Override
    public boolean isDeleteTempDir() {
        return parent.isDeleteTempDir();
    }

    @Override
    public String getOutFilename() {
        if (parent.getOutFilename() != null) {
            return parent.getOutFilename();
        }
        return uuid + ".json";
    }
}
