package com.ggang.be.api.facade;

import com.ggang.be.api.group.dto.RegisterGongbaekRequest;
import com.ggang.be.api.lectureTimeSlot.service.LectureTimeSlotService;
import com.ggang.be.api.user.service.UserService;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.timslot.lectureTimeSlot.dto.LectureTimeSlotRequest;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.global.annotation.Facade;
import com.ggang.be.global.util.TimeValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Facade
@RequiredArgsConstructor
@Slf4j
public class GongbaekRequestFacade {

    private final LectureTimeSlotService  lectureTimeSlotService;
    private final UserService userService;

    public void validateRegisterRequest(Long userId, RegisterGongbaekRequest dto) {
        TimeValidator.isTimeValid(dto.startTime(), dto.endTime());
        isDateValid(dto);
        isWeekDateRight(dto);

        UserEntity findUserEntity = userService.getUserById(userId);

        checkLectureTimeSlot(dto, findUserEntity); // 지금 해당 요일에 강의시간표가 들어가져있는지? 확인
    }

    private void isDateValid(RegisterGongbaekRequest dto) {
        if(dto.groupType() == GroupType.ONCE)
            TimeValidator.isDateBeforeNow(dto.weekDate());
        TimeValidator.isDateBeforeNow(dto.dueDate());
    }

    private void isWeekDateRight(RegisterGongbaekRequest dto) {
        if(dto.groupType() == GroupType.ONCE)
            TimeValidator.isWeekDateRight(dto.weekDay(), dto.weekDate());
    }

    private void checkLectureTimeSlot(RegisterGongbaekRequest dto, UserEntity findUserEntity) {
        LectureTimeSlotRequest lectureTimeSlotRequest = RegisterGongbaekRequest.toLectureTimeSlotRequest(
            findUserEntity, dto);
        lectureTimeSlotService.isExistInLectureTImeSlot(findUserEntity, lectureTimeSlotRequest);
    }



}
