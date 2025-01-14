package com.ggang.be.domain.userOnceGroup.infra;

import com.ggang.be.domain.group.onceGroup.OnceGroupEntity;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.domain.userOnceGroup.UserOnceGroupEntity;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserOnceGroupRepository extends JpaRepository<UserOnceGroupEntity, Long> {

    List<UserOnceGroupEntity> findUserOnceGroupEntityByOnceGroupEntity(OnceGroupEntity entity);
    List<UserOnceGroupEntity> findByUserEntity_id(Long id);

    List<UserOnceGroupEntity> findAllByUserEntity(UserEntity userEntity);
}
