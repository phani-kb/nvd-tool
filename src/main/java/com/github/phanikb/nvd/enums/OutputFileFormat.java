package com.github.phanikb.nvd.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OutputFileFormat {
    DATE("date"),
    CKSUM("cksum"),
    CKSUM_DATE("cksum.date"),
    YEAR_DATE("year.date"),
    YEAR_CKSUM_DATE("year.cksum.date");

    private final String format;
}
