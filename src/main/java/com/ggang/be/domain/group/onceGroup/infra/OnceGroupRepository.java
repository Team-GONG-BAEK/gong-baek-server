package com.ggang.be.domain.group.onceGroup.infra;

import com.ggang.be.domain.group.onceGroup.OnceGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OnceGroupRepository extends JpaRepository<OnceGroupEntity, Long> {
    List<OnceGroupEntity> findByUserEntity_Id(Long userEntityUserId);

}