package com.ggang.be.api.userEveryGroup.service;

import com.ggang.be.api.group.facade.NearestGroup;
import com.ggang.be.domain.group.dto.ReadEveryGroupMember;
import com.ggang.be.domain.group.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.group.everyGroup.dto.ReadEveryGroup;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.domain.userEveryGroup.dto.FillMember;

import java.util.List;

public interface UserEveryGroupService {
    List<FillMember> getEveryGroupUsersInfo(ReadEveryGroupMember dto);

    ReadEveryGroup getMyAppliedGroups(UserEntity currentUser, boolean status);

    NearestGroup getMyNearestGroup(UserEntity currentUser);

    void isValidCommentAccess(UserEntity userEntity,final long groupId);

    void applyEveryGroup(UserEntity currentUser, EveryGroupEntity everyGroupEntity);

    void cancelEveryGroup(UserEntity currentUser, EveryGroupEntity everyGroupEntity);

    void isUserInGroup(UserEntity findUserEntity, EveryGroupEntity findEveryGroupEntity);
}
