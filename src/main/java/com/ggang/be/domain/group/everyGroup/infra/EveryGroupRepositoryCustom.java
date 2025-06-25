package com.ggang.be.domain.group.everyGroup.infra;

import com.ggang.be.domain.constant.Category;
import com.ggang.be.domain.constant.WeekDay;
import com.ggang.be.domain.group.everyGroup.EveryGroupEntity;

import java.util.List;

public interface EveryGroupRepositoryCustom {
    List<EveryGroupEntity> findGroupsByCategoryAndDay(Category category, WeekDay day);
}
