package com.ggang.be.api.user.vo;

import com.ggang.be.domain.constant.WeekDay;
import com.ggang.be.domain.timslot.lectureTimeSlot.vo.LectureTimeSlotVo;
import java.util.Objects;
import lombok.EqualsAndHashCode;

public record TimeTableVo(WeekDay weekDay,
                          Double startTime,
                          Double endTime) {

    public static LectureTimeSlotVo toLectureTimeSlotVo(final TimeTableVo timeTableVo) {
        return new LectureTimeSlotVo(
                timeTableVo.weekDay(),
                timeTableVo.startTime(),
                timeTableVo.endTime()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TimeTableVo that)) {
            return false;
        }
        return Objects.equals(endTime, that.endTime) && weekDay == that.weekDay
            && Objects.equals(startTime, that.startTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(weekDay, startTime, endTime);
    }
}
