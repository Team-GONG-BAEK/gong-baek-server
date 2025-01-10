package com.ggang.be.api.group.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GroupResponse(
        long groupId,
        String groupType,
        String groupTitle,
        String location,
        String status,
        @JsonProperty("isHost") boolean isHost,
        @JsonProperty("isApply") boolean isApply,
        int currentPeopleCount,
        int maxPeopleCount,
        String introduction,
        String category,
        int coverImg,
        String weekDay,
        String weekDate,
        double startTime,
        double endTime
) {}
