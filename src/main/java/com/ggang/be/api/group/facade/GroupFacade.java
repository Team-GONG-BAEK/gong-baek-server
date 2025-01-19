package com.ggang.be.api.group.facade;

import com.ggang.be.api.group.GroupVoAggregator;
import com.ggang.be.api.group.dto.ActiveGroupsResponse;
import com.ggang.be.api.group.dto.FillGroupFilterRequest;
import com.ggang.be.api.group.dto.GroupRequest;
import com.ggang.be.api.group.dto.GroupResponse;
import com.ggang.be.api.group.dto.GroupResponsesDto;
import com.ggang.be.api.group.dto.MyGroupResponse;
import com.ggang.be.api.group.dto.NearestGroupResponse;
import com.ggang.be.api.group.dto.PrepareRegisterInfo;
import com.ggang.be.api.group.dto.ReadFillMembersRequest;
import com.ggang.be.api.group.dto.ReadFillMembersResponse;
import com.ggang.be.api.group.dto.RegisterGongbaekRequest;
import com.ggang.be.api.group.dto.RegisterGongbaekResponse;
import com.ggang.be.api.group.registry.ApplyGroupStrategy;
import com.ggang.be.api.group.registry.ApplyGroupStrategyRegistry;
import com.ggang.be.api.group.registry.CancelGroupStrategy;
import com.ggang.be.api.group.registry.CancelGroupStrategyRegistry;
import com.ggang.be.api.group.registry.GroupInfoStrategy;
import com.ggang.be.api.group.registry.GroupInfoStrategyRegistry;
import com.ggang.be.api.group.registry.GroupUserInfoStrategy;
import com.ggang.be.api.group.registry.GroupUserInfoStrategyRegistry;
import com.ggang.be.api.group.registry.LatestGroupStrategy;
import com.ggang.be.api.group.registry.LatestGroupStrategyRegistry;
import com.ggang.be.api.group.registry.MyGroupStrategy;
import com.ggang.be.api.group.registry.MyGroupStrategyRegistry;
import com.ggang.be.api.group.registry.ReadFillMemberStrategy;
import com.ggang.be.api.group.registry.ReadFillMemberStrategyRegistry;
import com.ggang.be.api.group.registry.RegisterGroupStrategy;
import com.ggang.be.api.group.registry.RegisterGroupStrategyRegistry;
import com.ggang.be.api.lectureTimeSlot.service.LectureTimeSlotService;
import com.ggang.be.api.mapper.GroupResponseMapper;
import com.ggang.be.api.user.service.UserService;
import com.ggang.be.api.userEveryGroup.service.UserEveryGroupService;
import com.ggang.be.api.userOnceGroup.service.UserOnceGroupService;
import com.ggang.be.domain.constant.Category;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.group.dto.GroupVo;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.domain.user.dto.UserInfo;
import com.ggang.be.domain.userEveryGroup.dto.NearestEveryGroup;
import com.ggang.be.domain.userOnceGroup.dto.NearestOnceGroup;
import com.ggang.be.global.annotation.Facade;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;


@Facade
@RequiredArgsConstructor
@Slf4j
public class GroupFacade {

    private final UserEveryGroupService userEveryGroupService;
    private final UserOnceGroupService userOnceGroupService;
    private final UserService userService;
    private final LectureTimeSlotService lectureTimeSlotService;
    private final LatestGroupStrategyRegistry latestGroupStrategyRegistry;
    private final ReadFillMemberStrategyRegistry readFillMemberStrategyRegistry;
    private final GroupInfoStrategyRegistry groupInfoStrategyRegistry;
    private final ApplyGroupStrategyRegistry applyGroupStrategyRegistry;
    private final RegisterGroupStrategyRegistry registerGroupStrategyRegistry;
    private final PrepareRegisterGongbaekFacade prepareRegisterGongbaekFacade;
    private final CancelGroupStrategyRegistry cancelGroupStrategyRegistry;
    private final GroupUserInfoStrategyRegistry groupUserInfoStrategyRegistry;
    private final PrepareGroupResponsesFacade prepareGroupResponsesFacade;
    private final MyGroupStrategyRegistry myGroupStrategyRegistry;

    public GroupResponse getGroupInfo(GroupType groupType, Long groupId, long userId) {
        UserEntity currentUser = userService.getUserById(userId);

        GroupInfoStrategy groupInfoStrategy = groupInfoStrategyRegistry.getGroupInfo(groupType);

        return groupInfoStrategy.getGroupInfo(groupId, currentUser);
    }

    public NearestGroupResponse getNearestGroupInfo(long userId) {
        UserEntity currentUser = userService.getUserById(userId);
        NearestEveryGroup nearestEveryGroup = userEveryGroupService.getMyNearestEveryGroup(
            currentUser);
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
        GroupUserInfoStrategy groupUserInfoStrategy = groupUserInfoStrategyRegistry.getGroupUserInfo(
            groupType);

        return UserInfo.of(
            userService.getUserById(groupUserInfoStrategy.getGroupUserInfo(groupId)));
    }

    @Transactional
    public RegisterGongbaekResponse registerGongbaek(Long userId, RegisterGongbaekRequest dto) {

        PrepareRegisterInfo prepareRegisterInfo = prepareRegisterGongbaekFacade.prepareInfo(userId,
            dto);

        RegisterGroupStrategy registerGroupStrategy = registerGroupStrategyRegistry.getRegisterGroupStrategy(
            dto.groupType());

        return registerGroupStrategy.registerGroup(prepareRegisterInfo);
    }

    @Transactional
    public void applyGroup(Long userId, GroupRequest requestDto) {
        UserEntity findUserEntity = userService.getUserById(userId);

        ApplyGroupStrategy applyGroupStrategy = applyGroupStrategyRegistry.getApplyGroupStrategy(
            requestDto.groupType()
        );

        applyGroupStrategy.applyGroup(findUserEntity, requestDto);
    }

    @Transactional
    public void cancelMyApplication(Long userId, GroupRequest requestDto) {
        UserEntity findUserEntity = userService.getUserById(userId);

        CancelGroupStrategy cancelGroupStrategy = cancelGroupStrategyRegistry.getCancelGroupStrategy(
            requestDto.groupType()
        );

        cancelGroupStrategy.cancelGroup(findUserEntity, requestDto);
    }

    public List<MyGroupResponse> getMyGroups(long userId, FillGroupFilterRequest filterRequestDto) {
        UserEntity currentUser = userService.getUserById(userId);

        MyGroupStrategy groupStrategy = myGroupStrategyRegistry.getGroupStrategy(
            filterRequestDto.getFillGroupCategory());

        List<GroupVo> groupResponses = groupStrategy.getGroups(currentUser,
            filterRequestDto.status());

        return groupResponses.stream()
            .map(MyGroupResponse::fromGroupVo)
            .collect(Collectors.toList());
    }

    public List<ActiveGroupsResponse> getFillGroups(long userId, Category category) {
        UserEntity currentUser = userService.getUserById(userId);

        GroupResponsesDto groupResponsesDto = prepareGroupResponsesFacade.prepareGroupResponses(
            currentUser, category);

        List<GroupVo> groupVos = GroupVoAggregator.aggregateAndSort(
            groupResponsesDto.everyGroupResponses(),
            groupResponsesDto.onceGroupResponses());

        return groupVos.stream()
            .filter(groupVo -> lectureTimeSlotService.isActiveGroupsInLectureTimeSlot(currentUser,
                groupVo))
            .map(ActiveGroupsResponse::fromGroupVo)
            .collect(Collectors.toList());
    }

    public List<ActiveGroupsResponse> getLatestGroups(long userId, GroupType groupType) {
        UserEntity currentUser = userService.getUserById(userId);

        LatestGroupStrategy latestGroupStrategy = latestGroupStrategyRegistry.getGroupStrategy(
            groupType);

        return latestGroupStrategy.getLatestGroups(currentUser).stream()
            .filter(groupVo -> lectureTimeSlotService.isActiveGroupsInLectureTimeSlot(currentUser,
                groupVo))
            .sorted((group1, group2) -> group2.createdAt().compareTo(group1.createdAt()))
            .limit(5)
            .map(ActiveGroupsResponse::fromGroupVo)
            .collect(Collectors.toList());
    }


    private NearestGroupResponse getNearestGroupFromDates(NearestEveryGroup nearestEveryGroup,
        NearestOnceGroup nearestOnceGroup) {
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

    public ReadFillMembersResponse getGroupUsersInfo(Long userId, ReadFillMembersRequest dto) {
        UserEntity findUserEntity = userService.getUserById(userId);

        log.info("find strategy");

        ReadFillMemberStrategy strategy = readFillMemberStrategyRegistry.getStrategy(
            dto.groupType());

        log.info("finish strategy");

        return strategy.getGroupUsersInfo(findUserEntity, dto);
    }

}
