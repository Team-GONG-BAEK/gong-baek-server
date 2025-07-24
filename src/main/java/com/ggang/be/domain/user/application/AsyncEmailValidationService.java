package com.ggang.be.domain.user.application;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.email.service.AppProperties;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.domain.user.infra.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class AsyncEmailValidationService {
    
    private final UserRepository userRepository;
    private final AppProperties appProperties;
    
    /**
     * 비동기로 이메일 중복 검사 실행
     */
    @Async("emailValidationExecutor")
    @Cacheable(value = "emailDuplicateCheck", key = "#email", unless = "#result.get() == false")
    public CompletableFuture<Boolean> checkEmailDuplicateAsync(String email) {
        try {
            if (isAdminMail(email)) {
                return CompletableFuture.completedFuture(false);
            }
            
            boolean exists = userRepository.existsByEmail(email);
            log.debug("Async email duplicate check for {}: {}", email, exists);
            return CompletableFuture.completedFuture(exists);
            
        } catch (Exception e) {
            log.error("Error during async email duplicate check for {}", email, e);
            return CompletableFuture.failedFuture(e);
        }
    }
    
    /**
     * 배치로 여러 이메일 중복 검사 실행
     */
    @Async("emailValidationExecutor")
    public CompletableFuture<Set<String>> checkEmailsBatchAsync(Set<String> emails) {
        try {
            // 관리자 이메일 필터링
            Set<String> filteredEmails = emails.stream()
                    .filter(email -> !isAdminMail(email))
                    .collect(java.util.stream.Collectors.toSet());
            
            Set<String> existingEmails = userRepository.findExistingEmails(filteredEmails);
            log.debug("Batch email duplicate check for {} emails, {} duplicates found", 
                     emails.size(), existingEmails.size());
            
            return CompletableFuture.completedFuture(existingEmails);
            
        } catch (Exception e) {
            log.error("Error during batch email duplicate check", e);
            return CompletableFuture.failedFuture(e);
        }
    }
    
    /**
     * 관리자 이메일 여부 확인
     */
    private boolean isAdminMail(String email) {
        return email.equals(appProperties.getIosReviewEmail()) || 
               email.equals(appProperties.getAndReviewEmail());
    }
    
    /**
     * 동기식 중복 검사 (기존 API 호환성 유지)
     */
    public void checkDuplicatedEmailSync(String email) {
        if (isAdminMail(email)) {
            return;
        }
        
        if (userRepository.existsByEmail(email)) {
            log.debug("Email duplicate check failed for: {}", email);
            throw new GongBaekException(ResponseError.USERNAME_ALREADY_EXISTS);
        }
    }
}