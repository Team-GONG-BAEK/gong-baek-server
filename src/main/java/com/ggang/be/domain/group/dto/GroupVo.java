package com.ggang.be.domain.group.dto;

import com.ggang.be.domain.constant.Category;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.constant.Status;
import com.ggang.be.domain.constant.WeekDate;
import com.ggang.be.domain.group.everyGroup.dto.EveryGroupVo;
import com.ggang.be.domain.group.onceGroup.dto.OnceGroupVo;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record GroupVo(long groupId, Status status, Category category, int coverImg, GroupType groupType,
                      String groupTitle, WeekDate weekDate, LocalDate groupDate, double startTime, double endTime, String location, LocalDateTime createdAt) {

    public static GroupVo fromEveryGroup(EveryGroupVo everyGroupVo) {
        return new GroupVo(
                everyGroupVo.groupId(),
                everyGroupVo.status(),
                everyGroupVo.category(),
                everyGroupVo.coverImg(),
                GroupType.WEEKLY,
                everyGroupVo.groupTitle(),
                everyGroupVo.weekDate(),
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
                GroupType.ONCE,
                onceGroupVo.groupTitle(),
                WeekDate.fromDayOfWeek(onceGroupVo.dateTime().getDayOfWeek()),
                onceGroupVo.dateTime(),
                onceGroupVo.startTime(),
                onceGroupVo.endTime(),
                onceGroupVo.location(),
                onceGroupVo.createdAt()
        );
    }
}
