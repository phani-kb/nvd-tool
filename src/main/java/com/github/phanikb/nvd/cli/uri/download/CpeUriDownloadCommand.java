package com.github.phanikb.nvd.cli.uri.download;

import picocli.CommandLine;

import com.github.phanikb.nvd.common.Constants;
import com.github.phanikb.nvd.common.NvdProperties;
import com.github.phanikb.nvd.enums.FeedType;

@CommandLine.Command(
        name = "cpe",
        mixinStandardHelpOptions = true,
        subcommands = {CommandLine.HelpCommand.class},
        description = "Download CPE data using traditional URLs.")
public class CpeUriDownloadCommand extends BaseUriDownloadCommand {

    @Override
    public String[] getUrls() {
        NvdProperties nvdProperties = NvdProperties.getInstance();
        String[] urls = new String[1];
        urls[0] = nvdProperties.getNvd().getCpe().getMainUrl();
        urls[0] = urls[0].replace(
                Constants.CVE_URL_ARCHIVE_TYPE, getArchiveType().toString().toLowerCase());
        return urls;
    }

    @Override
    public FeedType getFeedType() {
        return FeedType.CPE;
    }
}
