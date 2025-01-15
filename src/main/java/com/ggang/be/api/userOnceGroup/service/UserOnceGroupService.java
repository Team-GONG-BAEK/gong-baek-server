package com.ggang.be.api.userOnceGroup.service;

import com.ggang.be.domain.group.dto.ReadOnceGroupMember;
import com.ggang.be.domain.group.onceGroup.OnceGroupEntity;
import com.ggang.be.domain.group.onceGroup.dto.ReadOnceGroup;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.domain.userEveryGroup.dto.FillMember;
import com.ggang.be.domain.userOnceGroup.UserOnceGroupEntity;
import com.ggang.be.domain.userOnceGroup.dto.NearestOnceGroup;

import java.util.List;

public interface UserOnceGroupService {
    List<FillMember> getOnceGroupUsersInfo(ReadOnceGroupMember dto);

    ReadOnceGroup getMyAppliedGroups(UserEntity currentUser, boolean status);

    NearestOnceGroup getMyNearestOnceGroup(UserEntity currentUser);

    List<UserOnceGroupEntity> readUserTIme(UserEntity findUserEntity);

    void applyOnceGroup(UserEntity currentUser, OnceGroupEntity onceGroupEntity);

    void cancelOnceGroup(UserEntity currentUser, OnceGroupEntity onceGroupEntity);

    void isValidCommentAccess(UserEntity userEntity, final long groupId);
}
