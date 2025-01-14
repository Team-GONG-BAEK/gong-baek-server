package com.ggang.be.domain.group.everyGroup.infra;

import com.ggang.be.domain.group.everyGroup.EveryGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EveryGroupRepository extends JpaRepository<EveryGroupEntity, Long> {
    List<EveryGroupEntity> findByUserEntity_Id(Long userEntityUserId);

}