package com.ggang.be.domain.userOnceGroup.infra;

import com.ggang.be.domain.userOnceGroup.UserOnceGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserOnceGroupRepository extends JpaRepository<UserOnceGroupEntity, Long> {
    List<UserOnceGroupEntity> findByUserEntity_id(Long id);
}
