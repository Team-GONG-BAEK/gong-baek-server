package com.ggang.be.domain.group.onceGroup.infra;

import com.ggang.be.domain.constant.Status;
import com.ggang.be.domain.group.onceGroup.OnceGroupEntity;
import com.ggang.be.domain.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface OnceGroupRepository extends JpaRepository<OnceGroupEntity, Long> {
    List<OnceGroupEntity> findByUserEntity_Id(Long userEntityUserId);

    List<OnceGroupEntity> findByParticipantUsers_UserEntity_Id(Long userId);


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

}