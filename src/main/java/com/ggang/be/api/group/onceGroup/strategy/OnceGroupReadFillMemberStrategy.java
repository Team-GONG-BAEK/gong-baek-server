package com.ggang.be.api.group.onceGroup.strategy;

import com.ggang.be.api.group.dto.ReadFillMembersRequest;
import com.ggang.be.api.group.dto.ReadFillMembersResponse;
import com.ggang.be.api.group.onceGroup.service.OnceGroupService;
import com.ggang.be.api.group.registry.ReadFillMemberStrategy;
import com.ggang.be.api.userOnceGroup.service.UserOnceGroupService;
import com.ggang.be.domain.common.SameSchoolValidator;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.group.onceGroup.OnceGroupEntity;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.domain.userEveryGroup.dto.FillMember;
import com.ggang.be.global.annotation.Strategy;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Strategy
@RequiredArgsConstructor
public class OnceGroupReadFillMemberStrategy implements ReadFillMemberStrategy {

    private final OnceGroupService onceGroupService;
    private final UserOnceGroupService userOnceGroupService;
    private final SameSchoolValidator sameSchoolValidator;

    @Override
    public boolean support(GroupType groupType) {
        return groupType == GroupType.ONCE;
    }

    @Override
    public ReadFillMembersResponse getGroupUsersInfo(UserEntity findUserEntity, ReadFillMembersRequest dto) {
        OnceGroupEntity findOnceGroupEntity = onceGroupService.findOnceGroupEntityByGroupId(
            dto.groupId());

        userOnceGroupService.isUserInGroup(findUserEntity, findOnceGroupEntity);
        sameSchoolValidator.isUserReadMySchoolOnceGroup(findUserEntity, findOnceGroupEntity);

        List<FillMember> everyGroupUsersInfos = userOnceGroupService.getOnceGroupUsersInfo(
            ReadFillMembersRequest.toOnceGroupMemberInfo(findOnceGroupEntity));
        return ReadFillMembersResponse.ofOnceGroup(findOnceGroupEntity, everyGroupUsersInfos);
    }


}
