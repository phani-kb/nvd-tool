package com.github.phanikb.nvd.cli;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lombok.Getter;
import picocli.CommandLine;

import com.github.phanikb.nvd.cli.api.download.ApiDownloadCommand;
import com.github.phanikb.nvd.cli.uri.download.UriDownloadCommand;

@Getter
@CommandLine.Command(name = "download", mixinStandardHelpOptions = true, synopsisSubcommandLabel = "COMMAND",
        subcommands = {ApiDownloadCommand.class, UriDownloadCommand.class, CommandLine.HelpCommand.class},
        description = "Downloads CVE/CPE data from NIST NVD repository and CWE data maintained by MITRE.")
public class DownloadCommand extends BaseCommand implements INvdCommand {
    private static final Logger logger = LogManager.getLogger(DownloadCommand.class);

    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;

    @CommandLine.Option(names = {"--delete-temp-dir"}, description = "Delete temporary directory",
            scope = CommandLine.ScopeType.LOCAL)
    private boolean deleteTempDir;

    @Override
    public Integer call() {
        throw new CommandLine.ParameterException(spec.commandLine(), "missing required subcommand");
    }

    @Override
    public void validateOptions() {
        super.validateOptions();
        logger.info("delete temporary directory: {}", deleteTempDir);
    }
}
