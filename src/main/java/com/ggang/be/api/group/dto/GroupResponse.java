package com.ggang.be.api.group.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ggang.be.domain.constant.Category;
import com.ggang.be.domain.constant.WeekDay;

public record GroupResponse(
        long groupId,
        String groupType,
        String groupTitle,
        String location,
        @JsonProperty("isHost") boolean isHost,
        @JsonProperty("isApply") boolean isApply,
        int currentPeopleCount,
        int maxPeopleCount,
        String introduction,
        Category category,
        int coverImg,
        WeekDay weekDay,
        String weekDate,
        double startTime,
        double endTime
) {}