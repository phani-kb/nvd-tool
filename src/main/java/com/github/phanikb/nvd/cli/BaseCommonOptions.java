package com.github.phanikb.nvd.cli;

import java.io.File;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import picocli.CommandLine;

/**
 * Base class for common options used in various commands. This class provides options for specifying output directory
 * and file.
 */
@Getter
@Setter
@ToString
public class BaseCommonOptions {
    @CommandLine.Option(
            names = {"-o", "--output-dir"},
            description = "Output directory",
            paramLabel = "DIR",
            scope = CommandLine.ScopeType.LOCAL)
    private File outDir;

    @CommandLine.Option(
            names = {"--output-file"},
            description = "Output file",
            paramLabel = "FILE",
            scope = CommandLine.ScopeType.LOCAL)
    private String outFilename;
}
