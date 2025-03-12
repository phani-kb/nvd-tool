package com.github.phanikb.nvd.cli.api.download;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;

import picocli.CommandLine;

import com.github.phanikb.nvd.cli.api.CveApiOptions;
import com.github.phanikb.nvd.cli.processor.api.download.ApiDownloader;
import com.github.phanikb.nvd.common.Constants;
import com.github.phanikb.nvd.common.NvdApiDate;
import com.github.phanikb.nvd.enums.ApiQueryParams;
import com.github.phanikb.nvd.enums.NvdApiDateType;

@CommandLine.Command(
        name = "cve",
        mixinStandardHelpOptions = true,
        subcommands = {CommandLine.HelpCommand.class},
        description = "Download CVE data using the new APIs.")
public class CveApiDownloadCommand extends BaseApiDownloadCommand {

    @CommandLine.Spec
    private CommandLine.Model.CommandSpec spec;

    @CommandLine.Mixin
    private CveApiOptions cveApiOptions;

    @Override
    public Integer call() throws Exception {
        validateOptions();
        if (cveApiOptions.getLastModDateRange() != null) {
            logger.debug("last mod date range = {}", cveApiOptions.getLastModDateRange());
        }
        if (cveApiOptions.getPubDateRange() != null) {
            logger.info("pub date range = {}", cveApiOptions.getPubDateRange());
        }

        boolean validRange = false;
        List<NvdApiDate> dates = getDatesIfSingleDateRangeGiven();
        if (!dates.isEmpty()) {
            validRange = true;
        } else if (areDateRangesWithinAllowableRange()) {
            dates = null;
            validRange = true;
        } else {
            dates = getDatesIfOnlyOneDateRangeIsOutsideAllowableRange();
            if (!dates.isEmpty()) {
                validRange = true;
            }
        }

        if (!validRange) {
            throw new IllegalArgumentException(
                    "Date ranges are outside the allowable range of " + Constants.DEFAULT_MAX_RANGE_IN_DAYS + " days.");
        }

        return 0;
    }

    private List<NvdApiDate> getDatesIfOnlyOneDateRangeIsOutsideAllowableRange() {
        List<NvdApiDate> dates = new ArrayList<>();
        if (isLMDRWithAllowableRange() && !isPDRWithAllowableRange()) {
            dates.add(new NvdApiDate(
                    ApiQueryParams.PUB_START_DATE.getName(),
                    cveApiOptions.getPubDateRange().getPubStartDate(),
                    NvdApiDateType.START_DATE));
            dates.add(new NvdApiDate(
                    ApiQueryParams.PUB_END_DATE.getName(),
                    cveApiOptions.getPubDateRange().getPubEndDate(),
                    NvdApiDateType.END_DATE));
        } else if (!isLMDRWithAllowableRange() && isPDRWithAllowableRange()) {
            dates.add(new NvdApiDate(
                    ApiQueryParams.LAST_MODIFIED_START_DATE.getName(),
                    cveApiOptions.getLastModDateRange().getLastModStartDate(),
                    NvdApiDateType.START_DATE));
            dates.add(new NvdApiDate(
                    ApiQueryParams.LAST_MODIFIED_END_DATE.getName(),
                    cveApiOptions.getLastModDateRange().getLastModEndDate(),
                    NvdApiDateType.END_DATE));
        }
        return dates;
    }

    private boolean isPDRWithAllowableRange() {
        return cveApiOptions.getPubDateRange() != null
                && cveApiOptions
                        .getPubDateRange()
                        .getPubEndDate()
                        .isBefore(cveApiOptions
                                .getPubDateRange()
                                .getPubStartDate()
                                .plusDays(Constants.DEFAULT_MAX_RANGE_IN_DAYS));
    }

    private boolean isLMDRWithAllowableRange() {
        return cveApiOptions.getLastModDateRange() != null
                && cveApiOptions
                        .getLastModDateRange()
                        .getLastModEndDate()
                        .isBefore(cveApiOptions
                                .getLastModDateRange()
                                .getLastModStartDate()
                                .plusDays(Constants.DEFAULT_MAX_RANGE_IN_DAYS));
    }

    private boolean areDateRangesWithinAllowableRange() {
        if (cveApiOptions.getLastModDateRange() != null) {
            LocalDateTime lmsd = cveApiOptions.getLastModDateRange().getLastModStartDate();
            LocalDateTime lmed = cveApiOptions.getLastModDateRange().getLastModEndDate();
            if (lmed.isAfter(lmsd.plusDays(Constants.DEFAULT_MAX_RANGE_IN_DAYS))) {
                return false;
            }
        }

        if (cveApiOptions.getPubDateRange() != null) {
            LocalDateTime psd = cveApiOptions.getPubDateRange().getPubStartDate();
            LocalDateTime ped = cveApiOptions.getPubDateRange().getPubEndDate();
            return !ped.isAfter(psd.plusDays(Constants.DEFAULT_MAX_RANGE_IN_DAYS));
        }

        return true;
    }

    private List<NvdApiDate> getDatesIfSingleDateRangeGiven() {
        List<NvdApiDate> dates = new ArrayList<>();
        if (cveApiOptions.getLastModDateRange() != null && cveApiOptions.getPubDateRange() == null) {
            dates.add(new NvdApiDate(
                    ApiQueryParams.LAST_MODIFIED_START_DATE.getName(),
                    cveApiOptions.getLastModDateRange().getLastModStartDate(),
                    NvdApiDateType.START_DATE));
            dates.add(new NvdApiDate(
                    ApiQueryParams.LAST_MODIFIED_END_DATE.getName(),
                    cveApiOptions.getLastModDateRange().getLastModEndDate(),
                    NvdApiDateType.END_DATE));
        }

        if (cveApiOptions.getPubDateRange() != null && cveApiOptions.getLastModDateRange() == null) {
            dates.add(new NvdApiDate(
                    ApiQueryParams.PUB_START_DATE.getName(),
                    cveApiOptions.getPubDateRange().getPubStartDate(),
                    NvdApiDateType.START_DATE));
            dates.add(new NvdApiDate(
                    ApiQueryParams.PUB_END_DATE.getName(),
                    cveApiOptions.getPubDateRange().getPubEndDate(),
                    NvdApiDateType.END_DATE));
        }
        return dates;
    }

    @Override
    public void validateOptions() {
        try {
            cveApiOptions.validateOptions();
            super.validateOptions();
            validateCommandName(spec);
        } catch (Exception e) {
            throw new CommandLine.ParameterException(spec.commandLine(), e.getMessage(), e);
        }
    }

    @Override
    public List<NameValuePair> getQueryParams() {
        List<NameValuePair> queryParams = new ArrayList<>(super.getQueryParams());

        if (cveApiOptions == null) {
            return queryParams;
        }

        if (cveApiOptions.getCveId() != null) {
            queryParams.add(new BasicNameValuePair(
                    ApiQueryParams.CVE_ID.getName(), cveApiOptions.getCveId().getId()));
        }

        if (cveApiOptions.getCveTag() != null) {
            queryParams.add(new BasicNameValuePair(
                    ApiQueryParams.CVE_TAG.getName(), cveApiOptions.getCveTag().getValue()));
        }

        if (cveApiOptions.getCweId() != null) {
            queryParams.add(new BasicNameValuePair(
                    ApiQueryParams.CWE_ID.getName(), cveApiOptions.getCweId().getId()));
        }

        if (cveApiOptions.getVirtualMatchString() != null) {
            queryParams.add(new BasicNameValuePair(
                    ApiQueryParams.VIRTUAL_MATCH_STRING.getName(), cveApiOptions.getVirtualMatchString()));
        }

        if (cveApiOptions.getSourceIdentifier() != null) {
            queryParams.add(new BasicNameValuePair(
                    ApiQueryParams.SOURCE_IDENTIFIER.getName(), cveApiOptions.getSourceIdentifier()));
        }

        CveApiOptions.CpeVulnerable cpeVulnerable = cveApiOptions.getCpeVulnerable();
        if (cpeVulnerable != null) {
            if (cpeVulnerable.getCpeName() != null) {
                queryParams.add(new BasicNameValuePair(ApiQueryParams.CPE_NAME.getName(), cpeVulnerable.getCpeName()));
            }
            if (cpeVulnerable.isVulnerable()) {
                queryParams.add(new BasicNameValuePair(ApiQueryParams.IS_VULNERABLE.getName(), null));
            }
        }

        CveApiOptions.KeywordSearch kwSearch = cveApiOptions.getKeywordSearch();
        if (kwSearch != null) {
            if (kwSearch.isKeywordExactMatch()) {
                queryParams.add(new BasicNameValuePair(ApiQueryParams.KW_EXACT_MATCH.getName(), null));
            }
            if (kwSearch.getKeywordSearch() != null) {
                queryParams.add(
                        new BasicNameValuePair(ApiQueryParams.KW_SEARCH.getName(), kwSearch.getKeywordSearch()));
            }
        }

        return queryParams;
    }

    @Override
    public ApiDownloader getApiDownloader() {
        throw new UnsupportedOperationException("This method should not be called.");
    }
}
