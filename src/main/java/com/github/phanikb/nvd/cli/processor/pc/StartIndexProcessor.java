package com.github.phanikb.nvd.cli.processor.pc;

import java.io.File;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.concurrent.BlockingDeque;

import lombok.Getter;

import com.github.phanikb.nvd.common.DateFormats;
import com.github.phanikb.nvd.common.QueueElement;
import com.github.phanikb.nvd.enums.FeedType;

@Getter
public abstract class StartIndexProcessor<T> extends BaseProcessor<T> {
    protected StartIndexProcessor(
            FeedType feedType,
            T poison,
            int poisonPerCreator,
            int maxResultsPerPage,
            String endpoint,
            Path outDir,
            String outFilePrefix,
            BlockingDeque<QueueElement> downloadQueue) {
        super(feedType, poison, poisonPerCreator, maxResultsPerPage, endpoint, outDir, outFilePrefix, downloadQueue);
    }

    protected StartIndexProcessor(
            FeedType feedType, T poison, Path outDir, String outFilePrefix, BlockingDeque<QueueElement> downloadQueue) {
        super(feedType, poison, outDir, outFilePrefix, downloadQueue);
    }

    protected File getDownloadFile(int startIndex, int endIndex, Path outDir) {
        String date = LocalDateTime.now().format(DateFormats.DateFormat.DEFAULT.getFormatter());
        String si = String.format("%07d", startIndex);
        String ei = String.format("%07d", endIndex);
        return new File(outDir.toFile(), outFilePrefix + "-" + si + "_" + ei + "-" + date + ".json");
    }
}
