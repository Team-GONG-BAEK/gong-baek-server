package com.ggang.be.api.group.onceGroup.service;

import com.ggang.be.domain.group.onceGroup.OnceGroupEntity;
import com.ggang.be.domain.group.onceGroup.dto.OnceGroupDto;
import com.ggang.be.domain.group.onceGroup.dto.ReadOnceGroup;
import com.ggang.be.domain.user.UserEntity;

import java.util.List;

public interface OnceGroupService {
    OnceGroupDto getOnceGroupDetail(final long groupId, UserEntity user);

    long getOnceGroupRegisterUserId(final long groupId);

    ReadOnceGroup getMyRegisteredGroups(UserEntity currentUser, boolean status);

    List<OnceGroupEntity> getGroupsByStatus(List<OnceGroupEntity> onceGroupEntities, boolean status);

    ReadOnceGroup getMyAppliedGroups(UserEntity userEntity, boolean status);
}
