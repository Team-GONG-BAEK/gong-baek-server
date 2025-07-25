package com.ggang.be.domain.user.infra;

import com.ggang.be.domain.constant.Platform;
import com.ggang.be.domain.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findById(Long id);

    @Query("SELECT u.nickname FROM user u WHERE u.id = :id")
    Optional<String> findNicknameById(Long id);

    @Query("SELECT s.schoolName FROM user u JOIN u.school s WHERE u.id = :userId")
    Optional<String> findSchoolNameById(Long userId);

    boolean existsUserEntitiesByNickname(String name);

    UserEntity findByPlatformAndPlatformId(Platform platform, String platformId);

    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);
}
