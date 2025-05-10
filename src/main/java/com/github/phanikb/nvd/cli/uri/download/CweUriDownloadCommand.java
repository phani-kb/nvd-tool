package com.github.phanikb.nvd.cli.uri.download;

import picocli.CommandLine;

import com.github.phanikb.nvd.common.NvdProperties;
import com.github.phanikb.nvd.enums.FeedType;

@CommandLine.Command(
        name = "cwe",
        mixinStandardHelpOptions = true,
        subcommands = {CommandLine.HelpCommand.class},
        description = "Download CWE data.")
public class CweUriDownloadCommand extends BaseUriDownloadCommand {

    @Override
    public String[] getUrls() {
        NvdProperties nvdProperties = NvdProperties.getInstance();
        String[] urls = new String[1];
        urls[0] = nvdProperties.getNvd().getCwe().getUrl();
        return urls;
    }

    @Override
    public FeedType getFeedType() {
        return FeedType.CWE;
    }
}
