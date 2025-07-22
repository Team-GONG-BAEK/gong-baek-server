package com.ggang.be.api.group;

import com.ggang.be.api.group.dto.CombinedGroupVos;
import com.ggang.be.api.group.everyGroup.service.EveryGroupService;
import com.ggang.be.api.group.onceGroup.service.OnceGroupService;
import com.ggang.be.domain.constant.Category;
import com.ggang.be.domain.group.everyGroup.dto.EveryGroupVo;
import com.ggang.be.domain.group.onceGroup.dto.OnceGroupVo;
import com.ggang.be.domain.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ActiveCombinedGroupVoPreparer {

    private final EveryGroupService everyGroupService;
    private final OnceGroupService onceGroupService;

    public CombinedGroupVos prepareGroupVos(UserEntity currentUser, Category category, List<Long> reportedUserIds) {

        List<EveryGroupVo> everyGroupResponses = everyGroupService.getActiveEveryGroups(currentUser, category)
                .groups().stream()
                .filter(groupVo -> groupVo.creatorId() != null)
                .filter(groupVo -> !reportedUserIds.contains(groupVo.creatorId()))
                .filter(groupVo -> everyGroupService.isSameSchoolEveryGroup(currentUser, groupVo))
                .toList();

        List<OnceGroupVo> onceGroupResponses = onceGroupService.getActiveOnceGroups(currentUser, category)
                .groups().stream()
                .filter(groupVo -> groupVo.creatorId() != null)
                .filter(groupVo -> !reportedUserIds.contains(groupVo.creatorId()))
                .filter(groupVo -> onceGroupService.isSameSchoolOnceGroup(currentUser, groupVo))
                .toList();

        return CombinedGroupVos.of(everyGroupResponses, onceGroupResponses);
    }
}
