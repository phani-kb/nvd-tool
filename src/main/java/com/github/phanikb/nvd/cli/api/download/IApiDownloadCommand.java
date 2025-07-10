package com.github.phanikb.nvd.cli.api.download;

import java.io.File;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.hc.core5.http.NameValuePair;

import picocli.CommandLine;

import com.github.phanikb.nvd.cli.INvdCommand;
import com.github.phanikb.nvd.cli.processor.api.download.ApiDownloader;
import com.github.phanikb.nvd.common.NvdException;

public interface IApiDownloadCommand extends INvdCommand {
    Integer getStartIndex();

    int getMaxResultsPerPage();

    Integer execute(ApiDownloader downloader) throws NvdException;

    void validateCommandName(CommandLine.Model.CommandSpec spec);

    String getApiEndpoint(CommandLine.Model.CommandSpec spec);

    @Override
    File getOutDir();

    @Override
    boolean isDeleteTempDir();

    @Override
    String getOutFilename();

    boolean isCompress();

    List<NameValuePair> getQueryParams();

    ApiDownloader getApiDownloader();

    CountDownLatch getLatch();
}
