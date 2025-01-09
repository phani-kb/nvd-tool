package com.github.phanikb.nvd.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import picocli.CommandLine;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class CweId extends NvdId implements CommandLine.ITypeConverter<CweId> {
    public static final String ID_PATTERN = "CWE-\\d+";

    private String id;

    public CweId(String name) {
        this.id = name;
    }

    @Override
    public CweId convert(String value) {
        return new CweId(value);
    }

    @Override
    public String getPattern() {
        return ID_PATTERN;
    }
}
