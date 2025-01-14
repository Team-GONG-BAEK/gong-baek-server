package com.ggang.be.domain.group.everyGroup.dto;

import java.util.List;

public record ReadEveryGroup(List<EveryGroupVo> groups) {

    public static ReadEveryGroup of(List<EveryGroupVo> groups) {
        return new ReadEveryGroup(groups);
    }
}
