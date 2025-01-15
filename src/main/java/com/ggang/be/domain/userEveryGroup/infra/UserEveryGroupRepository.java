package com.ggang.be.domain.userEveryGroup.infra;


import com.ggang.be.domain.group.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.domain.userEveryGroup.UserEveryGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserEveryGroupRepository extends JpaRepository<UserEveryGroupEntity, Long> {
    List<UserEveryGroupEntity> findUserEveryGroupEntityByEveryGroupEntity(EveryGroupEntity entity);

    List<UserEveryGroupEntity> findByUserEntity_id(Long id);

    List<UserEveryGroupEntity> findAllByUserEntity(UserEntity findUserEntity);
}
