package com.ggang.be.api.group.onceGroup.strategy;

import com.ggang.be.api.group.dto.PrepareRegisterInfo;
import com.ggang.be.api.group.dto.RegisterGroupResponse;
import com.ggang.be.api.group.onceGroup.service.OnceGroupService;
import com.ggang.be.api.group.registry.RegisterGroupStrategy;
import com.ggang.be.api.userOnceGroup.service.UserOnceGroupService;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.group.dto.RegisterGroupServiceRequest;
import com.ggang.be.domain.group.onceGroup.OnceGroupEntity;
import com.ggang.be.domain.timslot.gongbaekTimeSlot.GongbaekTimeSlotEntity;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.global.annotation.Strategy;
import lombok.RequiredArgsConstructor;

@Strategy
@RequiredArgsConstructor
public class RegisterOnceGroupStrategy implements RegisterGroupStrategy {

    private final OnceGroupService onceGroupService;
    private final UserOnceGroupService userOnceGroupService;

    @Override
    public boolean support(GroupType groupType) {
        return groupType.equals(GroupType.ONCE);
    }

    @Override
    public RegisterGroupResponse registerGroup(PrepareRegisterInfo prepareRegisterInfo) {
        UserEntity findUserEntity = prepareRegisterInfo.findUserEntity();
        RegisterGroupServiceRequest serviceRequest = prepareRegisterInfo.request();
        GongbaekTimeSlotEntity gongbaekTimeSlotEntity = prepareRegisterInfo.gongbaekTimeSlotEntity();

        OnceGroupEntity onceGroupEntity = onceGroupService.registerOnceGroup(serviceRequest,
            gongbaekTimeSlotEntity);
        userOnceGroupService.applyOnceGroup(findUserEntity, onceGroupEntity);
        return RegisterGroupResponse.of(onceGroupEntity.getId());
    }
}
