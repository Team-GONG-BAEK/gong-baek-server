package com.ggang.be.api.group.everyGroup.strategy;

import com.ggang.be.api.group.dto.PrepareRegisterInfo;
import com.ggang.be.api.group.dto.RegisterGongbaekResponse;
import com.ggang.be.api.group.everyGroup.service.EveryGroupService;
import com.ggang.be.api.group.registry.RegisterGroupStrategy;
import com.ggang.be.api.userEveryGroup.service.UserEveryGroupService;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.group.dto.RegisterGroupServiceRequest;
import com.ggang.be.domain.group.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.timslot.gongbaekTimeSlot.GongbaekTimeSlotEntity;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.global.annotation.Strategy;
import lombok.RequiredArgsConstructor;

@Strategy
@RequiredArgsConstructor
public class RegisterEveryGroupStrategy implements RegisterGroupStrategy {

    private final EveryGroupService everyGroupService;
    private final UserEveryGroupService userEveryGroupService;


    @Override
    public boolean support(GroupType groupType) {
        return groupType.equals(GroupType.WEEKLY);
    }

    @Override
    public RegisterGongbaekResponse registerGroup(PrepareRegisterInfo prepareRegisterInfo) {
        UserEntity findUserEntity = prepareRegisterInfo.findUserEntity();
        RegisterGroupServiceRequest serviceRequest = prepareRegisterInfo.request();
        GongbaekTimeSlotEntity gongbaekTimeSlotEntity = prepareRegisterInfo.gongbaekTimeSlotEntity();


        EveryGroupEntity everyGroupEntity = everyGroupService.registerEveryGroup(serviceRequest,
            gongbaekTimeSlotEntity);
        userEveryGroupService.applyEveryGroup(findUserEntity, everyGroupEntity);
        return RegisterGongbaekResponse.of(
            everyGroupEntity.getId());
    }


}
