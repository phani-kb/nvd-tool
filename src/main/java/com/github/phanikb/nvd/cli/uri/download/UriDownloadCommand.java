package com.github.phanikb.nvd.cli.uri.download;

import java.io.File;
import java.util.concurrent.Callable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lombok.Getter;
import picocli.CommandLine;

import com.github.phanikb.nvd.cli.DownloadCommand;
import com.github.phanikb.nvd.cli.INvdCommand;
import com.github.phanikb.nvd.enums.ArchiveType;

@Getter
@CommandLine.Command(
        name = "using-uri",
        mixinStandardHelpOptions = true,
        subcommands = {
            CveUriDownloadCommand.class,
            CpeUriDownloadCommand.class,
            CpeMatchUriDownloadCommand.class,
            CweUriDownloadCommand.class,
            CommandLine.HelpCommand.class
        },
        description = "Download using traditional vulnerability data feeds.")
public class UriDownloadCommand implements Callable<Integer>, INvdCommand {
    private static final Logger logger = LogManager.getLogger(UriDownloadCommand.class);

    @CommandLine.ParentCommand
    protected DownloadCommand parent;

    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;

    @CommandLine.Option(
            names = {"-x", "--extract"},
            description = "Extract the downloaded files. Default: ${DEFAULT-VALUE}",
            defaultValue = "false")
    private boolean extract;

    @CommandLine.Option(
            names = {"-a", "--archive-type"},
            type = ArchiveType.class,
            description = "Archive type. Valid values: ${COMPLETION-CANDIDATES}.",
            paramLabel = "TYPE",
            defaultValue = "ZIP")
    private ArchiveType archiveType;

    @Override
    public Integer call() {
        throw new CommandLine.ParameterException(spec.commandLine(), "Missing required subcommand");
    }

    @Override
    public void validateOptions() {
        parent.validateOptions();
        logger.info("extract downloaded file(s): {}", extract);
        logger.info("archive type: {}", archiveType);
    }

    @Override
    public File getOutDir() {
        return parent.getOutDir();
    }

    @Override
    public boolean isDeleteTempDir() {
        return parent.isDeleteTempDir();
    }

    @Override
    public String getOutFilename() {
        return parent.getOutFilename();
    }
}
