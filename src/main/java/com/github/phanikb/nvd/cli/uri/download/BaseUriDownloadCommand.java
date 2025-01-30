package com.github.phanikb.nvd.cli.uri.download;

import java.io.File;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import picocli.CommandLine;

import com.github.phanikb.nvd.cli.uri.IUriDownloadCommand;
import com.github.phanikb.nvd.enums.ArchiveType;
import com.github.phanikb.nvd.enums.TransferMethod;

@CommandLine.Command(
        name = "base-uri-download",
        mixinStandardHelpOptions = true,
        description = "Download data from URI(s).")
public abstract class BaseUriDownloadCommand implements Callable<Integer>, IUriDownloadCommand {
    protected static final Logger logger = LogManager.getLogger(BaseUriDownloadCommand.class);

    @CommandLine.ParentCommand
    protected UriDownloadCommand parent;

    @Override
    public Integer call() throws Exception {
        validateOptions();

        return 0;
    }

    @Override
    public void validateOptions() {
        parent.validateOptions();
    }

    @Override
    public Set<URI> getUris() {
        Set<URI> uris = new HashSet<>();
        String[] urls = getUrls();
        if (urls != null) {
            for (String url : urls) {
                uris.add(URI.create(url));
            }
        }
        return uris;
    }

    @Override
    public File getOutDir() {
        return parent.getOutDir();
    }

    @Override
    public boolean isDeleteTempDir() {
        return parent.isDeleteTempDir();
    }

    @Override
    public String getOutFilename() {
        return parent.getOutFilename();
    }

    @Override
    public boolean isExtract() {
        return parent.isExtract();
    }

    @Override
    public TransferMethod getTransferMethod() {
        return TransferMethod.HTTP;
    }

    @Override
    public ArchiveType getArchiveType() {
        return parent.getArchiveType();
    }
}
