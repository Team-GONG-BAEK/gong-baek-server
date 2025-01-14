package com.ggang.be.api.userEveryGroup.service;

import com.ggang.be.domain.group.dto.ReadEveryGroupMember;
import com.ggang.be.domain.group.everyGroup.dto.ReadEveryGroup;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.domain.userEveryGroup.dto.FillMember;
import com.ggang.be.domain.userEveryGroup.dto.NearestEveryGroup;

import java.util.List;

public interface UserEveryGroupService {
    List<FillMember> getEveryGroupUsersInfo(ReadEveryGroupMember dto);

    ReadEveryGroup getMyAppliedGroups(UserEntity currentUser, boolean status);

    NearestEveryGroup getMyNearestEveryGroup(UserEntity currentUser);
}
