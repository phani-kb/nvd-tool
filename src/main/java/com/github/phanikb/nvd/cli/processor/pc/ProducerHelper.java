package com.github.phanikb.nvd.cli.processor.pc;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.hc.core5.http.NameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lombok.Getter;

import com.github.phanikb.nvd.common.NvdException;
import com.github.phanikb.nvd.enums.ApiQueryParams;
import com.github.phanikb.nvd.enums.FeedType;

@Getter
public class ProducerHelper {
    private static final Logger logger = LogManager.getLogger(ProducerHelper.class);
    private final FeedType feedType;
    private final CalculateTotalResultsFunction calculateTotalResultsFunction;
    private final List<NameValuePair> queryParams;
    private final Map<String, Integer> totalResultsByDate = new HashMap<>();
    private Integer totalResults;
    private Integer totalPages;

    public ProducerHelper(
            FeedType feedType,
            CalculateTotalResultsFunction calculateTotalResultsFunction,
            List<NameValuePair> queryParams) {
        this.feedType = feedType;
        this.calculateTotalResultsFunction = calculateTotalResultsFunction;
        this.queryParams = queryParams;
        validateQueryParams();
    }

    private void validateQueryParams() {
        List<String> names = ApiQueryParams.getQueryParams(feedType);
        for (NameValuePair param : queryParams) {
            String name = param.getName();
            if (!names.contains(name)) {
                throw new IllegalArgumentException("invalid query param: " + name + " for feed type: " + feedType);
            } else {
                logger.info("query param: {} = {}", name, param.getValue());
            }
        }
    }

    public void initResults(int maxResultsPerPage) throws NvdException {
        initTotalPages(maxResultsPerPage);
    }

    private void initTotalResults() throws NvdException {
        totalResults = calculateTotalResultsFunction.calculateTotalResults();
        logger.info("total results: {}", totalResults);
    }

    private void initTotalPages(int maxResultsPerPage) throws NvdException {
        totalPages = (int) Math.ceil((double) getTotalResults() / maxResultsPerPage);
        logger.info("total pages: {}", totalPages);
        logger.info("total files: {}", getTotalFiles(maxResultsPerPage));
    }

    public int getTotalResults() throws NvdException {
        if (totalResults == null) {
            initTotalResults();
        }
        return totalResults;
    }

    public int getTotalPages(int maxResultsPerPage) throws NvdException {
        if (totalPages == null) {
            initTotalPages(maxResultsPerPage);
        }
        return totalPages;
    }

    public int getTotalResultsByDate(LocalDateTime start, LocalDateTime end) {
        return totalResultsByDate.get(start + ":" + end);
    }

    public void addTotalResultsByDate(LocalDateTime start, LocalDateTime end, int totalResults) {
        totalResultsByDate.put(start + ":" + end, totalResults);
    }

    public boolean hasStartIndex() {
        return queryParams.stream().anyMatch(p -> p.getName().equals(ApiQueryParams.START_INDEX.getName()));
    }

    public int getStartIndex() {
        return queryParams.stream()
                .filter(p -> p.getName().equals(ApiQueryParams.START_INDEX.getName()))
                .findFirst()
                .map(NameValuePair::getValue)
                .map(Integer::parseInt)
                .orElseThrow(() ->
                        new IllegalArgumentException(ApiQueryParams.START_INDEX.getName() + " parameter is missing"));
    }

    public int getTotalFiles(int maxResultsPerPage) {
        AtomicInteger files = new AtomicInteger();
        totalResultsByDate
                .values()
                .forEach(total -> files.addAndGet((int) Math.ceil((double) total / maxResultsPerPage)));

        return files.get();
    }
}
