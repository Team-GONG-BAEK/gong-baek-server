package com.ggang.be.api.group.dto;

import com.ggang.be.domain.constant.Category;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.constant.WeekDate;
import com.ggang.be.domain.group.dto.GroupVo;

import java.time.LocalDate;

public record ActiveGroupsResponse(
        long groupId,
        Category category,
        int coverImg,
        int profileImg,
        GroupType groupType,
        String groupTitle,
        WeekDate weekDate,
        LocalDate gropuDate,
        double startTime,
        double endTime,
        String location
) {
    public static ActiveGroupsResponse fromGroupVo(GroupVo groupVo) {
        return new ActiveGroupsResponse(
                groupVo.groupId(),
                groupVo.category(),
                groupVo.coverImg(),
                groupVo.profileImg(),
                groupVo.groupType(),
                groupVo.groupTitle(),
                groupVo.weekDate(),
                groupVo.groupDate(),
                groupVo.startTime(),
                groupVo.endTime(),
                groupVo.location()
        );
    }
}
