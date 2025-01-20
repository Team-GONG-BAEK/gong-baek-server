package com.ggang.be.api.group.dto;

import com.ggang.be.domain.group.dto.RegisterGroupServiceRequest;
import com.ggang.be.domain.timslot.gongbaekTimeSlot.GongbaekTimeSlotEntity;
import com.ggang.be.domain.user.UserEntity;

public record PrepareRegisterInfo(
        UserEntity findUserEntity,
        GongbaekTimeSlotEntity gongbaekTimeSlotEntity,
        RegisterGroupServiceRequest request
) {
    public static PrepareRegisterInfo of(
            UserEntity findUserEntity, GongbaekTimeSlotEntity gongbaekTimeSlotEntity,
            RegisterGroupServiceRequest serviceRequest
    ) {
        return new PrepareRegisterInfo(findUserEntity, gongbaekTimeSlotEntity, serviceRequest);
    }
}
