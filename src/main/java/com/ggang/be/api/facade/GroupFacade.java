package com.ggang.be.api.facade;

import com.ggang.be.api.group.dto.FillGroupFilterRequest;
import com.ggang.be.api.group.dto.GroupResponse;
import com.ggang.be.api.group.everyGroup.service.EveryGroupService;
import com.ggang.be.api.group.onceGroup.service.OnceGroupService;
import com.ggang.be.api.mapper.GroupResponseMapper;
import com.ggang.be.api.user.service.UserService;
import com.ggang.be.domain.group.dto.GroupVo;
import com.ggang.be.domain.group.dto.ReadGroup;
import com.ggang.be.domain.group.everyGroup.dto.EveryGroupVo;
import com.ggang.be.domain.group.onceGroup.dto.OnceGroupVo;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.domain.user.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@RequiredArgsConstructor
@Slf4j
public class GroupFacade {

    private final EveryGroupService everyGroupService;
    private final OnceGroupService onceGroupService;
    private final UserService userService;

    public GroupResponse getGroupInfo(GroupType groupType, Long groupId, long userId) {
        UserEntity currentUser = userService.getUserById(userId);

        return switch (groupType) {
            case WEEKLY -> GroupResponseMapper.fromEveryGroup(
                    everyGroupService.getEveryGroupDetail(groupId, currentUser)
            );
            case ONCE -> GroupResponseMapper.fromOnceGroup(
                    onceGroupService.getOnceGroupDetail(groupId, currentUser)
            );
        };
    }

    public UserInfo getGroupUserInfo(GroupType groupType, Long groupId) {
        long userId = switch (groupType) {
            case WEEKLY -> everyGroupService.getEveryGroupRegisterUserId(groupId);
            case ONCE -> onceGroupService.getOnceGroupRegisterUserId(groupId);
        };

        return UserInfo.of(userService.getUserById(userId));
    }

    public ReadGroup getGroups(long userId, FillGroupFilterRequest filterRequestDto) {
        UserEntity currentUser = userService.getUserById(userId);

        List<GroupVo> groupResponses = switch (filterRequestDto.getFillGroupCategory()) {
            case REGISTER -> getGroupsRegister(currentUser, filterRequestDto.status());
            case APPLY -> getGroupsApply(currentUser, filterRequestDto.status());
        };

        return ReadGroup.of(groupResponses);
    }

    private List<GroupVo> getGroupsRegister (UserEntity currentUser, boolean status) {
        List<EveryGroupVo> everyGroupResponses = everyGroupService.getMyRegisteredGroups(currentUser, status).groups();
        List<OnceGroupVo> onceGroupResponses = onceGroupService.getMyRegisteredGroups(currentUser, status).groups();

        return Stream.concat(
                        everyGroupResponses.stream().map(GroupVo::fromEveryGroup),
                        onceGroupResponses.stream().map(GroupVo::fromOnceGroup))
                .sorted((group1, group2) -> group2.createdAt().compareTo(group1.createdAt()))
                .collect(Collectors.toList()
                );
    }

    private List<GroupVo> getGroupsApply (UserEntity currentUser, boolean status) {
        List<EveryGroupVo> everyGroupResponses = everyGroupService.getMyAppliedGroups(currentUser, status).groups();
        List<OnceGroupVo> onceGroupResponses = onceGroupService.getMyAppliedGroups(currentUser, status).groups();

        return Stream.concat(
                        everyGroupResponses.stream().map(GroupVo::fromEveryGroup),
                        onceGroupResponses.stream().map(GroupVo::fromOnceGroup))
                .sorted((group1, group2) -> group2.createdAt().compareTo(group1.createdAt()))
                .collect(Collectors.toList()
                );
    }
}
