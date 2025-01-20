package com.ggang.be.api.group.everyGroup.strategy;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.api.group.dto.GroupRequest;
import com.ggang.be.api.group.everyGroup.service.EveryGroupService;
import com.ggang.be.api.group.registry.ApplyGroupStrategy;
import com.ggang.be.api.lectureTimeSlot.service.LectureTimeSlotService;
import com.ggang.be.api.userEveryGroup.service.UserEveryGroupService;
import com.ggang.be.domain.common.SameSchoolValidator;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.group.dto.GroupVo;
import com.ggang.be.domain.group.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.group.everyGroup.dto.EveryGroupVo;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.global.annotation.Strategy;
import lombok.RequiredArgsConstructor;

@Strategy
@RequiredArgsConstructor
public class ApplyEveryGroupStrategy implements ApplyGroupStrategy {

    private final EveryGroupService everyGroupService;
    private final SameSchoolValidator sameSchoolValidator;
    private final UserEveryGroupService userEveryGroupService;
    private final LectureTimeSlotService lectureTimeSlotService;

    @Override
    public boolean support(GroupType groupType) {
        return groupType.equals(GroupType.WEEKLY);
    }

    @Override
    public void applyGroup(UserEntity findUserEntity, GroupRequest request) {
        EveryGroupEntity everyGroupEntity = everyGroupService.findEveryGroupEntityByGroupId(request.groupId());
        sameSchoolValidator.isUserReadMySchoolEveryGroup(findUserEntity, everyGroupEntity);
        everyGroupService.validateApplyEveryGroup(findUserEntity, everyGroupEntity);
        GroupVo groupVo = GroupVo.fromEveryGroup(EveryGroupVo.of(everyGroupEntity));

        if(checkGroupsLectureTimeSlot(findUserEntity, groupVo))
            userEveryGroupService.applyEveryGroup(findUserEntity, everyGroupEntity);
        else throw new GongBaekException(ResponseError.TIME_SLOT_ALREADY_EXIST);
    }

    private boolean checkGroupsLectureTimeSlot(UserEntity findUserEntity, GroupVo groupVo) {
        return lectureTimeSlotService.isActiveGroupsInLectureTimeSlot(
            findUserEntity,
            groupVo
        );
    }

}
