package com.ggang.be.api.common;

import com.ggang.be.domain.constant.WeekDate;
import lombok.Getter;

@Getter
public class GroupResponse {
    private long groupId;
    private String groupType;
    private String title;
    private String location;
    private boolean status;
    private int currentPeopleCount;
    private int maxPeopleCount;
    private String introduction;
    private String category;
    private String weekDate;
    private String weekDay;
    private double startTime;
    private double endTime;

    public GroupResponse(
            long groupId,
            String groupType,
            String title,
            String location,
            boolean status,
            int currentPeopleCount,
            int maxPeopleCount,
            String introduction,
            String category,
            WeekDate weekDay,
            String weekDate,
            double startTime,
            double endTime
    ) {
        this.groupId = groupId;
        this.groupType = groupType;
        this.title = title;
        this.location = location;
        this.status = status;
        this.currentPeopleCount = currentPeopleCount;
        this.maxPeopleCount = maxPeopleCount;
        this.introduction = introduction;
        this.category = category;
        this.weekDay = weekDay.toString();
        this.weekDate = weekDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
