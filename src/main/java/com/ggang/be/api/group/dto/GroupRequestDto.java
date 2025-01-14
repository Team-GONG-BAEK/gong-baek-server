package com.ggang.be.api.group.dto;

import com.ggang.be.domain.constant.GroupType;

public record GroupRequestDto(
    long groupId,
    GroupType groupType
) { }
