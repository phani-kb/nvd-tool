package com.github.phanikb.nvd.cli.processor.pc;

import java.io.File;
import java.net.URI;
import java.time.LocalDateTime;

import lombok.Getter;

import com.github.phanikb.nvd.common.QueueElement;

@Getter
public abstract class DatesQE extends QueueElement {

    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    protected DatesQE(
            URI uri, LocalDateTime startDate, LocalDateTime endDate, int startIndex, int endIndex, File outFile) {
        super(uri, outFile, startIndex, endIndex);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return getOutFile() != null ? getOutFile().getName() : "No output file";
    }

    @Override
    public String getKey() {
        return String.format("%s:%s:%d", startDate, endDate, getStartIndex());
    }
}
