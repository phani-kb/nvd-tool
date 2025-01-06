package com.github.phanikb.nvd.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CveTagType {

    DISPUTED("disputed"),
    UWA("unsupported-when-assigned"),
    EHS("exclusively-hosted-service");

    private final String value;
}
