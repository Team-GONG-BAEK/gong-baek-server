package com.ggang.be.domain.group.onceGroup.dto;

import com.ggang.be.api.facade.GroupType;
import com.ggang.be.domain.constant.Category;
import com.ggang.be.domain.constant.Status;
import com.ggang.be.domain.group.onceGroup.OnceGroupEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record OnceGroupVo(long groupId, Status status, Category category, int coverImg, GroupType groupType,
                          String groupTitle, LocalDate dateTime, double startTime, double endTime, String location,
                          LocalDateTime createdAt) {
    public static OnceGroupVo of(OnceGroupEntity onceGroupEntity) {
        return new OnceGroupVo(
                onceGroupEntity.getId(),
                onceGroupEntity.getStatus(),
                onceGroupEntity.getCategory(),
                onceGroupEntity.getCoverImg(),
                GroupType.ONCE,
                onceGroupEntity.getTitle(),
                onceGroupEntity.getGroupDate(),
                onceGroupEntity.getGongbaekTimeSlotEntity().getStartTime(),
                onceGroupEntity.getGongbaekTimeSlotEntity().getEndTime(),
                onceGroupEntity.getLocation(),
                onceGroupEntity.getCreatedAt()
        );
    }
}