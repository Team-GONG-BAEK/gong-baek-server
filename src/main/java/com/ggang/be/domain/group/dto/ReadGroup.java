package com.ggang.be.domain.group.dto;

import java.util.List;

public record ReadGroup(List<GroupVo> groups) {

    public static ReadGroup of(List<GroupVo> groups) {
        return new ReadGroup(groups);
    }
}
