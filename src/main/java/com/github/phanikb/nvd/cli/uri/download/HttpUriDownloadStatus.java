package com.github.phanikb.nvd.cli.uri.download;

import java.net.URI;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HttpUriDownloadStatus {
    private URI uri;

    private String filename;

    private long size;

    private boolean success;

    private String message;

    private int statusCode;

    public HttpUriDownloadStatus(URI uri, boolean success) {
        Objects.requireNonNull(uri, "uri cannot be null");
        this.uri = uri;
        this.success = success;
    }

    @Override
    public int hashCode() {
        return getUri().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HttpUriDownloadStatus that = (HttpUriDownloadStatus) o;
        return getUri().equals(that.getUri());
    }
}
