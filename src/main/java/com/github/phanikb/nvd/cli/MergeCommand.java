package com.github.phanikb.nvd.cli;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lombok.Getter;
import picocli.CommandLine;

import com.github.phanikb.nvd.common.Constants;
import com.github.phanikb.nvd.common.NvdException;
import com.github.phanikb.nvd.common.Util;
import com.github.phanikb.nvd.enums.CommandApiEndpointType;
import com.github.phanikb.nvd.enums.FeedType;

@Getter
@CommandLine.Command(
        name = "merge",
        mixinStandardHelpOptions = true,
        synopsisSubcommandLabel = "COMMAND",
        description = "Merges multiple NVD JSON files into one.")
public class MergeCommand extends BaseCommand implements INvdMergeCommand {
    private static final Logger logger = LogManager.getLogger(MergeCommand.class);

    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;

    @CommandLine.Option(
            names = {"-i", "--input-dir"},
            description = "Input directory",
            paramLabel = "DIR",
            required = true,
            scope = CommandLine.ScopeType.LOCAL)
    private File inputDir;

    @CommandLine.Option(
            names = {"-t", "--feed-type"},
            description = "API Feed type. Valid values: ${COMPLETION-CANDIDATES}",
            paramLabel = "TYPE",
            type = CommandApiEndpointType.class,
            required = true,
            scope = CommandLine.ScopeType.LOCAL)
    private CommandApiEndpointType type;

    @Override
    public Integer call() {
        File srcDir = getInputDir();
        File destDir = getOutDir();
        String outFilename = getOutFilename();
        validateOptions();
        try {
            File[] files = srcDir.listFiles((dir, name) -> name.endsWith(".json"));
            if (files == null || files.length == 0) {
                throw new NvdException("No JSON files found in " + srcDir.getAbsolutePath());
            }
            logger.info("found {} files in {}", files.length, srcDir.getAbsolutePath());
            File outFile = new File(destDir, outFilename);
            int actualCount = Util.mergeFiles(files, outFile, type.getFeedType().getCollectionNodeName());
            logger.info("number of records: {}", actualCount);
        } catch (NvdException e) {
            throw new CommandLine.ParameterException(spec.commandLine(), e.getMessage());
        }
        return 0;
    }

    @Override
    public void validateOptions() {
        super.validateOptions();
        validateInputDir(inputDir);
        validateFeedType(type);
    }

    private void validateInputDir(File inputDir) {
        super.validateDirectory(inputDir);
        if (!inputDir.canRead()) {
            throw new CommandLine.ParameterException(
                    spec.commandLine(), inputDir.getName() + " directory is not readable");
        }
        logger.info("input directory: {}", inputDir);
    }

    private void validateFeedType(CommandApiEndpointType type) {
        if (type == null) {
            throw new CommandLine.ParameterException(spec.commandLine(), "feed type is not specified");
        }
        logger.info(
                "feed type: {} collection node: {}",
                type.getFeedType(),
                type.getFeedType().getCollectionNodeName());
    }

    @Override
    public FeedType getFeedType() {
        return type.getFeedType();
    }

    @Override
    public File getOutDir() {
        File outDir = super.getOutDir();
        if (outDir == null) {
            return new File(System.getProperty("user.dir"));
        }
        return outDir;
    }

    @Override
    public String getOutFilename() {
        if (super.getOutFilename() != null) {
            return super.getOutFilename();
        }
        return Constants.OUT_FILE_PREFIX + type.getFeedType().getName() + "-"
                + type.getFeedType().getCollectionNodeName() + ".json";
    }
}
