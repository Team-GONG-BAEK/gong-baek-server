package com.ggang.be.domain.group.onceGroup.dto;

import java.util.List;

public record ReadOnceGroup(List<OnceGroupVo> groups) {

    public static ReadOnceGroup of(List<OnceGroupVo> groups) {
        return new ReadOnceGroup(groups);
    }
}
