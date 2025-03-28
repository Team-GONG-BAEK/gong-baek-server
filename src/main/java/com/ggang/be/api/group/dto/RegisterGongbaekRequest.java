package com.ggang.be.api.group.dto;

import com.ggang.be.domain.constant.Category;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.constant.WeekDay;
import com.ggang.be.domain.group.dto.RegisterGroupServiceRequest;
import com.ggang.be.domain.timslot.gongbaekTimeSlot.dto.GongbaekTimeSlotRequest;
import com.ggang.be.domain.timslot.lectureTimeSlot.dto.LectureTimeSlotRequest;
import com.ggang.be.domain.user.UserEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Objects;

public record RegisterGongbaekRequest(
        GroupType groupType,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate weekDate,
        WeekDay weekDay,
        double startTime,
        double endTime,
        Category category,
        int coverImg,
        String location,
        int maxPeopleCount,
        String groupTitle,
        String introduction
) {
    public static LectureTimeSlotRequest toLectureTimeSlotRequest(
            UserEntity userEntity, RegisterGongbaekRequest request
    ) {
        if (Objects.isNull(request.weekDay))
            return LectureTimeSlotRequest.of(
                    userEntity,
                    request.startTime(),
                    request.endTime(),
                    WeekDay.fromDayOfWeek(request.weekDate().getDayOfWeek())
            );
        else
            return LectureTimeSlotRequest.of(
                    userEntity,
                    request.startTime(),
                    request.endTime(),
                    request.weekDay()
            );
    }

    public static GongbaekTimeSlotRequest toGongbaekTimeSlotRequest(
            UserEntity userEntity, RegisterGongbaekRequest request
    ) {

        if (Objects.isNull(request.weekDay)) {
            return new GongbaekTimeSlotRequest(
                    userEntity,
                    request.startTime(),
                    request.endTime(),
                    WeekDay.fromDayOfWeek(request.weekDate().getDayOfWeek())
            );
        } else {
            return new GongbaekTimeSlotRequest(
                    userEntity,
                    request.startTime(),
                    request.endTime(),
                    request.weekDay
            );
        }
    }

    public static RegisterGroupServiceRequest toServiceRequest(
            UserEntity userEntity, RegisterGongbaekRequest request
    ) {
        return new RegisterGroupServiceRequest(
                userEntity,
                request.groupType(),
                request.weekDate(),
                request.weekDay(),
                request.startTime(),
                request.endTime(),
                request.category(),
                request.coverImg(),
                request.location(),
                request.maxPeopleCount(),
                request.groupTitle(),
                request.introduction()
        );
    }
}
