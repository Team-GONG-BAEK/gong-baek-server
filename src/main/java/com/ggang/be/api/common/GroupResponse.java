package com.ggang.be.api.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ggang.be.domain.constant.WeekDate;
import lombok.Getter;

@Getter
public class GroupResponse {
    private long groupId;
    private String groupType;
    private String groupTitle;
    private String location;
    private boolean status;

    @JsonProperty("isHost")
    private boolean isHost;

    @JsonProperty("isApply")
    private boolean isApply;

    private int currentPeopleCount;
    private int maxPeopleCount;
    private String introduction;
    private String category;
    private int coverImg;
    private String weekDay;
    private String weekDate;
    private double startTime;
    private double endTime;

    public GroupResponse(
            long groupId,
            String groupType,
            String groupTitle,
            String location,
            boolean status,
            boolean isHost,
            boolean isApply,
            int currentPeopleCount,
            int maxPeopleCount,
            String introduction,
            String category,
            int coverImg,
            WeekDate weekDay,
            String weekDate,
            double startTime,
            double endTime
    ) {
        this.groupId = groupId;
        this.groupType = groupType;
        this.groupTitle = groupTitle;
        this.location = location;
        this.status = status;
        this.isHost = isHost;
        this.isApply = isApply;
        this.currentPeopleCount = currentPeopleCount;
        this.maxPeopleCount = maxPeopleCount;
        this.introduction = introduction;
        this.category = category;
        this.coverImg = coverImg;
        this.weekDay = weekDay != null ? weekDay.toString() : null;
        this.weekDate = weekDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @JsonProperty("isHost")
    public boolean getIsHost() {
        return isHost;
    }

    @JsonProperty("isApply")
    public boolean getIsApply() {
        return isApply;
    }
}
