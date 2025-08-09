package com.github.phanikb.nvd.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import picocli.CommandLine;

@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class CveId extends NvdId implements CommandLine.ITypeConverter<CveId> {
    public static final String ID_PATTERN = "CVE-\\d{4}-\\d{4,}";

    private String id;

    @Override
    public CveId convert(String id) {
        this.id = id;
        return this;
    }

    @Override
    public String getPattern() {
        return ID_PATTERN;
    }
}
