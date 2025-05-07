package com.ggang.be.api.facade;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.api.group.dto.RegisterGongbaekRequest;
import com.ggang.be.api.lectureTimeSlot.service.LectureTimeSlotService;
import com.ggang.be.api.user.service.UserService;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.group.IntroductionValidator;
import com.ggang.be.domain.group.LocationValidator;
import com.ggang.be.domain.group.TitleValidator;
import com.ggang.be.domain.timslot.lectureTimeSlot.dto.LectureTimeSlotRequest;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.global.annotation.Facade;
import com.ggang.be.global.util.TimeValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Facade
@RequiredArgsConstructor
@Slf4j
public class GroupRequestFacade {
    private final LectureTimeSlotService  lectureTimeSlotService;
    private final UserService userService;
    private final LocationValidator locationValidator;
    private final TitleValidator titleValidator;

    public void validateRegisterRequest(Long userId, RegisterGongbaekRequest dto) {
        TimeValidator.isTimeValid(dto.startTime(), dto.endTime());
        isDateValid(dto);
        isWeekDateRight(dto);
        titleValidator.isGroupTitleValid(dto.groupTitle());
        locationValidator.isLocationValid(dto.location());
        IntroductionValidator.isIntroductionValid(dto.introduction());
        isValidCoverImg(dto);
        isValidMaxPeople(dto);

        UserEntity findUserEntity = userService.getUserById(userId);
        checkLectureTimeSlot(dto, findUserEntity);
    }

    private void isValidMaxPeople(RegisterGongbaekRequest dto) {
        if(dto.maxPeopleCount() < 2 || dto.maxPeopleCount() > 10) {
            log.error("그룹 maxPeople 검증에 실패하였습니다.");
            throw new GongBaekException(ResponseError.BAD_REQUEST);
        }
    }

    private void isValidCoverImg(RegisterGongbaekRequest dto) {
        if(dto.coverImg()<0 || dto.coverImg()>5) {
            log.error("그룹 coverImg 검증에 실패하였습니다.");
            throw new GongBaekException(ResponseError.BAD_REQUEST);
        }
    }

    private void isDateValid(RegisterGongbaekRequest dto) {
        if(dto.groupType() == GroupType.ONCE) {
            TimeValidator.isDateBeforeNow(dto.weekDate());
        }
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
