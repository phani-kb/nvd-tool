package com.github.phanikb.nvd.cli;

import java.io.File;

import com.github.phanikb.nvd.enums.FeedType;

public interface INvdMergeCommand extends INvdBaseCommand {
    File getInputDir();

    FeedType getFeedType();
}
