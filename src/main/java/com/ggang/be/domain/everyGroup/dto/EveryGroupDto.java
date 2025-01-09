package com.ggang.be.domain.everyGroup.dto;

import com.ggang.be.domain.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.user.UserEntity;

public record EveryGroupDto(
        long groupId,
        String title,
        String location,
        boolean status,
        int currentPeopleCount,
        int maxPeopleCount,
        boolean isHost,
        boolean isApply,
        String introduction,
        String category,
        int coverImg,
        String weekDay,
        double startTime,
        double endTime
) {
    public static EveryGroupDto toDto(EveryGroupEntity entity, UserEntity currentUser) {
        return new EveryGroupDto(
                entity.getId(),
                entity.getTitle(),
                entity.getLocation(),
                entity.getStatus().isActive(),
                entity.getCurrentPeopleCount(),
                entity.getMaxPeopleCount(),
                entity.isHost(currentUser),
                entity.isApply(currentUser),
                entity.getIntroduction(),
                entity.getCategory().toString(),
                entity.getCoverImg(),
                entity.getWeekDate().toString(),
                entity.getStartTime(),
                entity.getEndTime()
                );
    }

}
