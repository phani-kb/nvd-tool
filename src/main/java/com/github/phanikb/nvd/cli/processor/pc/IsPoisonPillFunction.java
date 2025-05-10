package com.github.phanikb.nvd.cli.processor.pc;

import com.github.phanikb.nvd.common.QueueElement;

@FunctionalInterface
public interface IsPoisonPillFunction {
    boolean isPoisonPill(QueueElement element);
}
