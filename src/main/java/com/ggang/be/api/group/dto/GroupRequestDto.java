package com.ggang.be.api.group.dto;

import com.ggang.be.api.facade.GroupType;

public record GroupRequestDto(
    long groupId,
    GroupType groupType
) { }
