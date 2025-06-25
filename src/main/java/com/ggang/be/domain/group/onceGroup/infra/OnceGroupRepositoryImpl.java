package com.ggang.be.domain.group.onceGroup.infra;

import com.ggang.be.domain.constant.Category;
import com.ggang.be.domain.constant.WeekDay;
import com.ggang.be.domain.group.onceGroup.OnceGroupEntity;
import com.ggang.be.domain.group.onceGroup.QOnceGroupEntity;
import com.ggang.be.domain.timslot.gongbaekTimeSlot.QGongbaekTimeSlotEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class OnceGroupRepositoryImpl implements OnceGroupRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<OnceGroupEntity> findGroupsByCategoryAndDay(Category category, WeekDay weekDay) {
        QOnceGroupEntity onceGroup = QOnceGroupEntity.onceGroupEntity;
        QGongbaekTimeSlotEntity timeSlot = QGongbaekTimeSlotEntity.gongbaekTimeSlotEntity;

        BooleanBuilder builder = new BooleanBuilder();

        if (category != null) {
            builder.and(onceGroup.category.eq(category));
        }
        if (weekDay != null) {
            builder.and(onceGroup.gongbaekTimeSlotEntity.weekDay.eq(weekDay));
        }

        return queryFactory
                .selectFrom(onceGroup)
                .join(onceGroup.gongbaekTimeSlotEntity, timeSlot).fetchJoin()
                .where(builder)
                .fetch();
    }
}
