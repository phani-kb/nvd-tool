package com.github.phanikb.nvd.cli.processor.api.download;

import java.io.IOException;
import java.io.InputStream;

import org.apache.hc.client5.http.ClientProtocolException;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.github.phanikb.nvd.api2.cpe.CpeApiJson20Schema;
import com.github.phanikb.nvd.api2.cpe.match.CpematchApiJson20Schema;
import com.github.phanikb.nvd.api2.cve.CveApiJson20Schema;
import com.github.phanikb.nvd.api2.cve.history.CveHistoryApiJson20Schema;
import com.github.phanikb.nvd.common.NvdForbiddenRequestException;
import com.github.phanikb.nvd.enums.FeedType;

public record NvdHttpClientResponseHandler<T>(Class<T> clazz) implements HttpClientResponseHandler<T> {

    @SuppressWarnings("unchecked")
    public static <T> NvdHttpClientResponseHandler<T> getResponseHandler(FeedType type) {
        return switch (type) {
            case CVE -> (NvdHttpClientResponseHandler<T>) new NvdHttpClientResponseHandler<>(CveApiJson20Schema.class);
            case CVE_HISTORY -> (NvdHttpClientResponseHandler<T>)
                    new NvdHttpClientResponseHandler<>(CveHistoryApiJson20Schema.class);
            case CPE -> (NvdHttpClientResponseHandler<T>) new NvdHttpClientResponseHandler<>(CpeApiJson20Schema.class);
            case CPE_MATCH -> (NvdHttpClientResponseHandler<T>)
                    new NvdHttpClientResponseHandler<>(CpematchApiJson20Schema.class);
            default -> throw new IllegalArgumentException("unsupported http response for feed type: " + type);
        };
    }

    @Override
    public T handleResponse(ClassicHttpResponse response) throws IOException {
        final int responseCode = response.getCode();
        if (responseCode == HttpStatus.SC_SUCCESS) {
            final HttpEntity entity = response.getEntity();
            if (entity == null) {
                throw new ClientProtocolException("response contains no content");
            }
            ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
            JsonFactory jsonFactory = mapper.getFactory();
            InputStream is = entity.getContent();
            try (JsonParser jp = jsonFactory.createParser(is)) {
                T apiJson = mapper.readValue(jp, clazz);
                if (apiJson == null) {
                    throw new ClientProtocolException("json parser response contains no content");
                }
                return apiJson;
            }
        } else {
            if (responseCode == HttpStatus.SC_FORBIDDEN) {
                throw new NvdForbiddenRequestException("response code: " + response.getReasonPhrase() + " "
                        + responseCode + " thread: " + Thread.currentThread().getName());
            }
            throw new ClientProtocolException("unexpected response code: " + response.getReasonPhrase() + " "
                    + responseCode + " thread: " + Thread.currentThread().getName());
        }
    }
}
