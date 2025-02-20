package com.github.phanikb.nvd.cli.processor.pc;

import java.io.File;
import java.net.URI;
import java.time.LocalDateTime;

public class ChangeDatesQE extends DatesQE {
    public ChangeDatesQE(
            URI uri, LocalDateTime startDate, LocalDateTime endDate, int startIndex, int endIndex, File outFile) {
        super(uri, startDate, endDate, startIndex, endIndex, outFile);
    }
}
