package com.ggang.be.api.group.everyGroup.service;

import com.ggang.be.domain.group.dto.ReadEveryGroupMember;
import com.ggang.be.domain.group.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.group.everyGroup.dto.ReadEveryGroup;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.domain.userEveryGroup.UserEveryGroupEntity;
import com.ggang.be.domain.userEveryGroup.dto.FillMember;
import java.util.List;

public interface UserEveryGroupService {
    List<FillMember> getEveryGroupUsersInfo(ReadEveryGroupMember dto);

    ReadEveryGroup getMyAppliedGroups(UserEntity currentUser, boolean status);

    List<EveryGroupEntity> getGroupsByStatus(List<UserEveryGroupEntity> userEveryGroupEntities,
        boolean status);
}
