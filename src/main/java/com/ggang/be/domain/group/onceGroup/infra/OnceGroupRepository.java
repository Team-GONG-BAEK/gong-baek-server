package com.ggang.be.domain.group.onceGroup.infra;

import com.ggang.be.domain.constant.Category;
import com.ggang.be.domain.constant.Status;
import com.ggang.be.domain.group.onceGroup.OnceGroupEntity;
import com.ggang.be.domain.user.UserEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface OnceGroupRepository extends JpaRepository<OnceGroupEntity, Long> {
    List<OnceGroupEntity> findByUserEntity_Id(Long userEntityUserId);

    @Query("SELECT CASE WHEN COUNT(o) > 0 THEN TRUE ELSE FALSE END " +
        "FROM once_group o " +
        "WHERE o.userEntity = :userEntity " +
        "AND o.groupDate = :groupDate " +
        "AND o.status != :status " +
        "AND (" +
        "     (o.gongbaekTimeSlotEntity.startTime <= :startTime AND o.gongbaekTimeSlotEntity.endTime > :startTime) " +
        "  OR (o.gongbaekTimeSlotEntity.startTime < :endTime AND o.gongbaekTimeSlotEntity.endTime >= :endTime) " +
        "  OR (o.gongbaekTimeSlotEntity.startTime > :startTime AND o.gongbaekTimeSlotEntity.endTime < :endTime)" +
        ")")
    boolean isInTime(UserEntity userEntity, double startTime, double endTime,
        LocalDate groupDate, Status status);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select o from once_group o join fetch o.gongbaekTimeSlotEntity where o.status!=:status")
    List<OnceGroupEntity> findAllByNotStatus(Status status);

    List<OnceGroupEntity> findAllByCategory(Category category);
}