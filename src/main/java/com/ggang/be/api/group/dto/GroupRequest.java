package com.ggang.be.api.group.dto;

import com.ggang.be.domain.constant.GroupType;

public record GroupRequest(
    long groupId,
    GroupType groupType
) {
    public static GroupRequest of(long groupId, GroupType groupType) {
        return new GroupRequest(groupId, groupType);
    }
}
