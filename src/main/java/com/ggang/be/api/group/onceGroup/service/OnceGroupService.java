package com.ggang.be.api.group.onceGroup.service;

import com.ggang.be.domain.onceGroup.dto.OnceGroupDto;
import com.ggang.be.domain.user.UserEntity;

public interface OnceGroupService {
    OnceGroupDto getOnceGroupDetail(final long groupId, UserEntity user);
    long getOnceGroupRegisterUserId(final long groupId);
}
