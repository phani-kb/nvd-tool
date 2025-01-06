package com.github.phanikb.nvd.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VersionType {
    INCLUDING("including"),
    EXCLUDING("excluding");

    private final String value;
}
