package com.ggang.be.api.group.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ggang.be.domain.constant.Category;
import com.ggang.be.domain.constant.Status;
import com.ggang.be.domain.constant.WeekDate;

public record GroupResponse(
        long groupId,
        String groupType,
        String groupTitle,
        String location,
        Status status,
        @JsonProperty("isHost") boolean isHost,
        @JsonProperty("isApply") boolean isApply,
        int currentPeopleCount,
        int maxPeopleCount,
        String introduction,
        Category category,
        int coverImg,
        WeekDate weekDay,
        String weekDate,
        double startTime,
        double endTime
) {}