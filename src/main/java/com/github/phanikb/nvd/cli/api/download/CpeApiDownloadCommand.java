package com.github.phanikb.nvd.cli.api.download;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;

import picocli.CommandLine;

import com.github.phanikb.nvd.cli.api.CpeApiOptions;
import com.github.phanikb.nvd.cli.api.LastModApiOptions.LastModDateRange;
import com.github.phanikb.nvd.cli.processor.api.download.ApiDownloader;
import com.github.phanikb.nvd.common.CpeName;
import com.github.phanikb.nvd.enums.ApiQueryParams;
import com.github.phanikb.nvd.enums.FeedType;

@CommandLine.Command(
        name = "cpe",
        mixinStandardHelpOptions = true,
        subcommands = {CommandLine.HelpCommand.class},
        description = "Download CPE data using the new APIs.")
public class CpeApiDownloadCommand extends BaseApiDownloadCommand {

    @CommandLine.Spec
    private CommandLine.Model.CommandSpec spec;

    @CommandLine.Mixin
    private CpeApiOptions cpeApiOptions;

    @Override
    public void validateOptions() {
        try {
            cpeApiOptions.validateOptions();
            super.validateOptions();
            validateCommandName(spec);
        } catch (Exception e) {
            throw new CommandLine.ParameterException(spec.commandLine(), e.getMessage(), e);
        }
    }

    @Override
    public List<NameValuePair> getQueryParams() {
        List<NameValuePair> queryParams = new ArrayList<>(super.getQueryParams());

        if (cpeApiOptions == null) {
            return queryParams;
        }

        CpeName cpeName = cpeApiOptions.getCpeMatchString();
        if (cpeName != null) {
            queryParams.add(new BasicNameValuePair(ApiQueryParams.CPE_MATCH_STRING.getName(), cpeName.getName()));
        }

        if (cpeApiOptions.getCpeNameId() != null) {
            queryParams.add(new BasicNameValuePair(
                    ApiQueryParams.CPE_NAME_ID.getName(),
                    cpeApiOptions.getCpeNameId().toString()));
        }

        CpeApiOptions.KeywordSearch kwSearch = cpeApiOptions.getKeywordSearch();
        if (kwSearch != null) {
            if (kwSearch.isKeywordExactMatch()) {
                queryParams.add(new BasicNameValuePair(ApiQueryParams.KW_EXACT_MATCH.getName(), null));
            }
            if (kwSearch.getKws() != null) {
                queryParams.add(new BasicNameValuePair(ApiQueryParams.KW_SEARCH.getName(), kwSearch.getKws()));
            }
        }

        LastModDateRange lastModDateRange = cpeApiOptions.getLastModDateRange();
        if (lastModDateRange != null) {
            queryParams.addAll(getDateRangeQueryParams(
                    ApiQueryParams.LAST_MODIFIED_START_DATE,
                    lastModDateRange.getLastModStartDate(),
                    ApiQueryParams.LAST_MODIFIED_END_DATE,
                    lastModDateRange.getLastModEndDate()));
        }

        UUID matchCriteriaId = cpeApiOptions.getMatchCriteriaId();
        if (matchCriteriaId != null) {
            queryParams.add(
                    new BasicNameValuePair(ApiQueryParams.MATCH_CRITERIA_ID.getName(), matchCriteriaId.toString()));
        }

        return queryParams;
    }

    @Override
    public ApiDownloader getApiDownloader() {
        return super.getApiDownloader(FeedType.CPE, getDates(cpeApiOptions.getLastModDateRange()), spec);
    }
}
