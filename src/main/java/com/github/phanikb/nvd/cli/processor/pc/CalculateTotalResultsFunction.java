package com.github.phanikb.nvd.cli.processor.pc;

import com.github.phanikb.nvd.common.NvdException;

@FunctionalInterface
public interface CalculateTotalResultsFunction {
    int calculateTotalResults() throws NvdException;
}
