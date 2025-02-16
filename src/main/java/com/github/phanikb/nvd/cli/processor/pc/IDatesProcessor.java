package com.github.phanikb.nvd.cli.processor.pc;

import java.time.LocalDateTime;

import com.github.phanikb.nvd.common.NvdApiDate;
import com.github.phanikb.nvd.common.NvdException;

public interface IDatesProcessor {
    LocalDateTime getStartDate();

    LocalDateTime getEndDate();

    void validateDateRange(LocalDateTime startDate, LocalDateTime endDate) throws NvdException;

    NvdApiDate getNvdApiStartDate();

    NvdApiDate getNvdApiEndDate();
}
