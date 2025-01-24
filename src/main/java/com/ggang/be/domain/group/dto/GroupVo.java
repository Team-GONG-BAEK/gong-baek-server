package com.ggang.be.domain.group.dto;

import com.ggang.be.domain.constant.Category;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.constant.Status;
import com.ggang.be.domain.constant.WeekDay;
import com.ggang.be.domain.group.everyGroup.dto.EveryGroupVo;
import com.ggang.be.domain.group.onceGroup.dto.OnceGroupVo;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record GroupVo(long groupId, Status status, Category category, int coverImg, int profileImg, String nickname,
                      GroupType groupType,
                      String groupTitle, WeekDay weekDay, LocalDate weekDate, double startTime, double endTime, String location, LocalDateTime createdAt) {

    public static GroupVo fromEveryGroup(EveryGroupVo everyGroupVo) {
        return new GroupVo(
                everyGroupVo.groupId(),
                everyGroupVo.status(),
                everyGroupVo.category(),
                everyGroupVo.coverImg(),
                everyGroupVo.profileImg(),
                everyGroupVo.nickname(),
                GroupType.WEEKLY,
                everyGroupVo.groupTitle(),
                everyGroupVo.weekDay(),
                null,
                everyGroupVo.startTime(),
                everyGroupVo.endTime(),
                everyGroupVo.location(),
                everyGroupVo.createdAt()
        );
    }

    public static GroupVo fromOnceGroup(OnceGroupVo onceGroupVo) {
        return new GroupVo(
                onceGroupVo.groupId(),
                onceGroupVo.status(),
                onceGroupVo.category(),
                onceGroupVo.coverImg(),
                onceGroupVo.profileImg(),
                onceGroupVo.nickname(),
                GroupType.ONCE,
                onceGroupVo.groupTitle(),
                WeekDay.fromDayOfWeek(onceGroupVo.dateTime().getDayOfWeek()),
                onceGroupVo.dateTime(),
                onceGroupVo.startTime(),
                onceGroupVo.endTime(),
                onceGroupVo.location(),
                onceGroupVo.createdAt()
        );
    }
}
