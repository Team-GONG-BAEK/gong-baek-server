package com.ggang.be.domain.userEveryGroup.infra;


import com.ggang.be.domain.group.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.domain.userEveryGroup.UserEveryGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

public interface UserEveryGroupRepository extends JpaRepository<UserEveryGroupEntity, Long> {
    List<UserEveryGroupEntity> findUserEveryGroupEntityByEveryGroupEntity(EveryGroupEntity entity);

    List<UserEveryGroupEntity> findByUserEntity_id(Long id);

    @Query("select u from user_every_group u "
        + " join fetch u.userEntity"
        + " join fetch u.everyGroupEntity"
        + " where u.userEntity = :userEntity")
    List<UserEveryGroupEntity> findAllByUserEntity(UserEntity findUserEntity);

    Optional<UserEveryGroupEntity> findByUserEntityAndEveryGroupEntity(UserEntity userEntity, EveryGroupEntity everyGroupEntity);
}
