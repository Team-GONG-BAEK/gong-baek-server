package com.ggang.be.domain.everyGroup.infra;

import com.ggang.be.domain.everyGroup.EveryGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EveryGroupRepository extends JpaRepository<EveryGroupEntity, Long> {

}