package com.github.phanikb.nvd.common;

public abstract class NvdId implements INvdId {
    @Override
    public void validateId() {
        String id = getId();
        if (id == null) {
            throw new IllegalArgumentException("name cannot be null");
        }
        if (id.isEmpty()) {
            throw new IllegalArgumentException("name cannot be empty");
        }
        if (!id.matches(getPattern())) {
            throw new IllegalArgumentException("Invalid ID, must match pattern " + getPattern() + ".");
        }
    }
}
