package com.github.phanikb.nvd.cli.processor.api;

import java.util.concurrent.ExecutorService;

import com.github.phanikb.nvd.common.NvdException;

public interface IApiDownloadUriProducer {
    void generateUris(ExecutorService executorService, int numberOfProducers);

    int calculateTotalResults() throws NvdException;

    int getTotalPages() throws NvdException;

    int getTotalResults() throws NvdException;

    int getTotalFiles() throws NvdException;

    void setMaxResultsPerPage(int maxResultsPerPage);
}
