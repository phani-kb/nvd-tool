package com.github.phanikb.nvd.cli.processor.pc;

import java.io.File;
import java.net.URI;

import lombok.Getter;

import com.github.phanikb.nvd.common.QueueElement;

@Getter
public class StartIndexQE extends QueueElement {

    public StartIndexQE(URI uri, int startIndex, int endIndex, File outFile) {
        super(uri, outFile, startIndex, endIndex);
    }

    @Override
    public String toString() {
        return getOutFile().getName();
    }

    @Override
    public String getKey() {
        return String.valueOf(getStartIndex());
    }
}
