package com.github.phanikb.nvd.cli.uri;

import java.net.URI;
import java.util.Set;

import com.github.phanikb.nvd.cli.INvdCommand;
import com.github.phanikb.nvd.enums.ArchiveType;
import com.github.phanikb.nvd.enums.FeedType;
import com.github.phanikb.nvd.enums.TransferMethod;

public interface IUriDownloadCommand extends INvdCommand {
    Set<URI> getUris();

    String[] getUrls();

    FeedType getFeedType();

    boolean isExtract();

    ArchiveType getArchiveType();

    TransferMethod getTransferMethod();
}
