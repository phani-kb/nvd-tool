package com.github.phanikb.nvd.cli.processor;

import java.io.File;

import com.github.phanikb.nvd.common.NvdException;
import com.github.phanikb.nvd.enums.FeedType;

public interface ICommandProcessor {
    void preProcess() throws NvdException;

    void process();

    void postProcess() throws NvdException;

    FeedType getFeedType();

    File getOutDir();
}
