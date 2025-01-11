package com.ggang.be.api.group.everyGroup.service;

import com.ggang.be.domain.everyGroup.dto.EveryGroupDto;
import com.ggang.be.domain.user.UserEntity;

public interface EveryGroupService {
    EveryGroupDto getEveryGroupDetail(final long groupId, UserEntity userEntity);
    long getEveryGroupRegisterUserId(final long groupId);
}
