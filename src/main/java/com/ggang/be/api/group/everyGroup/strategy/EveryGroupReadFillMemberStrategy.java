package com.ggang.be.api.group.everyGroup.strategy;

import com.ggang.be.api.group.dto.ReadFillMembersRequest;
import com.ggang.be.api.group.dto.ReadFillMembersResponse;
import com.ggang.be.api.group.everyGroup.service.EveryGroupService;
import com.ggang.be.api.group.registry.ReadFillMemberStrategy;
import com.ggang.be.api.userEveryGroup.service.UserEveryGroupService;
import com.ggang.be.domain.common.SameSchoolValidator;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.group.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.domain.userEveryGroup.dto.FillMember;
import com.ggang.be.global.annotation.Strategy;
import lombok.RequiredArgsConstructor;

import java.util.List;


@Strategy
@RequiredArgsConstructor
public class EveryGroupReadFillMemberStrategy implements ReadFillMemberStrategy {

    private final EveryGroupService everyGroupService;
    private final UserEveryGroupService userEveryGroupService;
    private final SameSchoolValidator sameSchoolValidator;

    @Override
    public boolean support(GroupType groupType) {
        return groupType == GroupType.WEEKLY;
    }

    @Override
    public ReadFillMembersResponse getGroupUsersInfo(UserEntity findUserEntity, ReadFillMembersRequest dto) {
        EveryGroupEntity findEveryGroupEntity
                = everyGroupService.findEveryGroupEntityByGroupId(dto.groupId());
        userEveryGroupService.isUserInGroup(findUserEntity, findEveryGroupEntity);
        sameSchoolValidator.isUserReadMySchoolEveryGroup(findUserEntity, findEveryGroupEntity);

        List<FillMember> everyGroupUsersInfos = userEveryGroupService.getEveryGroupUsersInfo(
            ReadFillMembersRequest.toEveryGroupMemberInfo(findEveryGroupEntity));
        return ReadFillMembersResponse.ofEveryGroup(findEveryGroupEntity, everyGroupUsersInfos);
    }


}
