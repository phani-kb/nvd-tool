package com.github.phanikb.nvd.cli.uri.download;

import java.util.Arrays;

import picocli.CommandLine;

import com.github.phanikb.nvd.common.Constants;
import com.github.phanikb.nvd.common.NvdProperties;
import com.github.phanikb.nvd.enums.CveDownloadType;
import com.github.phanikb.nvd.enums.FeedType;

import static com.github.phanikb.nvd.common.DateFormats.CURRENT_YEAR;
import static com.github.phanikb.nvd.common.DateFormats.END_YEAR;
import static com.github.phanikb.nvd.common.DateFormats.START_YEAR;

@CommandLine.Command(
        name = "cve",
        mixinStandardHelpOptions = true,
        subcommands = {CommandLine.HelpCommand.class},
        description = "Download CVE data using traditional URLs.")
public class CveUriDownloadCommand extends BaseUriDownloadCommand {
    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;

    @CommandLine.Option(
            names = {"-y", "--years"},
            split = ",",
            description =
                    "Year(s) of the data feed, comma-separated (with no space). By default, all years starting from 2002 to 2024 year are downloaded.",
            paramLabel = "YEAR")
    private Integer[] years;

    @CommandLine.Option(
            names = {"-t", "--download-type"},
            type = CveDownloadType.class,
            description = "Valid values: ${COMPLETION-CANDIDATES}. Default: ${DEFAULT-VALUE}",
            paramLabel = "TYPE",
            defaultValue = "FULL")
    private CveDownloadType downloadType;

    @Override
    public void validateOptions() {
        String errorMsg = "error validating options: ";
        try {
            super.validateOptions();
        } catch (CommandLine.ParameterException e) {
            logger.error("{}{}", errorMsg, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("{}{}", errorMsg, e.getMessage());
            throw new CommandLine.ParameterException(spec.commandLine(), e.getMessage(), e);
        }
        validateYearsOption();
        logger.info("download type = {}", downloadType);
        logger.info("years to download = {}", years == null ? "all years" : Arrays.toString(years));
    }

    private void validateYearsOption() {
        if (years != null) {
            int minYear = Math.min(CURRENT_YEAR, END_YEAR);
            for (Integer year : years) {
                if (year < START_YEAR || year > minYear) {
                    throw new CommandLine.ParameterException(
                            spec.commandLine(), "Year should be in range " + START_YEAR + " to " + minYear);
                }
            }
            // remove duplicate years
            years = Arrays.stream(years).distinct().toArray(Integer[]::new);
        } else {
            // if years not specified, download all years
            years = new Integer[CURRENT_YEAR - START_YEAR + 1];
            for (int i = 0; i < years.length; i++) {
                years[i] = START_YEAR + i;
            }
        }
    }

    @Override
    public String[] getUrls() {
        NvdProperties nvdProperties = NvdProperties.getInstance();
        String url = nvdProperties.getNvd().getCve().getMainUrl();
        url = url.replace(
                Constants.CVE_URL_ARCHIVE_TYPE,
                parent.getArchiveType().toString().toLowerCase());
        switch (downloadType) {
            case FULL -> {
                String[] urls = new String[years.length];
                for (int i = 0; i < years.length; i++) {
                    urls[i] = url.replace(Constants.CVE_URL_DOWNLOAD_TYPE, years[i].toString());
                }
                return urls;
            }
            case MODIFIED, RECENT -> {
                String[] urls = new String[1];
                urls[0] = url.replace(
                        Constants.CVE_URL_DOWNLOAD_TYPE, downloadType.toString().toLowerCase());
                return urls;
            }
            default -> throw new IllegalArgumentException("invalid download type: " + downloadType);
        }
    }

    @Override
    public FeedType getFeedType() {
        return FeedType.CVE;
    }
}
