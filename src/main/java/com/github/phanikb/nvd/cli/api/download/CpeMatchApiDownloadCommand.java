package com.github.phanikb.nvd.cli.api.download;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;

import picocli.CommandLine;

import com.github.phanikb.nvd.cli.api.CpeMatchApiOptions;
import com.github.phanikb.nvd.cli.processor.api.download.ApiDownloader;
import com.github.phanikb.nvd.common.CpeName;
import com.github.phanikb.nvd.enums.ApiQueryParams;
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
        List<NameValuePair> queryParams = new ArrayList<>(super.getQueryParams());

        if (cpeMatchApiOptions == null) {
            return queryParams;
        }

        CpeName cpeName = cpeMatchApiOptions.getMatchStringSearch();
        if (cpeName != null) {
            queryParams.add(new BasicNameValuePair(ApiQueryParams.MATCH_STRING_SEARCH.getName(), cpeName.getName()));
        }

        String cveId = cpeMatchApiOptions.getCveId();
        if (StringUtils.isNotBlank(cveId)) {
            queryParams.add(new BasicNameValuePair(ApiQueryParams.CVE_ID.getName(), cveId));
        }

        UUID matchCriteriaId = cpeMatchApiOptions.getMatchCriteriaId();
        if (matchCriteriaId != null) {
            queryParams.add(
                    new BasicNameValuePair(ApiQueryParams.MATCH_CRITERIA_ID.getName(), matchCriteriaId.toString()));
        }

        CpeMatchApiOptions.LastModDateRange lastModDateRange = cpeMatchApiOptions.getLastModDateRange();
        if (lastModDateRange != null) {
            queryParams.addAll(getDateRangeQueryParams(
                    ApiQueryParams.LAST_MODIFIED_START_DATE,
                    lastModDateRange.getLastModStartDate(),
                    ApiQueryParams.LAST_MODIFIED_END_DATE,
                    lastModDateRange.getLastModEndDate()));
        }

        return queryParams;
    }

    @Override
    public ApiDownloader getApiDownloader() {
        return super.getApiDownloader(FeedType.CPE_MATCH, getDates(cpeMatchApiOptions.getLastModDateRange()), spec);
    }
}
