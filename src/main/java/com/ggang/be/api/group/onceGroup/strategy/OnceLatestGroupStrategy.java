package com.ggang.be.api.group.onceGroup.strategy;

import com.ggang.be.api.group.onceGroup.service.OnceGroupService;
import com.ggang.be.api.group.registry.LatestGroupStrategy;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.group.dto.GroupVo;
import com.ggang.be.domain.group.onceGroup.OnceGroupEntity;
import com.ggang.be.domain.group.onceGroup.dto.OnceGroupVo;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.global.annotation.Strategy;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Strategy
@RequiredArgsConstructor
public class OnceLatestGroupStrategy implements LatestGroupStrategy {

    private final OnceGroupService onceGroupService;

    @Override
    public boolean support(GroupType groupType) {
        return groupType == GroupType.ONCE;
    }

    @Override
    public List<GroupVo> getLatestGroups(UserEntity userEntity) {
        List<OnceGroupVo> onceGroupResponses = onceGroupService.getActiveOnceGroups(userEntity, null).groups();
        return onceGroupResponses.stream()
                .map(GroupVo::fromOnceGroup)
                .filter(groupVo -> isSameSchoolOnceGroup(userEntity, groupVo))
                .collect(Collectors.toList());
    }

    private boolean isSameSchoolOnceGroup(UserEntity currentUser, GroupVo groupVo) {
        String userSchool = currentUser.getSchool().getSchoolName();
        OnceGroupEntity onceGroupEntity = onceGroupService.findOnceGroupEntityByGroupId(groupVo.groupId());
        String groupCreatorSchool = onceGroupEntity.getUserEntity().getSchool().getSchoolName();

        return userSchool.equals(groupCreatorSchool);
    }
}
