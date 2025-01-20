package com.ggang.be.api.group;

import com.ggang.be.api.group.dto.CombinedGroupVos;
import com.ggang.be.api.group.everyGroup.service.EveryGroupService;
import com.ggang.be.api.group.onceGroup.service.OnceGroupService;
import com.ggang.be.domain.constant.Category;
import com.ggang.be.domain.group.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.group.everyGroup.dto.EveryGroupVo;
import com.ggang.be.domain.group.onceGroup.OnceGroupEntity;
import com.ggang.be.domain.group.onceGroup.dto.OnceGroupVo;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.global.annotation.Facade;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Facade
@RequiredArgsConstructor
public class ActivceCombinedGroupVoPreparer {

    private final EveryGroupService everyGroupService;
    private final OnceGroupService onceGroupService;

    public CombinedGroupVos prepareGroupVos(UserEntity currentUser, Category category) {
        List<EveryGroupVo> everyGroupResponses = everyGroupService.getActiveEveryGroups(currentUser,
                category).groups().
            stream().filter(groupVo -> isSameSchoolEveryGroup(currentUser, groupVo)).toList();

        List<OnceGroupVo> onceGroupResponses = onceGroupService.getActiveOnceGroups(currentUser,
                category).groups().stream()
            .filter(groupVo -> isSameSchoolOnceGroup(currentUser, groupVo)).toList();

        return CombinedGroupVos.of(everyGroupResponses, onceGroupResponses);
    }


    private boolean isSameSchoolOnceGroup(UserEntity currentUser, OnceGroupVo groupVo) {
        String userSchool = currentUser.getSchool().getSchoolName();
        OnceGroupEntity onceGroupEntity = onceGroupService.findOnceGroupEntityByGroupId(
            groupVo.groupId());
        String groupCreatorSchool = onceGroupEntity.getUserEntity().getSchool().getSchoolName();

        return userSchool.equals(groupCreatorSchool);
    }

    private boolean isSameSchoolEveryGroup(UserEntity currentUser, EveryGroupVo groupVo) {
        String userSchool = currentUser.getSchool().getSchoolName();
        EveryGroupEntity everyGroupEntity = everyGroupService.findEveryGroupEntityByGroupId(
            groupVo.groupId());
        String groupCreatorSchool = everyGroupEntity.getUserEntity().getSchool().getSchoolName();

        return userSchool.equals(groupCreatorSchool);
    }


}
