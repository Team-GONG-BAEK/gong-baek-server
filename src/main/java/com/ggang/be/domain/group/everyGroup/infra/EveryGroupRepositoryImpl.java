package com.ggang.be.domain.group.everyGroup.infra;

import com.ggang.be.domain.constant.Category;
import com.ggang.be.domain.constant.Status;
import com.ggang.be.domain.constant.WeekDay;

import com.ggang.be.domain.group.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.group.everyGroup.QEveryGroupEntity;
import com.ggang.be.domain.timslot.gongbaekTimeSlot.QGongbaekTimeSlotEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class EveryGroupRepositoryImpl implements EveryGroupRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<EveryGroupEntity> findGroupsByCategoryAndDay(Category category, WeekDay weekDay) {
        QEveryGroupEntity everyGroup = QEveryGroupEntity.everyGroupEntity;
        QGongbaekTimeSlotEntity timeSlot = QGongbaekTimeSlotEntity.gongbaekTimeSlotEntity;

        BooleanBuilder builder = new BooleanBuilder();

        if (category != null) {
            builder.and(everyGroup.category.eq(category));
        }
        if (weekDay != null) {
            builder.and(everyGroup.gongbaekTimeSlotEntity.weekDay.eq(weekDay));
        }

        return queryFactory
                .selectFrom(everyGroup)
                .join(everyGroup.gongbaekTimeSlotEntity, timeSlot).fetchJoin()
                .where(builder)
                .fetch();
    }
}
