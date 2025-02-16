package com.github.phanikb.nvd.cli.processor;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.github.phanikb.nvd.common.NvdDownloadException;
import com.github.phanikb.nvd.common.NvdException;
import com.github.phanikb.nvd.enums.DownloadMode;
import com.github.phanikb.nvd.enums.FeedType;

@Getter
@RequiredArgsConstructor
public abstract class CommandProcessor implements ICommandProcessor {
    protected static final Logger logger = LogManager.getLogger(CommandProcessor.class);
    private final DownloadMode dwnMode;
    private final FeedType feedType;
    private final File outDir;

    @Override
    public void preProcess() throws NvdException {
        logger.debug("preProcess");
        if (outDir == null) throw new NvdDownloadException("outputDir is null");
        if (!outDir.exists()) throw new NvdDownloadException("outputDir " + outDir + " does not exist");
        if (!outDir.isDirectory()) throw new NvdDownloadException("outputDir " + outDir + " is not a directory");
    }

    @Override
    public void postProcess() throws NvdException {}
}
