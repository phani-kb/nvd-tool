package com.github.phanikb.nvd.cli.processor;

import java.io.File;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.github.phanikb.nvd.enums.FeedType;

@Getter
@RequiredArgsConstructor
public abstract class NvdDownloader implements INvdDownloader {
    private final FeedType feedType;
    private final File outDir;
    private final int maxResultsPerPage;
    private final String outFile;
    private final boolean deleteTempDir;
    private final boolean compress;
}
