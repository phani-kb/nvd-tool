package com.github.phanikb.nvd.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import picocli.CommandLine;

@Getter
@ToString
@NoArgsConstructor
public class CpeName implements CommandLine.ITypeConverter<CpeName> {
    public static final String CPE_MATCH_PREFIX = "cpe:2.3";

    public static final int MAX_CPE_COMPONENTS = 13;

    public static final int MIN_CPE_COMPONENTS = 3;

    private String name = null;

    public CpeName(String name) {
        this.name = name;
    }

    public void validateName() {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null");
        }
        if (name.isEmpty()) {
            throw new IllegalArgumentException("name cannot be empty");
        }
        if (!name.startsWith(CPE_MATCH_PREFIX)) {
            throw new IllegalArgumentException("Invalid CPE match string, must start with " + CPE_MATCH_PREFIX + ".");
        }
        final int i = name.length() - name.replace(":", "").length();
        if (i > MAX_CPE_COMPONENTS || i < MIN_CPE_COMPONENTS) {
            throw new IllegalArgumentException("Invalid CPE match string, too many/few CPE components.");
        }
    }

    @Override
    public CpeName convert(String value) {
        return new CpeName(value);
    }
}
