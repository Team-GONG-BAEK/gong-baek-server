package com.ggang.be.api.group.onceGroup.strategy;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.api.group.dto.GroupRequest;
import com.ggang.be.api.group.onceGroup.service.OnceGroupService;
import com.ggang.be.api.group.registry.ApplyGroupStrategy;
import com.ggang.be.api.lectureTimeSlot.service.LectureTimeSlotService;
import com.ggang.be.api.userOnceGroup.service.UserOnceGroupService;
import com.ggang.be.domain.common.SameSchoolValidator;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.group.dto.GroupVo;
import com.ggang.be.domain.group.onceGroup.OnceGroupEntity;
import com.ggang.be.domain.group.onceGroup.dto.OnceGroupVo;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.global.annotation.Strategy;
import lombok.RequiredArgsConstructor;

@Strategy
@RequiredArgsConstructor
public class ApplyOnceGroupStrategy implements ApplyGroupStrategy {

    private final OnceGroupService onceGroupService;
    private final SameSchoolValidator sameSchoolValidator;
    private final UserOnceGroupService userOnceGroupService;
    private final LectureTimeSlotService lectureTimeSlotService;

    @Override
    public boolean support(GroupType groupType) {
        return groupType.equals(GroupType.ONCE);
    }

    @Override
    public void applyGroup(UserEntity findUserEntity, GroupRequest request) {
        OnceGroupEntity onceGroupEntity = onceGroupService.findOnceGroupEntityByGroupId(request.groupId());
        sameSchoolValidator.isUserReadMySchoolOnceGroup(findUserEntity, onceGroupEntity);
        onceGroupService.validateApplyOnceGroup(findUserEntity, onceGroupEntity);
        GroupVo groupVo = GroupVo.fromOnceGroup(OnceGroupVo.of(onceGroupEntity));

        if(checkGroupsLectureTimeSlot(findUserEntity, groupVo))
            userOnceGroupService.applyOnceGroup(findUserEntity, onceGroupEntity);
        else throw new GongBaekException(ResponseError.TIME_SLOT_ALREADY_EXIST);
    }

    private boolean checkGroupsLectureTimeSlot(UserEntity findUserEntity, GroupVo groupVo) {
        return lectureTimeSlotService.isActiveGroupsInLectureTimeSlot(
            findUserEntity,
            groupVo
        );
    }

}
