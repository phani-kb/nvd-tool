package com.github.phanikb.nvd.cli.processor.uri;

import java.io.File;
import java.net.URI;
import java.util.List;
import java.util.Set;

import com.github.phanikb.nvd.cli.processor.ICommandProcessor;
import com.github.phanikb.nvd.cli.uri.download.HttpUriDownloadStatus;
import com.github.phanikb.nvd.common.NvdDownloadException;

public interface IUriDownloadCommandProcessor extends ICommandProcessor {
    List<HttpUriDownloadStatus> download();

    HttpUriDownloadStatus download(URI uri, File outputDir, File filename) throws NvdDownloadException;

    int getMaxConcurrentDownloads();

    Set<URI> getUris();

    void close();
}
