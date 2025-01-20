package com.ggang.be.api.group.dto;

import com.ggang.be.domain.constant.Category;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.constant.Status;
import com.ggang.be.domain.constant.WeekDate;
import com.ggang.be.domain.group.dto.GroupVo;

import java.time.LocalDate;

public record MyGroupResponse(
        long groupId, Status status, Category category, int coverImg, GroupType groupType,
        String groupTitle, WeekDate weekDay, LocalDate weekDate, double startTime, double endTime, String location
) {
    public static MyGroupResponse fromGroupVo(GroupVo groupVo) {
        return new MyGroupResponse(
                groupVo.groupId(),
                groupVo.status(),
                groupVo.category(),
                groupVo.coverImg(),
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
