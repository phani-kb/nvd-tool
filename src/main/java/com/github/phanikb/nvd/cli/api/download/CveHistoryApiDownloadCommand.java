package com.github.phanikb.nvd.cli.api.download;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;

import picocli.CommandLine;

import com.github.phanikb.nvd.cli.api.CveHistoryApiOptions;
import com.github.phanikb.nvd.cli.processor.api.download.ApiDownloader;
import com.github.phanikb.nvd.common.NvdApiDate;
import com.github.phanikb.nvd.enums.ApiQueryParams;
import com.github.phanikb.nvd.enums.CveHistoryEventName;
import com.github.phanikb.nvd.enums.FeedType;
import com.github.phanikb.nvd.enums.NvdApiDateType;

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
        List<NameValuePair> queryParams = new ArrayList<>(super.getQueryParams());

        if (cveHistoryApiOptions == null) {
            return queryParams;
        }

        String cveId = cveHistoryApiOptions.getCveId();
        if (StringUtils.isNotBlank(cveId)) {
            queryParams.add(new BasicNameValuePair("cveId", cveId));
        }

        CveHistoryApiOptions.ChangeDateRange changeDateRange = cveHistoryApiOptions.getChangeDateRange();
        if (changeDateRange != null) {
            queryParams.addAll(getDateRangeQueryParams(
                    ApiQueryParams.CHANGE_START_DATE,
                    changeDateRange.getChangeStartDate(),
                    ApiQueryParams.CHANGE_END_DATE,
                    changeDateRange.getChangeEndDate()));
        }

        CveHistoryEventName eventName = cveHistoryApiOptions.getEventName();
        if (eventName != null) {
            queryParams.add(new BasicNameValuePair(ApiQueryParams.EVENT_NAME.getName(), eventName.getValue()));
        }

        return queryParams;
    }

    @Override
    public ApiDownloader getApiDownloader() {
        List<NvdApiDate> dates = new ArrayList<>();
        if (cveHistoryApiOptions.getChangeDateRange() != null) {
            dates.add(new NvdApiDate(
                    ApiQueryParams.CHANGE_START_DATE.getName(),
                    cveHistoryApiOptions.getChangeDateRange().getChangeStartDate(),
                    NvdApiDateType.START_DATE));
            dates.add(new NvdApiDate(
                    ApiQueryParams.CHANGE_END_DATE.getName(),
                    cveHistoryApiOptions.getChangeDateRange().getChangeEndDate(),
                    NvdApiDateType.END_DATE));
        }
        return super.getApiDownloader(FeedType.CVE_HISTORY, dates, spec);
    }
}
