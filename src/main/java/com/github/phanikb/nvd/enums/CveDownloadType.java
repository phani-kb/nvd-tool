package com.github.phanikb.nvd.enums;

import lombok.Getter;

@Getter
public enum CveDownloadType {
    FULL("full"),
    MODIFIED("modified"),
    RECENT("recent");

    private final String value;

    CveDownloadType(String value) {
        this.value = value;
    }

}
