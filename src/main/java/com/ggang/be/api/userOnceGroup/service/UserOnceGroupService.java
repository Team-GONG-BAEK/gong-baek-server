package com.ggang.be.api.userOnceGroup.service;

import com.ggang.be.domain.group.onceGroup.dto.ReadOnceGroup;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.domain.userOnceGroup.dto.NearestOnceGroup;

public interface UserOnceGroupService {
    ReadOnceGroup getMyAppliedGroups(UserEntity currentUser, boolean status);

    NearestOnceGroup getMyNearestOnceGroup(UserEntity currentUser);

}
