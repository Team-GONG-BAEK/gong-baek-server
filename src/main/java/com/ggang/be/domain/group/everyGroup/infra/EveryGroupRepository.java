package com.ggang.be.domain.group.everyGroup.infra;

import com.ggang.be.domain.constant.Category;
import com.ggang.be.domain.constant.Status;
import com.ggang.be.domain.constant.WeekDay;
import com.ggang.be.domain.group.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.user.UserEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EveryGroupRepository extends JpaRepository<EveryGroupEntity, Long> {
    List<EveryGroupEntity> findByUserEntity_Id(Long userEntityUserId);

    @Query("SELECT CASE WHEN COUNT(o) > 0 THEN TRUE ELSE FALSE END " +
        "FROM every_group o " +
        "WHERE o.userEntity = :userEntity " +
        "AND o.gongbaekTimeSlotEntity.weekDay = :weekDay " +
        "AND o.status != :status " +
        "AND (" +
        "     (o.gongbaekTimeSlotEntity.startTime <= :startTime AND o.gongbaekTimeSlotEntity.endTime > :startTime) " +
        "  OR (o.gongbaekTimeSlotEntity.startTime < :endTime AND o.gongbaekTimeSlotEntity.endTime >= :endTime) " +
        "  OR (o.gongbaekTimeSlotEntity.startTime > :startTime AND o.gongbaekTimeSlotEntity.endTime < :endTime)" +
        ")")
    boolean isInTime(UserEntity userEntity, double startTime, double endTime,
       WeekDay weekDay, Status status);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select o from every_group o join fetch o.gongbaekTimeSlotEntity where o.status!=:status")
    List<EveryGroupEntity> findAllByNotStatus(Status status);

    List<EveryGroupEntity> findAllByCategory(Category category);
}