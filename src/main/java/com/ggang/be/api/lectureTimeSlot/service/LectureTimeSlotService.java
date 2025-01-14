package com.ggang.be.api.lectureTimeSlot.service;

import com.ggang.be.domain.constant.WeekDate;
import com.ggang.be.domain.timslot.lectureTimeSlot.LectureTimeSlotEntity;
import com.ggang.be.domain.timslot.lectureTimeSlot.dto.LectureTimeSlotRequest;
import com.ggang.be.domain.timslot.lectureTimeSlot.vo.LectureTimeSlotVo;
import com.ggang.be.domain.user.UserEntity;

import java.util.List;

public interface LectureTimeSlotService {
    void saveLectureTimeSlot(List<LectureTimeSlotVo> timeTableVos, UserEntity userEntity);

    void isExistInLectureTImeSlot(UserEntity findUserEntity, LectureTimeSlotRequest dto);

    boolean isActiveGroupsInLectureTimeSlot(UserEntity findUserEntity, double startTime, double endTime, WeekDate weekDate);

    List<LectureTimeSlotEntity> readUserTime(UserEntity userById);
}
