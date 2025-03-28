package com.ggang.be.api.gongbaekTimeSlot.service;

import com.ggang.be.domain.timslot.gongbaekTimeSlot.GongbaekTimeSlotEntity;
import com.ggang.be.domain.timslot.gongbaekTimeSlot.dto.GongbaekTimeSlotRequest;
import com.ggang.be.domain.user.UserEntity;

public interface GongbaekTimeSlotService {
    GongbaekTimeSlotEntity registerGongbaekTimeSlot(UserEntity userEntity, GongbaekTimeSlotRequest dto);

    void removeGongbaekTimeSlotUser(long userId);
}
