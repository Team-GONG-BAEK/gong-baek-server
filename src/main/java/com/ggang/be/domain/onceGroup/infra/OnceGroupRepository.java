package com.ggang.be.domain.onceGroup.infra;

import com.ggang.be.domain.onceGroup.OnceGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OnceGroupRepository extends JpaRepository<OnceGroupEntity, Long> {

}