package com.ggang.be.api.group.onceGroup.service;

import com.ggang.be.domain.group.dto.ReadOnceGroupMember;
import com.ggang.be.domain.group.onceGroup.OnceGroupEntity;
import com.ggang.be.domain.group.onceGroup.dto.ReadOnceGroup;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.domain.userEveryGroup.dto.FillMember;
import com.ggang.be.domain.userOnceGroup.UserOnceGroupEntity;
import java.time.LocalDate;
import java.util.List;

public interface UserOnceGroupService {
    List<FillMember> getOnceGroupUsersInfo(ReadOnceGroupMember dto);

    ReadOnceGroup getMyAppliedGroups(UserEntity currentUser, boolean status);

    List<OnceGroupEntity> getGroupsByStatus(List<UserOnceGroupEntity> userOnceGroupEntities,
        boolean status);

    List<UserOnceGroupEntity> readUserTIme(UserEntity findUserEntity);
}
