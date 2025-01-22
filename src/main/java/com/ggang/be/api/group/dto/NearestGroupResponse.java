package com.ggang.be.api.group.dto;

import com.ggang.be.domain.constant.Category;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.constant.WeekDay;

public record NearestGroupResponse(
        long groupId,
        Category category,
        GroupType groupType,
        String groupTitle,
        WeekDay weekDay,
        String weekDate,
        int currentPeopleCount,
        int maxPeopleCount,
        double startTime,
        double endTime,
        String location
) {
}
