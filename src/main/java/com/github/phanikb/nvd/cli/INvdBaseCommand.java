package com.github.phanikb.nvd.cli;

import java.io.File;

public interface INvdBaseCommand {
    void validateOptions();

    File getOutDir();

    String getOutFilename();
}
