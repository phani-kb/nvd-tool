package com.github.phanikb.nvd.cli.api.download;

import java.util.ArrayList;
import java.util.List;

import org.apache.hc.core5.http.NameValuePair;

import picocli.CommandLine;

import com.github.phanikb.nvd.cli.api.CveHistoryApiOptions;
import com.github.phanikb.nvd.cli.processor.api.download.ApiDownloader;

@CommandLine.Command(
        name = "cve-history",
        mixinStandardHelpOptions = true,
        subcommands = {CommandLine.HelpCommand.class},
        description = "Download CVE history data using the new APIs.")
public class CveHistoryApiDownloadCommand extends BaseApiDownloadCommand {

    @CommandLine.Spec
    private CommandLine.Model.CommandSpec spec;

    @CommandLine.Mixin
    private CveHistoryApiOptions cveHistoryApiOptions;

    @Override
    public void validateOptions() {
        try {
            cveHistoryApiOptions.validateOptions();
            super.validateOptions();
            validateCommandName(spec);
        } catch (Exception e) {
            throw new CommandLine.ParameterException(spec.commandLine(), e.getMessage(), e);
        }
    }

    @Override
    public List<NameValuePair> getQueryParams() {

        return new ArrayList<>();
    }

    @Override
    public ApiDownloader getApiDownloader() {
        return null;
    }
}
