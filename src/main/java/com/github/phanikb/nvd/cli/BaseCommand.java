package com.github.phanikb.nvd.cli;

import java.io.File;
import java.util.concurrent.Callable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lombok.Getter;
import picocli.CommandLine;

import com.github.phanikb.nvd.common.Constants;
import com.github.phanikb.nvd.common.Util;

@Getter
public abstract class BaseCommand implements Callable<Integer>, INvdBaseCommand {
    private static final Logger logger = LogManager.getLogger(BaseCommand.class);

    @CommandLine.Spec
    private CommandLine.Model.CommandSpec spec;

    @CommandLine.Mixin
    private BaseCommonOptions baseCommonOptions;

    @Override
    public Integer call() {
        validateOptions();
        return 0;
    }

    @Override
    public void validateOptions() {
        File outDir = baseCommonOptions.getOutDir();
        validateOutDirectory(outDir);
        validateOutputFile(baseCommonOptions.getOutFilename());
        checkFreeSpace(outDir);
    }

    protected void validateDirectory(File dir) {
        if (dir != null) {
            if (!dir.exists()) {
                throw new CommandLine.ParameterException(
                        spec.commandLine(), dir.getName() + " directory does not exist");
            }
            if (!dir.isDirectory()) {
                throw new CommandLine.ParameterException(
                        spec.commandLine(), dir.getName() + " directory is not a directory");
            }
        }
    }

    private void validateOutDirectory(File outDir) {
        validateDirectory(outDir);
        if (!outDir.canWrite()) {
            throw new CommandLine.ParameterException(
                    spec.commandLine(), outDir.getName() + " directory is not writable");
        }
        logger.info("output directory: {}", outDir);
    }

    private void validateOutputFile(String filename) {
        if (filename != null) {
            if (filename.contains(File.separator)) {
                throw new CommandLine.ParameterException(
                        spec.commandLine(), "output file name contains path separator");
            }
            logger.info("output file: {}", filename);
        }
    }

    private void checkFreeSpace(File outDir) {
        int freeSpace = Util.getUsableSpace(outDir);
        if (freeSpace < Constants.MIN_FREE_SPACE_IN_GB) {
            logger.warn("low disk space detected. Free space: {} GB", freeSpace);
        }
        logger.debug("free space: {} GB", freeSpace);
    }

    @Override
    public File getOutDir() {
        return baseCommonOptions.getOutDir();
    }

    @Override
    public String getOutFilename() {
        return baseCommonOptions.getOutFilename();
    }
}
