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

    boolean existsUserEntitiesByNickname(String name);

    UserEntity findByPlatformAndPlatformId(Platform platform, String platformId);

    Optional<UserEntity> findByEmail(String email);
    
    // 이메일 중복 검사를 위한 효율적인 메서드 (존재 여부만 확인)
    boolean existsByEmail(String email);
    
    // 배치 이메일 중복 검사를 위한 메서드
    @Query("SELECT u.email FROM user u WHERE u.email IN :emails")
    Set<String> findExistingEmails(@Param("emails") Set<String> emails);
}
