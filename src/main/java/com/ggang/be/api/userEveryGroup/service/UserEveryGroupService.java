package com.ggang.be.api.userEveryGroup.service;

import com.ggang.be.domain.group.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.group.everyGroup.dto.ReadEveryGroup;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.domain.userEveryGroup.UserEveryGroupEntity;

import java.util.List;

public interface UserEveryGroupService {
    ReadEveryGroup getMyAppliedGroups(UserEntity currentUser, boolean status);
    List<EveryGroupEntity> getGroupsByStatus(List<UserEveryGroupEntity> userEveryGroupEntities, boolean status);
}
