package com.ggang.be.api.group.facade;

import com.ggang.be.api.gongbaekTimeSlot.service.GongbaekTimeSlotService;
import com.ggang.be.api.group.dto.PrepareRegisterInfo;
import com.ggang.be.api.group.dto.RegisterGongbaekRequest;
import com.ggang.be.api.user.service.UserService;
import com.ggang.be.domain.group.dto.RegisterGroupServiceRequest;
import com.ggang.be.domain.timslot.gongbaekTimeSlot.GongbaekTimeSlotEntity;
import com.ggang.be.domain.timslot.gongbaekTimeSlot.dto.GongbaekTimeSlotRequest;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.global.annotation.Facade;
import lombok.RequiredArgsConstructor;

@Facade
@RequiredArgsConstructor
public class PrepareRegisterGongbaekFacade {

    private final UserService userService;
    private final GongbaekTimeSlotService gongbaekTimeSlotService;

    public PrepareRegisterInfo prepareInfo(Long userId, RegisterGongbaekRequest dto){
        UserEntity findUserEntity = userService.getUserById(userId);

        GongbaekTimeSlotRequest gongbaekDto = convertDtoToGongbaekDto(dto, findUserEntity);
        RegisterGroupServiceRequest serviceRequest = convertDtoToServiceDto(dto, findUserEntity);

        GongbaekTimeSlotEntity gongbaekTimeSlotEntity = gongbaekTimeSlotService.registerGongbaekTimeSlot(
            findUserEntity, gongbaekDto);

        return PrepareRegisterInfo.of(findUserEntity, gongbaekTimeSlotEntity, serviceRequest);
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


}
