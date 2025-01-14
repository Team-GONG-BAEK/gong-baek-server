package com.ggang.be.api.facade;

import com.ggang.be.api.group.dto.FillGroupFilterRequest;
import com.ggang.be.api.group.dto.GroupResponse;
import com.ggang.be.api.group.dto.NearestGroupResponse;
import com.ggang.be.api.group.everyGroup.service.EveryGroupService;
import com.ggang.be.api.group.onceGroup.service.OnceGroupService;
import com.ggang.be.api.mapper.GroupResponseMapper;
import com.ggang.be.api.user.service.UserService;
import com.ggang.be.api.userEveryGroup.service.UserEveryGroupService;
import com.ggang.be.api.userOnceGroup.service.UserOnceGroupService;
import com.ggang.be.domain.group.dto.GroupVo;
import com.ggang.be.domain.group.dto.ReadGroup;
import com.ggang.be.domain.group.everyGroup.dto.EveryGroupVo;
import com.ggang.be.domain.group.onceGroup.dto.OnceGroupVo;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.domain.user.dto.UserInfo;
import com.ggang.be.domain.userEveryGroup.dto.NearestEveryGroup;
import com.ggang.be.domain.userOnceGroup.dto.NearestOnceGroup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@RequiredArgsConstructor
@Slf4j
public class GroupFacade {

    private final EveryGroupService everyGroupService;
    private final OnceGroupService onceGroupService;
    private final UserEveryGroupService userEveryGroupService;
    private final UserOnceGroupService userOnceGroupService;
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

    public NearestGroupResponse getNearestGroupInfo(long userId) {
        UserEntity currentUser = userService.getUserById(userId);
        NearestEveryGroup nearestEveryGroup = userEveryGroupService.getMyNearestEveryGroup(currentUser);
        NearestOnceGroup nearestOnceGroup = userOnceGroupService.getMyNearestOnceGroup(currentUser);

        if (nearestEveryGroup == null && nearestOnceGroup == null) {
            return null;
        }

        if (nearestEveryGroup == null) {
            return GroupResponseMapper.toNearestGroupResponse(nearestOnceGroup);
        } else if (nearestOnceGroup == null) {
            return GroupResponseMapper.toNearestGroupResponse(nearestEveryGroup);
        }

        return getNearestGroupFromDates(nearestEveryGroup, nearestOnceGroup);
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

    private NearestGroupResponse getNearestGroupFromDates(NearestEveryGroup nearestEveryGroup, NearestOnceGroup nearestOnceGroup) {
        LocalDate everyGroupDate = nearestEveryGroup.weekDate();
        LocalDate onceGroupDate = nearestOnceGroup.weekDate();

        log.info("EveryGroup Date: {}", everyGroupDate);
        log.info("OnceGroup Date: {}", onceGroupDate);

        if (everyGroupDate.isBefore(onceGroupDate)) {
            return GroupResponseMapper.toNearestGroupResponse(nearestEveryGroup);
        } else {
            return GroupResponseMapper.toNearestGroupResponse(nearestOnceGroup);
        }
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
        List<EveryGroupVo> everyGroupResponses = userEveryGroupService.getMyAppliedGroups(currentUser, status).groups();
        List<OnceGroupVo> onceGroupResponses = userOnceGroupService.getMyAppliedGroups(currentUser, status).groups();

        return Stream.concat(
                        everyGroupResponses.stream().map(GroupVo::fromEveryGroup),
                        onceGroupResponses.stream().map(GroupVo::fromOnceGroup))
                .sorted((group1, group2) -> group2.createdAt().compareTo(group1.createdAt()))
                .collect(Collectors.toList()
                );
    }
}
