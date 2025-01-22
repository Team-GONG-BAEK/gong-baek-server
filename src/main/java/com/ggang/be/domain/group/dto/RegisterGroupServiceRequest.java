package com.ggang.be.domain.group.dto;

import com.ggang.be.domain.constant.Category;
import com.ggang.be.domain.constant.GroupType;
import com.ggang.be.domain.constant.WeekDay;
import com.ggang.be.domain.user.UserEntity;

import java.time.LocalDate;

public record RegisterGroupServiceRequest(
    UserEntity userEntity,
    GroupType groupType,
    LocalDate weekDate,
    WeekDay weekDay,
    double startTime,
    double endTime,
    Category category,
    int coverImg,
    String location,
    int maxPeopleCount,
    String groupTitle,
    String introduction
) { }
