package com.ggang.be.api.userEveryGroup.service;

import com.ggang.be.domain.group.everyGroup.dto.ReadEveryGroup;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.domain.userEveryGroup.dto.NearestEveryGroup;

public interface UserEveryGroupService {
    ReadEveryGroup getMyAppliedGroups(UserEntity currentUser, boolean status);

    NearestEveryGroup getMyNearestEveryGroup(UserEntity currentUser);
}
