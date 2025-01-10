package com.ggang.be.domain.user.infra;

import com.ggang.be.domain.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsUserEntitiesByNickname(String name);
    Optional<UserEntity> findById(Long id);
}
