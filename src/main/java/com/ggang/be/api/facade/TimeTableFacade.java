package com.ggang.be.api.facade;

import com.ggang.be.api.gongbaekTimeSlot.dto.ReadInvalidTimeResponse;
import com.ggang.be.api.lectureTimeSlot.service.LectureTimeSlotService;
import com.ggang.be.api.user.service.UserService;
import com.ggang.be.domain.timslot.ReadCommonInvalidTimeVoMaker;
import com.ggang.be.domain.timslot.lectureTimeSlot.LectureTimeSlotEntity;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.global.annotation.Facade;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Facade
@RequiredArgsConstructor
public class TimeTableFacade {
    private final LectureTimeSlotService lectureTimeSlotService;
    private final UserService userService;

    public ReadInvalidTimeResponse readMyInvalidTime(final long userId) {
        UserEntity findUserEntity = userService.getUserById(userId);
        List<LectureTimeSlotEntity> lectureTimeSlotEntities = lectureTimeSlotService.readUserTime(findUserEntity);

        return ReadInvalidTimeResponse.fromVo(ReadCommonInvalidTimeVoMaker.convertToCommonResponse(lectureTimeSlotEntities));
    }
}
