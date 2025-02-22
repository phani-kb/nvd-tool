package com.github.phanikb.nvd.utils;

import java.util.concurrent.Callable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import picocli.CommandLine;

import com.github.phanikb.nvd.cli.DownloadCommand;
import com.github.phanikb.nvd.cli.MergeCommand;

@CommandLine.Command(
        name = "NVDTool",
        mixinStandardHelpOptions = true,
        scope = CommandLine.ScopeType.INHERIT,
        version = {
            "NVDTool 1.0",
            "JVM: ${java.version} (${java.vendor} ${java.vm.name} ${java.vm.version})",
            "OS: ${os.name} ${os.version} ${os.arch}"
        },
        headerHeading = "@|bold,underline Usage|@:%n",
        synopsisHeading = "%n",
        descriptionHeading = "%n@|bold,underline Description|@:%n%n",
        optionListHeading = "%n@|bold,underline Options|@:%n",
        subcommands = {DownloadCommand.class, MergeCommand.class, CommandLine.HelpCommand.class},
        description = "A tool to retrieve CVE/CPE data from NIST's NVD repository and CWE data maintained by MITRE.",
        footerHeading = "\nThis tool uses data from the NVD API but is not endorsed or certified by the NVD.\n",
        defaultValueProvider = NVDToolDefaultsProvider.class,
        showDefaultValues = true,
        sortOptions = false,
        usageHelpAutoWidth = true)
public class NVDTool implements Callable<Integer> {
    private static final Logger logger = LogManager.getLogger(NVDTool.class);

    public static void main(String... args) {
        NVDTool tool = new NVDTool();
        CommandLine cli = new CommandLine(tool).setExecutionStrategy(tool::executionStrategy);
        tool.handleHelpAndVersionRequests(cli, args);
        int exitCode = cli.execute(args);
        System.exit(exitCode);
    }

    private void handleHelpAndVersionRequests(CommandLine cli, String... args) {
        try {
            cli.parseArgs(args);
        } catch (CommandLine.ParameterException e) {
            logger.error("{}", e.getMessage());
            cli.usage(System.out);
            System.exit(1);
        }
        if (cli.isUsageHelpRequested()) {
            cli.usage(System.out);
            System.exit(0);
        }
        if (cli.isVersionHelpRequested()) {
            cli.printVersionHelp(System.out);
            System.exit(0);
        }
    }

    private int executionStrategy(CommandLine.ParseResult parseResult) {
        return new CommandLine.RunLast().execute(parseResult); // default execution strategy
    }

    @Override
    public Integer call() {
        throw new CommandLine.ParameterException(new CommandLine(this), "Missing required subcommand");
    }
}
