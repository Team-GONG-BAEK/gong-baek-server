package com.ggang.be.domain.userOnceGroup.infra;

import com.ggang.be.domain.group.onceGroup.OnceGroupEntity;
import com.ggang.be.domain.userOnceGroup.UserOnceGroupEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserOnceGroupRepository extends JpaRepository<UserOnceGroupEntity, Long> {

    List<UserOnceGroupEntity> findUserOnceGroupEntityByOnceGroupEntity(OnceGroupEntity entity);
}
