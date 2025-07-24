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
    
    // 이메일 중복 검사를 위한 효율적인 쿼리 - EXISTS 사용으로 성능 최적화
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM user u WHERE u.email = :email")
    boolean existsByEmail(String email);
    
    // 더 효율적인 네이티브 EXISTS 쿼리 (옵션)
    @Query(value = "SELECT EXISTS(SELECT 1 FROM user WHERE email = :email)", nativeQuery = true)
    boolean existsByEmailNative(String email);
}
