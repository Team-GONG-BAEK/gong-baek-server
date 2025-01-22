package com.ggang.be.domain.group.everyGroup.dto;

import com.ggang.be.domain.constant.Category;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.constant.Status;
import com.ggang.be.domain.constant.WeekDay;
import com.ggang.be.domain.group.everyGroup.EveryGroupEntity;

import java.time.LocalDateTime;

public record EveryGroupVo(long groupId, Status status, Category category, int coverImg, int profileImg,
                           String nickname, GroupType groupType,
                           String groupTitle, WeekDay weekDay, double startTime, double endTime, String location, LocalDateTime createdAt) {
    public static EveryGroupVo of(EveryGroupEntity everyGroupEntity) {
        return new EveryGroupVo(
                everyGroupEntity.getId(),
                everyGroupEntity.getStatus(),
                everyGroupEntity.getCategory(),
                everyGroupEntity.getCoverImg(),
                everyGroupEntity.getUserEntity().getProfileImg(),
                everyGroupEntity.getUserEntity().getNickname(),
                GroupType.WEEKLY,
                everyGroupEntity.getTitle(),
                everyGroupEntity.getGongbaekTimeSlotEntity().getWeekDay(),
                everyGroupEntity.getGongbaekTimeSlotEntity().getStartTime(),
                everyGroupEntity.getGongbaekTimeSlotEntity().getEndTime(),
                everyGroupEntity.getLocation(),
                everyGroupEntity.getCreatedAt()
        );
    }
}