package com.github.phanikb.nvd.cli;

import java.io.File;

import lombok.Getter;
import lombok.ToString;
import picocli.CommandLine;

@Getter
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
