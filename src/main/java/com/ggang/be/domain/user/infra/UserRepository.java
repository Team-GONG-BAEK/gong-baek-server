package com.ggang.be.domain.user.infra;

import com.ggang.be.domain.constant.Platform;
import com.ggang.be.domain.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findById(Long id);

    @Query("SELECT u.nickname FROM user u WHERE u.id = :id")
    Optional<String> findNicknameById(Long id);

    @Query("SELECT s.schoolName FROM user u JOIN u.school s WHERE u.id = :userId")
    Optional<String> findSchoolNameById(Long userId);

    boolean existsUserEntitiesByNickname(String nickname);

    UserEntity findByPlatformAndPlatformId(Platform platform, String platformId);

    Optional<UserEntity> findByEmail(String email);
    
    /**
     * 최적화된 이메일 중복 검사 - LIMIT 1을 사용하여 첫 번째 매치에서 즉시 중단
     * 인덱스 없이도 효율적인 검사 가능
     */
    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM user WHERE email = :email LIMIT 1", nativeQuery = true)
    boolean existsByEmailOptimized(@Param("email") String email);
    
    /**
     * 더욱 최적화된 버전 - EXISTS 절 사용으로 첫 번째 매치에서 즉시 반환
     */
    @Query(value = "SELECT EXISTS(SELECT 1 FROM user WHERE email = :email LIMIT 1)", nativeQuery = true)
    boolean existsByEmailFast(@Param("email") String email);
    
    // 기존 메서드도 유지 (호환성)
    boolean existsByEmail(String email);
    
    // 배치 이메일 중복 검사를 위한 메서드
    @Query("SELECT u.email FROM user u WHERE u.email IN :emails")
    Set<String> findExistingEmails(@Param("emails") Set<String> emails);
}
