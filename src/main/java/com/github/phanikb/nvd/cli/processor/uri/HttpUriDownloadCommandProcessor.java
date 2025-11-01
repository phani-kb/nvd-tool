package com.github.phanikb.nvd.cli.processor.uri;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;

import com.github.phanikb.nvd.cli.uri.download.HttpUriDownloadStatus;
import com.github.phanikb.nvd.common.HttpUtil;
import com.github.phanikb.nvd.common.NvdDownloadException;
import com.github.phanikb.nvd.enums.ArchiveType;
import com.github.phanikb.nvd.enums.FeedType;

public class HttpUriDownloadCommandProcessor extends UriDownloadCommandProcessor {
    private final HttpClient httpClient;
    private final ExecutorService executor;

    private HttpUriDownloadCommandProcessor(
            FeedType feedType, File outDir, ArchiveType archiveType, boolean extract, Set<URI> uris) {
        super(feedType, outDir, archiveType, extract, uris);
        this.httpClient = buildHttpClient();
        this.executor = Executors.newFixedThreadPool(getMaxConcurrentDownloads());
    }

    public static HttpUriDownloadCommandProcessor create(
            FeedType feedType, File outDir, ArchiveType archiveType, boolean extract, Set<URI> uris) {
        return new HttpUriDownloadCommandProcessor(feedType, outDir, archiveType, extract, uris);
    }

    @Override
    public List<HttpUriDownloadStatus> download() {
        File outDir = getOutDir();
        List<CompletableFuture<HttpUriDownloadStatus>> futures = getUris().stream()
                .map(uri -> CompletableFuture.supplyAsync(() -> downloadUri(uri, outDir), executor))
                .toList();
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture<?>[0]));
        allOf.join(); // wait for all futures to complete

        return futures.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    private HttpUriDownloadStatus downloadUri(URI uri, File outDir) {
        String filename = HttpUtil.getFilename(uri);
        if (filename == null) {
            return createFailedStatus(uri, "cannot get filename from uri " + uri);
        }
        logger.info("downloading {}", uri);
        try {
            File file = new File(outDir, filename);
            return download(uri, outDir, file);
        } catch (NvdDownloadException e) {
            return createFailedStatus(uri, e.getMessage());
        }
    }

    private HttpUriDownloadStatus createFailedStatus(URI uri, String message) {
        HttpUriDownloadStatus status = new HttpUriDownloadStatus(uri, false);
        status.setMessage(message);
        logger.error(status.toString());
        return status;
    }

    @Override
    public HttpUriDownloadStatus download(URI uri, File outDir, File filename) throws NvdDownloadException {
        validateInputs(uri, filename);
        HttpUriDownloadStatus status = new HttpUriDownloadStatus(uri, false);
        status.setFilename(filename.getName());
        HttpRequest request = buildHttpRequest(uri);

        try {
            HttpResponse<InputStream> response = httpClient.send(request, HttpResponse.BodyHandlers.ofInputStream());
            return handleResponse(response, uri, outDir, filename, status);
        } catch (IOException | InterruptedException e) {
            logger.error("cannot download {}: {}", uri, e.getMessage());
            throw new NvdDownloadException("cannot download " + uri, e);
        }
    }

    private void validateInputs(URI uri, File filename) throws NvdDownloadException {
        if (uri == null) throw new NvdDownloadException("uri is null");
        if (filename == null) throw new NvdDownloadException("filename is null");
        if (filename.isDirectory()) throw new NvdDownloadException("filename " + filename + " is a directory");
        if (filename.exists()) logger.warn("filename {} already exists", filename);
    }

    private HttpRequest buildHttpRequest(URI uri) {
        return HttpRequest.newBuilder()
                .header("User-Agent", HttpUtil.getUserAgent())
                .version(HttpClient.Version.HTTP_2)
                .uri(uri)
                .GET()
                .build();
    }

    private HttpClient buildHttpClient() {
        InetSocketAddress proxy = HttpUtil.getProxy();
        HttpClient.Builder builder = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .version(HttpClient.Version.HTTP_2);
        if (proxy != null) {
            builder.proxy(ProxySelector.of(proxy));
        }
        return builder.build();
    }

    private HttpUriDownloadStatus handleResponse(
            HttpResponse<InputStream> response, URI uri, File outputDir, File filename, HttpUriDownloadStatus status)
            throws IOException {
        status.setStatusCode(response.statusCode());
        if (response.statusCode() != 200) {
            status.setMessage("cannot download " + uri + ": status code " + response.statusCode());
            logger.error(status.toString());
            return status;
        }
        try (InputStream body = response.body()) {
            File destFile = new File(outputDir, filename.getName());
            try {
                FileUtils.copyInputStreamToFile(body, destFile);
                status.setSuccess(true);
                status.setMessage("downloaded " + uri);
                status.setSize(destFile.length());
            } catch (IOException e) {
                logger.error("Error while copying input stream to file: {}", e.getMessage());
                status.setMessage("Error while downloading " + uri);
            }
            logger.debug(status.toString());
            return status;
        }
    }

    @Override
    public void close() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS)) {
                logger.warn("Executor service did not terminate");
                List<Runnable> droppedTasks = executor.shutdownNow();
                if (!droppedTasks.isEmpty()) {
                    logger.warn("Executor service terminated with {} unfinished tasks", droppedTasks.size());
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.warn("Executor service termination was interrupted");
            executor.shutdownNow();
        }
    }

    @Override
    public void process() {
        statusList = download();
    }
}
