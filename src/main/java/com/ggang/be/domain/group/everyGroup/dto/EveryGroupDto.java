package com.ggang.be.domain.group.everyGroup.dto;

import com.ggang.be.domain.constant.Category;
import com.ggang.be.domain.constant.Status;
import com.ggang.be.domain.group.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.timslot.gongbaekTimeSlot.GongbaekTimeSlotEntity;
import com.ggang.be.domain.user.UserEntity;

public record EveryGroupDto(
        long groupId,
        Status status,
        String title,
        String location,
        int currentPeopleCount,
        int maxPeopleCount,
        boolean isHost,
        boolean isApply,
        String introduction,
        Category category,
        int coverImg,
        GongbaekTimeSlotEntity gongbaekTimeSlotEntity
) {
    public static EveryGroupDto toDto(EveryGroupEntity entity, UserEntity currentUser) {
        return new EveryGroupDto(
                entity.getId(),
                entity.getStatus(),
                entity.getTitle(),
                entity.getLocation(),
                entity.getCurrentPeopleCount(),
                entity.getMaxPeopleCount(),
                entity.isHost(currentUser),
                entity.isApply(currentUser),
                entity.getIntroduction(),
                entity.getCategory(),
                entity.getCoverImg(),
                entity.getGongbaekTimeSlotEntity()
                );
    }

}
