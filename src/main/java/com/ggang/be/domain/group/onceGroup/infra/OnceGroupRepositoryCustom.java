package com.ggang.be.domain.group.onceGroup.infra;

import com.ggang.be.domain.constant.Category;
import com.ggang.be.domain.constant.WeekDay;
import com.ggang.be.domain.group.onceGroup.OnceGroupEntity;

import java.util.List;

public interface OnceGroupRepositoryCustom {
    List<OnceGroupEntity> findGroupsByCategoryAndDay(Category category, WeekDay day);
}
