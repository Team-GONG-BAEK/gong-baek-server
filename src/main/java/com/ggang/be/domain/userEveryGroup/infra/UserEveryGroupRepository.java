package com.ggang.be.domain.userEveryGroup.infra;


import com.ggang.be.domain.group.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.userEveryGroup.UserEveryGroupEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEveryGroupRepository extends JpaRepository<UserEveryGroupEntity, Long> {
    List<UserEveryGroupEntity> findUserEveryGroupEntityByEveryGroupEntity(EveryGroupEntity entity);
}
