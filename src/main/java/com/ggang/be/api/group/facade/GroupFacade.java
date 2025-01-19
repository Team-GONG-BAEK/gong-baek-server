package com.ggang.be.api.group.facade;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.api.gongbaekTimeSlot.service.GongbaekTimeSlotService;
import com.ggang.be.api.group.dto.*;
import com.ggang.be.api.group.everyGroup.service.EveryGroupService;
import com.ggang.be.api.group.onceGroup.service.OnceGroupService;
import com.ggang.be.api.group.registry.*;
import com.ggang.be.api.lectureTimeSlot.service.LectureTimeSlotService;
import com.ggang.be.api.mapper.GroupResponseMapper;
import com.ggang.be.api.user.service.UserService;
import com.ggang.be.api.userEveryGroup.service.UserEveryGroupService;
import com.ggang.be.api.userOnceGroup.service.UserOnceGroupService;
import com.ggang.be.domain.constant.Category;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.group.dto.GroupVo;
import com.ggang.be.domain.group.dto.RegisterGroupServiceRequest;
import com.ggang.be.domain.group.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.group.everyGroup.dto.EveryGroupVo;
import com.ggang.be.domain.group.onceGroup.OnceGroupEntity;
import com.ggang.be.domain.group.onceGroup.dto.OnceGroupVo;
import com.ggang.be.domain.timslot.gongbaekTimeSlot.GongbaekTimeSlotEntity;
import com.ggang.be.domain.timslot.gongbaekTimeSlot.dto.GongbaekTimeSlotRequest;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.domain.user.dto.UserInfo;
import com.ggang.be.domain.userEveryGroup.dto.NearestEveryGroup;
import com.ggang.be.domain.userOnceGroup.dto.NearestOnceGroup;
import com.ggang.be.global.annotation.Facade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Facade
@RequiredArgsConstructor
@Slf4j
public class GroupFacade {
    private final EveryGroupService everyGroupService;
    private final OnceGroupService onceGroupService;
    private final UserEveryGroupService userEveryGroupService;
    private final UserOnceGroupService userOnceGroupService;
    private final UserService userService;
    private final GongbaekTimeSlotService gongbaekTimeSlotService;
    private final LectureTimeSlotService lectureTimeSlotService;
    private final LatestGroupStrategyRegistry latestGroupStrategyRegistry;
    private final ReadFillMemberStrategyRegistry readFillMemberStrategyRegistry;
    private final GroupInfoStrategyRegistry groupInfoStrategyRegistry;
    private final ApplyGroupStrategyRegistry applyGroupStrategyRegistry;
    private final CancelGroupStrategyRegistry cancelGroupStrategyRegistry;
    private final GroupUserInfoStrategyRegistry groupUserInfoStrategyRegistry;

    public GroupResponse getGroupInfo(GroupType groupType, Long groupId, long userId) {
        UserEntity currentUser = userService.getUserById(userId);

        GroupInfoStrategy groupInfoStrategy = groupInfoStrategyRegistry.getGroupInfo(groupType);

        return groupInfoStrategy.getGroupInfo(groupId, currentUser);
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
        GroupUserInfoStrategy groupUserInfoStrategy = groupUserInfoStrategyRegistry.getGroupUserInfo(groupType);

        return UserInfo.of(userService.getUserById(groupUserInfoStrategy.getGroupUserInfo(groupId)));
    }

    @Transactional
    public RegisterGongbaekResponse registerGongbaek(Long userId, RegisterGongbaekRequest dto) {
        UserEntity findUserEntity = userService.getUserById(userId);

        GongbaekTimeSlotRequest gongbaekDto = convertDtoToGongbaekDto(dto, findUserEntity);
        RegisterGroupServiceRequest serviceRequest = convertDtoToServiceDto(dto, findUserEntity);

        GongbaekTimeSlotEntity gongbaekTimeSlotEntity = gongbaekTimeSlotService.registerGongbaekTimeSlot(
                findUserEntity, gongbaekDto);

        if (dto.groupType() == GroupType.WEEKLY) {
            EveryGroupEntity everyGroupEntity = everyGroupService.registerEveryGroup(serviceRequest,
                gongbaekTimeSlotEntity);
            userEveryGroupService.applyEveryGroup(findUserEntity, everyGroupEntity);
            return RegisterGongbaekResponse.of(
                everyGroupEntity.getId());
        }
        if (dto.groupType() == GroupType.ONCE) {
            OnceGroupEntity onceGroupEntity = onceGroupService.registerOnceGroup(serviceRequest,
                gongbaekTimeSlotEntity);
            userOnceGroupService.applyOnceGroup(findUserEntity, onceGroupEntity);
            return RegisterGongbaekResponse.of(onceGroupEntity.getId());
        }

        throw new GongBaekException(ResponseError.BAD_REQUEST);
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

    private RegisterGroupServiceRequest convertDtoToServiceDto(
            RegisterGongbaekRequest dto, UserEntity findUserEntity
    ) {
        return RegisterGongbaekRequest.toServiceRequest(findUserEntity, dto);
    }

    private GongbaekTimeSlotRequest convertDtoToGongbaekDto(
            RegisterGongbaekRequest dto, UserEntity findUserEntity
    ) {
        return RegisterGongbaekRequest.toGongbaekTimeSlotRequest(findUserEntity, dto);
    }

    public List<MyGroupResponse> getMyGroups(long userId, FillGroupFilterRequest filterRequestDto) {
        UserEntity currentUser = userService.getUserById(userId);

        List<GroupVo> groupResponses = switch (filterRequestDto.getFillGroupCategory()) {
            case REGISTER -> getGroupsRegister(currentUser, filterRequestDto.status());
            case APPLY -> getGroupsApply(currentUser, filterRequestDto.status());
        };

        return groupResponses.stream()
                .map(MyGroupResponse::fromGroupVo)
                .collect(Collectors.toList());
    }

    public List<ActiveGroupsResponse> getFillGroups(long userId, Category category) {
        UserEntity currentUser = userService.getUserById(userId);

        List<EveryGroupVo> everyGroupResponses = getActiveEveryGroups(currentUser, category);
        List<OnceGroupVo> onceGroupResponses = getActiveOnceGroups(currentUser, category);

        // TODO 분기처리 하기!!!
        List<GroupVo> allGroups = Stream.concat(
                        everyGroupResponses.stream().map(GroupVo::fromEveryGroup)
                                .filter(groupVo -> isSameSchoolEveryGroup(currentUser, groupVo)),
                        onceGroupResponses.stream().map(GroupVo::fromOnceGroup))
                                .filter(groupVo -> isSameSchoolOnceGroup(currentUser, groupVo))
                .sorted((group1, group2) -> group2.createdAt().compareTo(group1.createdAt()))
                .toList();

        return allGroups.stream()
                .filter(groupVo -> checkGroupsLectureTimeSlot(currentUser, groupVo))
                .map(ActiveGroupsResponse::fromGroupVo)
                .collect(Collectors.toList());
    }

    public List<ActiveGroupsResponse> getLatestGroups(long userId, GroupType groupType) {
        UserEntity currentUser = userService.getUserById(userId);

        LatestGroupStrategy latestGroupStrategy = latestGroupStrategyRegistry.getGroupStrategy(groupType);

        return latestGroupStrategy.getLatestGroups(currentUser).stream()
                .filter(groupVo -> checkGroupsLectureTimeSlot(currentUser, groupVo))
                .sorted((group1, group2) -> group2.createdAt().compareTo(group1.createdAt()))
                .limit(5)
                .map(ActiveGroupsResponse::fromGroupVo)
                .collect(Collectors.toList());
    }

    private boolean isSameSchoolOnceGroup(UserEntity currentUser, GroupVo groupVo) {
        String userSchool = currentUser.getSchool().getSchoolName();
        OnceGroupEntity onceGroupEntity = onceGroupService.findOnceGroupEntityByGroupId(groupVo.groupId());
        String groupCreatorSchool = onceGroupEntity.getUserEntity().getSchool().getSchoolName();

        return userSchool.equals(groupCreatorSchool);
    }

    private boolean isSameSchoolEveryGroup(UserEntity currentUser, GroupVo groupVo) {
        String userSchool = currentUser.getSchool().getSchoolName();
        EveryGroupEntity everyGroupEntity = everyGroupService.findEveryGroupEntityByGroupId(groupVo.groupId());
        String groupCreatorSchool = everyGroupEntity.getUserEntity().getSchool().getSchoolName();

        return userSchool.equals(groupCreatorSchool);
    }

    private List<EveryGroupVo> getActiveEveryGroups(UserEntity currentUser, Category category) {
        return everyGroupService.getActiveEveryGroups(currentUser, category).groups();
    }

    private List<OnceGroupVo> getActiveOnceGroups(UserEntity currentUser, Category category) {
        return onceGroupService.getActiveOnceGroups(currentUser, category).groups();
    }

    private boolean checkGroupsLectureTimeSlot(UserEntity findUserEntity, GroupVo groupVo) {
        return lectureTimeSlotService.isActiveGroupsInLectureTimeSlot(
                findUserEntity,
                groupVo.startTime(),
                groupVo.endTime(),
                groupVo.weekDate()
        );
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
                .sorted(Comparator.comparing(GroupVo::createdAt).reversed())
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

    public ReadFillMembersResponse getGroupUsersInfo(Long userId, ReadFillMembersRequest dto) {
        UserEntity findUserEntity = userService.getUserById(userId);

        log.info("find strategy");

        ReadFillMemberStrategy strategy = readFillMemberStrategyRegistry.getStrategy(
            dto.groupType());

        log.info("finish strategy");

        return strategy.getGroupUsersInfo(findUserEntity, dto);
    }

}
