package com.github.phanikb.nvd.cli.processor.pc;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingDeque;

import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.net.URIBuilder;

import lombok.Getter;

import com.github.phanikb.nvd.common.DateFormats;
import com.github.phanikb.nvd.common.HttpUtil;
import com.github.phanikb.nvd.common.NvdApiDate;
import com.github.phanikb.nvd.common.NvdException;
import com.github.phanikb.nvd.common.QueueElement;
import com.github.phanikb.nvd.enums.ApiQueryParams;
import com.github.phanikb.nvd.enums.FeedType;
import com.github.phanikb.nvd.enums.NvdApiDateType;

import static com.github.phanikb.nvd.common.Constants.DEFAULT_MAX_RANGE_IN_DAYS;

@Getter
public abstract class DatesProcessor<T> extends BaseProcessor<T> implements IDatesProcessor {
    protected final List<NvdApiDate> dates;
    protected final Map<String, Integer> totalResultsByDate = new HashMap<>();

    protected DatesProcessor(
            FeedType feedType,
            T poison,
            int poisonPerCreator,
            int maxResultsPerPage,
            String endpoint,
            Path outDir,
            String outFilePrefix,
            List<NvdApiDate> dates,
            BlockingDeque<QueueElement> downloadQueue) {
        super(feedType, poison, poisonPerCreator, maxResultsPerPage, endpoint, outDir, outFilePrefix, downloadQueue);
        if (dates.size() != 2) {
            throw new IllegalArgumentException("dates must have 2 elements");
        }
        this.dates = dates;
    }

    protected DatesProcessor(
            FeedType feedType, T poison, Path outDir, String outFilePrefix, BlockingDeque<QueueElement> downloadQueue) {
        super(feedType, poison, outDir, outFilePrefix, downloadQueue);
        this.dates = new ArrayList<>();
    }

    @Override
    public NvdApiDate getNvdApiStartDate() {
        for (NvdApiDate date : dates) {
            if (date.type().equals(NvdApiDateType.START_DATE)) {
                return date;
            }
        }
        throw new IllegalArgumentException("start date not found");
    }

    @Override
    public NvdApiDate getNvdApiEndDate() {
        for (NvdApiDate date : dates) {
            if (date.type().equals(NvdApiDateType.END_DATE)) {
                return date;
            }
        }
        throw new IllegalArgumentException("end date not found");
    }

    @Override
    public LocalDateTime getStartDate() {
        return getNvdApiStartDate().value();
    }

    @Override
    public LocalDateTime getEndDate() {
        return getNvdApiEndDate().value();
    }

    @Override
    public void validateDateRange(LocalDateTime startDate, LocalDateTime endDate) throws NvdException {
        if (startDate == null || startDate.isAfter(endDate) || startDate.isAfter(LocalDateTime.now())) {
            logger.error("invalid date range");
            throw new NvdException("Invalid date range");
        }

        LocalDateTime adjustedEndDate = endDate;
        if (adjustedEndDate == null || adjustedEndDate.isAfter(LocalDateTime.now())) {
            adjustedEndDate = LocalDateTime.now();
        }

        if (startDate.plusYears(1).isBefore(adjustedEndDate)) {
            logger.warn("date range is more than a year");
        } else {
            if (startDate.plusDays(DEFAULT_MAX_RANGE_IN_DAYS).isBefore(adjustedEndDate)) {
                logger.warn("date range is more than {} days", DEFAULT_MAX_RANGE_IN_DAYS);
            }
        }
    }

    protected File getDownloadFile(
            LocalDateTime startDate, LocalDateTime endDate, int startIndex, int endIndex, Path outDir) {
        String date = DateFormats.DateFormat.OUTPUT_FILE_NAME_SUFFIX.format(LocalDateTime.now());
        String si = String.format("%07d", startIndex);
        String ei = String.format("%07d", endIndex);
        String sd = DateFormats.DateFormat.OUTPUT_FILE_NAME_SUFFIX.format(startDate);
        String ed = DateFormats.DateFormat.OUTPUT_FILE_NAME_SUFFIX.format(endDate);
        return new File(
                outDir.toFile(), outFilePrefix + "-" + sd + "_" + ed + "_" + si + "_" + ei + "-" + date + ".json");
    }

    protected URI getDownloadUri(
            NvdApiDate startDate, NvdApiDate endDate, long startIndex, List<NameValuePair> queryParams)
            throws NvdException {
        try {
            URIBuilder builder = new URIBuilder(endpoint);
            builder.setParameters(queryParams);
            builder.removeParameter(startDate.name());
            builder.removeParameter(endDate.name());
            builder.removeParameter(ApiQueryParams.START_INDEX.getName());
            builder.addParameter(startDate.name(), DateFormats.DateFormat.ISO_DATE_TIME_EXT.format(startDate.value()));
            builder.addParameter(
                    endDate.name(), DateFormats.DateFormat.ISO_DATE_TIME_EXT.format(endDate.value(), true));
            builder.addParameter(ApiQueryParams.START_INDEX.getName(), String.valueOf(startIndex));
            for (NameValuePair param : HttpUtil.getNvdDefaultParams(maxResultsPerPage)) {
                builder.addParameter(param.getName(), param.getValue());
            }
            return builder.build();
        } catch (URISyntaxException e) {
            throw new NvdException("failed to build URI", e);
        }
    }
}
