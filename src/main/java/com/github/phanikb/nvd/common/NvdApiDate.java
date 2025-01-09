package com.github.phanikb.nvd.common;

import java.time.LocalDateTime;

import com.github.phanikb.nvd.enums.NvdApiDateType;

public record NvdApiDate(String name, LocalDateTime value, NvdApiDateType type) {
}