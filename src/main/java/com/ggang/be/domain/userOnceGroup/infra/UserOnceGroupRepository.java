package com.ggang.be.domain.userOnceGroup.infra;

import com.ggang.be.domain.group.onceGroup.OnceGroupEntity;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.domain.userOnceGroup.UserOnceGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

public interface UserOnceGroupRepository extends JpaRepository<UserOnceGroupEntity, Long> {

    List<UserOnceGroupEntity> findUserOnceGroupEntityByOnceGroupEntity(OnceGroupEntity entity);

    List<UserOnceGroupEntity> findByUserEntity_id(Long id);

    @Query("select u from user_once_group u "
        + " join fetch u.userEntity"
        + " join fetch u.onceGroupEntity"
        + " where u.userEntity = :userEntity")
    List<UserOnceGroupEntity> findAllByUserEntity(UserEntity userEntity);

    Optional<UserOnceGroupEntity> findByUserEntityAndOnceGroupEntity(UserEntity userEntity, OnceGroupEntity onceGroupEntity);
}
