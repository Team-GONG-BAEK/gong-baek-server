package com.ggang.be.domain.lectureTimeSlot.infra;

import com.ggang.be.domain.constant.WeekDate;
import com.ggang.be.domain.lectureTimeSlot.LectureTimeSlotEntity;
import com.ggang.be.domain.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureTimeSlotRepository extends JpaRepository<LectureTimeSlotEntity, Long> {

    boolean existsByStartTimeAndEndTimeAndUserEntityAndWeekDate(double startTime, double endTime, UserEntity findUserEntity, WeekDate weekDate);
}
