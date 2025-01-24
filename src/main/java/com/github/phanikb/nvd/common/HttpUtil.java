package com.github.phanikb.nvd.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.io.FilenameUtils;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.DefaultHttpRequestRetryStrategy;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.message.BasicHeader;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.util.TimeValue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import static com.github.phanikb.nvd.common.Constants.DEFAULT_DELAY_BETWEEN_REQUESTS_IN_MS;
import static com.github.phanikb.nvd.common.Constants.DEFAULT_RATE_LIMIT;

public final class HttpUtil {
    private static final Logger logger = LogManager.getLogger(HttpUtil.class);
    private static final NvdProperties properties = NvdProperties.getInstance();

    private HttpUtil() {
        // prevent instantiation
    }

    /**
     * Gets the filename from the given URI.
     *
     * @param uri the URI to extract the filename from
     * @return the filename, or null if the path is empty
     */
    public static String getFilename(URI uri) {
        String path = uri.getPath();
        return Util.isNullOrEmpty(path) ? null : FilenameUtils.getName(path);
    }

    /**
     * Gets a random user agent from the list of user agents.
     *
     * @return a random user agent string
     */
    public static String getUserAgent() {
        try {
            SecureRandom sr = SecureRandom.getInstanceStrong();
            String[] userAgents = UserAgents.getUserAgents().toArray(new String[0]);
            return userAgents[sr.nextInt(userAgents.length)];
        } catch (NoSuchAlgorithmException e) {
            logger.error("Error getting user agent: {}", e.getMessage());
            return UserAgents.getDefaultUserAgent();
        }
    }

    /**
     * Gets the proxy settings.
     *
     * @return an InetSocketAddress for the proxy, or null if not configured
     */
    public static InetSocketAddress getProxy() {
        String host = properties.getNvd().getProxy().getHost();
        Integer port = properties.getNvd().getProxy().getPort();
        return (Util.isNullOrEmpty(host) || port == null) ? null : new InetSocketAddress(host, port);
    }

    public static TimeValue getDownloadDelay() {
        long configuredDelay = properties.getNvd().getDownload().getUsingApi().getDelayBetweenRequestsInMs();
        int rateLimit = getRateLimit();
        int windowSizeInSecs =
                properties.getNvd().getDownload().getUsingApi().getProcessor().getRollingWindowInSecs();

        if (rateLimit > 0) {
            long minDelay = (windowSizeInSecs * 1000L) / rateLimit;
            return TimeValue.ofMilliseconds(Stream.of(configuredDelay, minDelay, DEFAULT_DELAY_BETWEEN_REQUESTS_IN_MS)
                    .max(Long::compare)
                    .orElse(configuredDelay));
        }

        return TimeValue.ofMilliseconds(configuredDelay);
    }

    public static int getRateLimit() {
        int rateLimitWithKey =
                properties.getNvd().getDownload().getUsingApi().getProcessor().getRateLimitWithKey();
        int rateLimitWithoutKey =
                properties.getNvd().getDownload().getUsingApi().getProcessor().getRateLimitWithoutKey();
        String apiKey = properties.getNvd().getApi().getKey();

        int rateLimit = Util.isNullOrEmpty(apiKey) ? rateLimitWithoutKey : rateLimitWithKey;

        if (rateLimit < 0) {
            logger.warn("rate limit is less than 0, setting to default: {}", DEFAULT_RATE_LIMIT);
            rateLimit = DEFAULT_RATE_LIMIT;
        }

        return rateLimit;
    }

    public static int getRollingWindowSizeInSecs() {
        return properties.getNvd().getDownload().getUsingApi().getProcessor().getRollingWindowInSecs();
    }

    public static int getMaxRetries() {
        return properties.getNvd().getDownload().getUsingApi().getMaxRetries();
    }

    public static int getRetryIntervalInSecs() {
        return properties.getNvd().getDownload().getUsingApi().getRetryIntervalInSecs();
    }

    public static List<BasicHeader> getNvdDefaultHeaders() {
        String apiKey = properties.getNvd().getApi().getKey();
        List<BasicHeader> uriHeaders = new ArrayList<>();
        uriHeaders.add(new BasicHeader("Accept", "application/json"));
        uriHeaders.add(new BasicHeader("User-Agent", getUserAgent()));
        uriHeaders.add(new BasicHeader("Content-Type", "application/json"));
        if (!Util.isNullOrEmpty(apiKey)) {
            uriHeaders.add(new BasicHeader("X-Api-Key", apiKey));
        }
        return uriHeaders;
    }

    public static List<NameValuePair> getNvdDefaultParams(int rpp) {
        List<NameValuePair> uriParams = new ArrayList<>();
        uriParams.add(new BasicNameValuePair("resultsPerPage", String.valueOf(rpp)));
        return uriParams;
    }

    public static <T> T getApiJson(
            URI uri, CloseableHttpClient httpclient, HttpClientResponseHandler<T> responseHandler)
            throws IOException, NvdException {
        final HttpGet request = new HttpGet(uri);
        for (NameValuePair header : getNvdDefaultHeaders()) {
            request.addHeader(header.getName(), header.getValue());
        }
        T apiJson = httpclient.execute(request, responseHandler);

        if (apiJson == null) {
            throw new NvdException("failed to download API JSON");
        }
        return apiJson;
    }

    public static <T> void downloadHttpGetRequest(URI uri, File outFile, HttpClientResponseHandler<T> responseHandler)
            throws NvdException {
        // replaceUriHeader("User-Agent", getUserAgent());
        try (CloseableHttpClient httpclient = HttpClients.custom()
                .setRetryStrategy(new DefaultHttpRequestRetryStrategy(
                        HttpUtil.getMaxRetries(), TimeValue.ofSeconds(HttpUtil.getRetryIntervalInSecs())))
                .setDefaultHeaders(HttpUtil.getNvdDefaultHeaders())
                .build()) {
            T apiJson = getApiJson(uri, httpclient, responseHandler);
            try (BufferedWriter writer = Files.newBufferedWriter(outFile.toPath())) {
                ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                // mapper.enable(SerializationFeature.INDENT_OUTPUT);
                mapper.writeValue(writer, apiJson);
            }
        } catch (Exception e) {
            throw new NvdException("failed to download API JSON: " + e.getMessage(), e);
        }
    }
}
