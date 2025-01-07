package com.github.phanikb.nvd.common;

public interface INvdId {
    void validateId();

    String getPattern();

    String getId();
}
