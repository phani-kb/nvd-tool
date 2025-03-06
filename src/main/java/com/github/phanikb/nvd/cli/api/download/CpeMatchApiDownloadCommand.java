package com.github.phanikb.nvd.cli.api.download;

import java.util.ArrayList;
import java.util.List;

import org.apache.hc.core5.http.NameValuePair;

import picocli.CommandLine;

import com.github.phanikb.nvd.cli.api.CpeMatchApiOptions;
import com.github.phanikb.nvd.cli.processor.api.download.ApiDownloader;
import com.github.phanikb.nvd.enums.FeedType;

@CommandLine.Command(
        name = "cpe-match",
        mixinStandardHelpOptions = true,
        subcommands = {CommandLine.HelpCommand.class},
        description = "Download CPE match data using the new APIs.")
public class CpeMatchApiDownloadCommand extends BaseApiDownloadCommand {
    @CommandLine.Spec
    private CommandLine.Model.CommandSpec spec;

    @CommandLine.Mixin
    private CpeMatchApiOptions cpeMatchApiOptions;

    @Override
    public void validateOptions() {
        try {
            cpeMatchApiOptions.validateOptions();
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
        return super.getApiDownloader(FeedType.CPE_MATCH, getDates(cpeMatchApiOptions.getLastModDateRange()), spec);
    }
}
