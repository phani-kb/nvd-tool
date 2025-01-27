package com.github.phanikb.nvd.cli.processor;

import java.util.concurrent.CountDownLatch;

import com.github.phanikb.nvd.common.NvdException;

public interface INvdDownloader {
    void download(CountDownLatch latch) throws NvdException;

    void generateOutputFile(String collectionNodeName) throws NvdException;

    void deleteTempDir();
}
