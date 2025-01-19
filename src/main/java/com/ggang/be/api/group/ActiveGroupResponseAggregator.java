package com.ggang.be.api.group;


import com.ggang.be.api.group.dto.GroupResponsesDto;
import com.ggang.be.api.group.everyGroup.service.EveryGroupService;
import com.ggang.be.api.group.onceGroup.service.OnceGroupService;
import com.ggang.be.domain.group.dto.GroupVo;
import com.ggang.be.domain.group.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.group.everyGroup.dto.EveryGroupVo;
import com.ggang.be.domain.group.onceGroup.OnceGroupEntity;
import com.ggang.be.domain.group.onceGroup.dto.OnceGroupVo;
import com.ggang.be.domain.user.UserEntity;
import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ActiveGroupResponseAggregator {

    private final EveryGroupService everyGroupService;
    private final OnceGroupService onceGroupService;


    public List<GroupVo> aggregateActiveGroupResponses(
        GroupResponsesDto groupResponsesDto,
        UserEntity currentUser) {
        List<EveryGroupVo> everyGroupResponses = groupResponsesDto.everyGroupResponses();
        List<OnceGroupVo> onceGroupResponses = groupResponsesDto.onceGroupResponses();
        return Stream.concat(
                everyGroupResponses.stream().map(GroupVo::fromEveryGroup)
                    .filter(groupVo -> isSameSchoolEveryGroup(currentUser, groupVo)),
                onceGroupResponses.stream().map(GroupVo::fromOnceGroup))
            .filter(groupVo -> isSameSchoolOnceGroup(currentUser, groupVo))
            .sorted((group1, group2) -> group2.createdAt().compareTo(group1.createdAt()))
            .toList();
    }

    private boolean isSameSchoolOnceGroup(UserEntity currentUser, GroupVo groupVo) {
        String userSchool = currentUser.getSchool().getSchoolName();
        OnceGroupEntity onceGroupEntity = onceGroupService.findOnceGroupEntityByGroupId(
            groupVo.groupId());
        String groupCreatorSchool = onceGroupEntity.getUserEntity().getSchool().getSchoolName();

        return userSchool.equals(groupCreatorSchool);
    }

    private boolean isSameSchoolEveryGroup(UserEntity currentUser, GroupVo groupVo) {
        String userSchool = currentUser.getSchool().getSchoolName();
        EveryGroupEntity everyGroupEntity = everyGroupService.findEveryGroupEntityByGroupId(
            groupVo.groupId());
        String groupCreatorSchool = everyGroupEntity.getUserEntity().getSchool().getSchoolName();

        return userSchool.equals(groupCreatorSchool);
    }


}
