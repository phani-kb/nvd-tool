package com.github.phanikb.nvd.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NvdApiDateType {
    START_DATE("startDate"),
    END_DATE("endDate");

    private final String name;
}
