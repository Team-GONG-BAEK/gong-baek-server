package com.ggang.be.api.facade;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.api.gongbaekTimeSlot.service.GongbaekTimeSlotService;
import com.ggang.be.api.group.dto.*;
import com.ggang.be.api.group.everyGroup.service.EveryGroupService;
import com.ggang.be.api.group.onceGroup.service.OnceGroupService;
import com.ggang.be.api.lectureTimeSlot.service.LectureTimeSlotService;
import com.ggang.be.api.mapper.GroupResponseMapper;
import com.ggang.be.api.user.service.UserService;
import com.ggang.be.api.userEveryGroup.service.UserEveryGroupService;
import com.ggang.be.api.userOnceGroup.service.UserOnceGroupService;
import com.ggang.be.domain.common.SameSchoolValidator;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.constant.WeekDate;
import com.ggang.be.domain.group.dto.GroupVo;
import com.ggang.be.domain.group.dto.ReadGroup;
import com.ggang.be.domain.group.dto.RegisterGroupServiceRequest;
import com.ggang.be.domain.group.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.group.everyGroup.dto.EveryGroupVo;
import com.ggang.be.domain.group.onceGroup.OnceGroupEntity;
import com.ggang.be.domain.group.onceGroup.dto.OnceGroupVo;
import com.ggang.be.domain.timslot.gongbaekTimeSlot.GongbaekTimeSlotEntity;
import com.ggang.be.domain.timslot.gongbaekTimeSlot.dto.GongbaekTimeSlotRequest;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.domain.user.dto.UserInfo;
import com.ggang.be.domain.userEveryGroup.dto.FillMember;
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
    private final SameSchoolValidator sameSchoolValidator;

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

    @Transactional
    public RegisterGongbaekResponse registerGongbaek(Long userId, RegisterGongbaekRequest dto) {
        UserEntity findUserEntity = userService.getUserById(userId);

        GongbaekTimeSlotRequest gongbaekDto = convertDtoToGongbaekDto(dto, findUserEntity);
        RegisterGroupServiceRequest serviceRequest = convertDtoToServiceDto(dto, findUserEntity);

        GongbaekTimeSlotEntity gongbaekTimeSlotEntity = gongbaekTimeSlotService.registerGongbaekTimeSlot(
                findUserEntity, gongbaekDto);

        if (dto.groupType() == GroupType.WEEKLY) {
            return RegisterGongbaekResponse.of(
                    everyGroupService.registerEveryGroup(serviceRequest, gongbaekTimeSlotEntity));
        }
        if (dto.groupType() == GroupType.ONCE) {
            return RegisterGongbaekResponse.of(
                    onceGroupService.registerOnceGroup(serviceRequest, gongbaekTimeSlotEntity));
        }

        throw new GongBaekException(ResponseError.BAD_REQUEST);
    }

    @Transactional
    public void applyGroup(Long userId, GroupRequest requesDto) {
        UserEntity findUserEntity = userService.getUserById(userId);

        switch (requesDto.groupType()){
            case WEEKLY -> {
                EveryGroupEntity everyGroupEntity = everyGroupService.findEveryGroupEntityByGroupId(requesDto.groupId());
                if(everyGroupService.validateApplyEveryGroup(findUserEntity, everyGroupEntity))
                    userEveryGroupService.applyEveryGroup(findUserEntity, everyGroupEntity);
                else throw new GongBaekException(ResponseError.BAD_REQUEST);
            }
            case ONCE -> {
                OnceGroupEntity onceGroupEntity = onceGroupService.findOnceGroupEntityByGroupId(requesDto.groupId());
                if(onceGroupService.validateApplyOnceGroup(findUserEntity, onceGroupEntity))
                    userOnceGroupService.applyOnceGroup(findUserEntity, onceGroupEntity);
                else throw new GongBaekException(ResponseError.BAD_REQUEST);
            }
        }
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

    public ReadGroup getMyGroups(long userId, FillGroupFilterRequest filterRequestDto) {
        UserEntity currentUser = userService.getUserById(userId);

        List<GroupVo> groupResponses = switch (filterRequestDto.getFillGroupCategory()) {
            case REGISTER -> getGroupsRegister(currentUser, filterRequestDto.status());
            case APPLY -> getGroupsApply(currentUser, filterRequestDto.status());
        };

        return ReadGroup.of(groupResponses);
    }

    public List<ActiveGroupsResponse> getFillGroups(long userId) {
        UserEntity currentUser = userService.getUserById(userId);

        List<EveryGroupVo> everyGroupResponses = getActiveEveryGroups(currentUser);
        List<OnceGroupVo> onceGroupResponses = getActiveOnceGroups(currentUser);

        List<GroupVo> allGroups = Stream.concat(
                        everyGroupResponses.stream().map(GroupVo::fromEveryGroup),
                        onceGroupResponses.stream().map(GroupVo::fromOnceGroup))
                .sorted((group1, group2) -> group2.createdAt().compareTo(group1.createdAt()))
                .toList();

        return allGroups.stream()
                .filter(groupVo -> checkGroupsLectureTimeSlot(currentUser, groupVo.startTime(), groupVo.endTime(), groupVo.weekDate()))
                .map(ActiveGroupsResponse::fromGroupVo)
                .collect(Collectors.toList());
    }

    public List<ActiveGroupsResponse> getLatestGroups(long userId, GroupType groupType) {
        UserEntity currentUser = userService.getUserById(userId);

        List<GroupVo> allGroups;
        switch (groupType) {
            case WEEKLY -> {
                List<EveryGroupVo> everyGroupResponses = getActiveEveryGroups(currentUser);
                allGroups = everyGroupResponses.stream()
                        .map(GroupVo::fromEveryGroup)
                        .collect(Collectors.toList());
            }
            case ONCE -> {
                List<OnceGroupVo> onceGroupResponses = getActiveOnceGroups(currentUser);
                allGroups = onceGroupResponses.stream()
                        .map(GroupVo::fromOnceGroup)
                        .collect(Collectors.toList());
            }
            default -> throw new GongBaekException(ResponseError.BAD_REQUEST);
        }

        return allGroups.stream()
                .filter(groupVo -> checkGroupsLectureTimeSlot(currentUser, groupVo.startTime(), groupVo.endTime(), groupVo.weekDate()))
                .sorted((group1, group2) -> group2.createdAt().compareTo(group1.createdAt()))
                .limit(5)
                .map(ActiveGroupsResponse::fromGroupVo)
                .collect(Collectors.toList());
    }

    private List<EveryGroupVo> getActiveEveryGroups(UserEntity currentUser) {
        return everyGroupService.getActiveEveryGroups(currentUser).groups();
    }

    private List<OnceGroupVo> getActiveOnceGroups(UserEntity currentUser) {
        return onceGroupService.getActiveOnceGroups(currentUser).groups();
    }

    private boolean checkGroupsLectureTimeSlot(UserEntity findUserEntity, double startTime, double endTime, WeekDate weekDate) {
        return lectureTimeSlotService.isActiveGroupsInLectureTimeSlot(findUserEntity, startTime, endTime, weekDate);
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
        if (dto.groupType() == GroupType.WEEKLY) {
            EveryGroupEntity findEveryGroupEntity = everyGroupService.findEveryGroupEntityByGroupId(
                    dto.groupId());
            sameSchoolValidator.isUserReadMySchoolEveryGroup(findUserEntity, findEveryGroupEntity);

            List<FillMember> everyGroupUsersInfos = userEveryGroupService.getEveryGroupUsersInfo(
                    ReadFillMembersRequest.toEveryGroupMemberInfo(findEveryGroupEntity));
            return ReadFillMembersResponse.ofEveryGroup(findEveryGroupEntity, everyGroupUsersInfos);
        }
        if (dto.groupType() == GroupType.ONCE) {
            OnceGroupEntity findOnceGroupEntity = onceGroupService.findOnceGroupEntityByGroupId(
                    dto.groupId());
            sameSchoolValidator.isUserReadMySchoolOnceGroup(findUserEntity, findOnceGroupEntity);

            List<FillMember> onceGroupUserInfos = userOnceGroupService.getOnceGroupUsersInfo(
                    ReadFillMembersRequest.toOnceGroupMemberInfo(findOnceGroupEntity));
            return ReadFillMembersResponse.ofOnceGroup(findOnceGroupEntity, onceGroupUserInfos);
        }
        throw new GongBaekException(ResponseError.BAD_REQUEST);
    }
}
