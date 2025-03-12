package com.github.phanikb.nvd.cli.processor.uri;

import java.io.File;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import lombok.Getter;

import com.github.phanikb.nvd.cli.processor.CommandProcessor;
import com.github.phanikb.nvd.cli.uri.download.HttpUriDownloadStatus;
import com.github.phanikb.nvd.common.NvdException;
import com.github.phanikb.nvd.common.NvdProperties;
import com.github.phanikb.nvd.common.Util;
import com.github.phanikb.nvd.enums.ArchiveType;
import com.github.phanikb.nvd.enums.DownloadMode;
import com.github.phanikb.nvd.enums.FeedType;
import com.github.phanikb.nvd.enums.TransferMethod;

@Getter
public abstract class UriDownloadCommandProcessor extends CommandProcessor implements IUriDownloadCommandProcessor {
    private final Set<URI> uris = new HashSet<>();
    private final ArchiveType archiveType;
    private final boolean extract;
    protected List<HttpUriDownloadStatus> statusList = List.of();

    public UriDownloadCommandProcessor(FeedType feedType, File outDir, ArchiveType archiveType, boolean extract) {
        super(DownloadMode.URI, feedType, outDir);
        this.extract = extract;
        this.archiveType = archiveType;
    }

    public UriDownloadCommandProcessor(
            FeedType feedType, File outDir, ArchiveType archiveType, boolean extract, Set<URI> uris) {
        this(feedType, outDir, archiveType, extract);
        Optional.ofNullable(uris).orElse(new HashSet<>()).forEach(uri -> {
            if (uri != null) {
                this.uris.add(uri);
            }
        });
    }

    public static IUriDownloadCommandProcessor getProcessor(
            TransferMethod transferMethod,
            FeedType feedType,
            File outDir,
            ArchiveType archiveType,
            boolean extract,
            Set<URI> uris) {
        if (transferMethod == TransferMethod.HTTP) {
            return new HttpUriDownloadCommandProcessor(feedType, outDir, archiveType, extract, uris);
        } else {
            throw new IllegalArgumentException("Unsupported transfer method: " + transferMethod);
        }
    }

    @Override
    public void preProcess() throws NvdException {
        super.preProcess();
        // check uris count
        Set<URI> uris = getUris();
        if (uris == null || uris.isEmpty()) {
            throw new NvdException("no URI found.");
        }
        logger.info("uris count: {}", uris.size());
        // validate the uris
        for (URI uri : uris) {
            boolean isValid = Util.isValidDownloadUri(uri);
            if (!isValid) {
                throw new NvdException("invalid URI: " + uri);
            }
        }

        int maxConcurrentDownloads = getMaxConcurrentDownloads();
        if (maxConcurrentDownloads < 1) {
            throw new NvdException("max concurrent downloads must be greater than 0");
        }
    }

    @Override
    public void postProcess() throws NvdException {
        super.postProcess();
        // check if all uris downloaded successfully
        int successCount = 0;
        for (HttpUriDownloadStatus status : statusList) {
            if (!status.isSuccess()) {
                logger.error("failed to download: {}, status: {}", status.getUri(), status);
            } else {
                successCount++;
                // extract the downloaded file
                if (isExtract()) {
                    try {
                        Util.extract(status.getFilename(), getOutDir());
                        logger.info("extracted: {}", status.getFilename());
                    } catch (NvdException e) {
                        logger.error("failed to extract: {}, error: {}", status.getFilename(), e.getMessage());
                    }
                }
            }
        }
        if (successCount == 0) {
            throw new NvdException("failed to download any URI.");
        }
        if (successCount != statusList.size()) {
            logger.warn("failed to download {} of {} URIs.", statusList.size() - successCount, statusList.size());
        }
        logger.info("completed at {}", LocalDateTime.now());
    }

    @Override
    public int getMaxConcurrentDownloads() {
        NvdProperties properties = NvdProperties.getInstance();
        return properties.getNvd().getDownload().getUsingUri().getMaxConcurrentDownloads();
    }
}
