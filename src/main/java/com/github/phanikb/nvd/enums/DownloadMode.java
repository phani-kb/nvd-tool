package com.github.phanikb.nvd.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DownloadMode {
    API("api"),
    URI("uri");

    private final String name;
}
