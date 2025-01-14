package com.ggang.be.domain.timslot.gongbaekTimeSlot.application;

import com.ggang.be.api.gongbaekTimeSlot.service.GongbaekTimeSlotService;
import com.ggang.be.domain.timslot.gongbaekTimeSlot.GongbaekTimeSlotEntity;
import com.ggang.be.domain.timslot.gongbaekTimeSlot.dto.GongbaekTimeSlotRequest;
import com.ggang.be.domain.timslot.gongbaekTimeSlot.infra.GongbaekTimeSlotRepository;
import com.ggang.be.domain.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GongbaekTimeSlotServiceImpl implements GongbaekTimeSlotService {

    private final GongbaekTimeSlotRepository gongbaekTimeSlotRepository;

    @Override
    @Transactional
    public GongbaekTimeSlotEntity registerGongbaekTimeSlot(UserEntity userEntity, GongbaekTimeSlotRequest dto) {

        GongbaekTimeSlotEntity buildEntity = GongbaekTimeSlotEntity.builder()
            .userEntity(userEntity)
            .startTime(dto.startTime())
            .endTime(dto.endTime())
            .weekDate(dto.weekDay())
            .build();

        return gongbaekTimeSlotRepository.save(buildEntity);
    }



}
