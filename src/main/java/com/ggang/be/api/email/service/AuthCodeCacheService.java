package com.ggang.be.api.email.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Scheduler;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthCodeCacheService {

    private Cache<String, String> authCodeCache;
    private Cache<String, Boolean> emailVerifiedCache;
    private final EmailProperties emailProperties;

    @PostConstruct
    public void init() {
        long expirationMillis = emailProperties.getAuthCodeExpirationMillis();
        log.info("AuthCodeCache time: {}ms", expirationMillis);

        this.authCodeCache = Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofMillis(expirationMillis))
                .maximumSize(10000)
                .scheduler(Scheduler.systemScheduler())
                .recordStats()
                .build();
                
        // 이메일 검증 상태 캐시 (인증 코드와 동일한 TTL)
        this.emailVerifiedCache = Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofMillis(expirationMillis))
                .maximumSize(10000)
                .scheduler(Scheduler.systemScheduler())
                .recordStats()
                .build();
    }

    public void saveAuthCode(String email, String authCode) {
        authCodeCache.put(email, authCode);
    }

    public String getAuthCode(String email) {
        return authCodeCache.getIfPresent(email);
    }

    public boolean verifyCode(String email, String code) {
        String storedCode = authCodeCache.getIfPresent(email);
        return storedCode != null && storedCode.equals(code);
    }

    public void removeCode(String email) {
        authCodeCache.invalidate(email);
    }
    
    // 이메일 검증 상태 관리 메서드들
    public void saveEmailVerifiedStatus(String email, boolean isVerified) {
        emailVerifiedCache.put(email, isVerified);
        log.debug("Email verification status saved: {} -> {}", email, isVerified);
    }
    
    public Boolean isEmailVerified(String email) {
        Boolean verified = emailVerifiedCache.getIfPresent(email);
        log.debug("Email verification status checked: {} -> {}", email, verified);
        return verified;
    }
    
    public void removeEmailVerifiedStatus(String email) {
        emailVerifiedCache.invalidate(email);
        log.debug("Email verification status removed: {}", email);
    }
}
