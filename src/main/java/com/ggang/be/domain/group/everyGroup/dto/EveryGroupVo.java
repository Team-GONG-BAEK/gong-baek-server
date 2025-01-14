package com.ggang.be.domain.group.everyGroup.dto;

import com.ggang.be.api.facade.GroupType;
import com.ggang.be.domain.constant.Category;
import com.ggang.be.domain.constant.Status;
import com.ggang.be.domain.constant.WeekDate;
import com.ggang.be.domain.group.everyGroup.EveryGroupEntity;

import java.time.LocalDateTime;

public record EveryGroupVo(long groupId, Status status, Category category, int coverImg, GroupType groupType,
                           String groupTitle, WeekDate weekDate, double startTime, double endTime, String location, LocalDateTime createdAt) {
    public static EveryGroupVo of(EveryGroupEntity everyGroupEntity) {
        return new EveryGroupVo(
                everyGroupEntity.getId(),
                everyGroupEntity.getStatus(),
                everyGroupEntity.getCategory(),
                everyGroupEntity.getCoverImg(),
                GroupType.WEEKLY,
                everyGroupEntity.getTitle(),
                everyGroupEntity.getWeekDate(),
                everyGroupEntity.getStartTime(),
                everyGroupEntity.getEndTime(),
                everyGroupEntity.getLocation(),
                everyGroupEntity.getCreatedAt()
        );
    }
}