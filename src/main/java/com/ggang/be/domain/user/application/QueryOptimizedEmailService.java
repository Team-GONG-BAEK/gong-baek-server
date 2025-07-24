package com.ggang.be.domain.user.application;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.email.service.AppProperties;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.domain.user.infra.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * 쿼리 튜닝에 특화된 이메일 검증 서비스
 * 인덱스 없이도 최적의 성능을 달성하기 위한 다양한 쿼리 최적화 기법 적용
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QueryOptimizedEmailService {
    
    private final UserRepository userRepository;
    private final AppProperties appProperties;
    private final JdbcTemplate jdbcTemplate;
    private final EmailValidationMetrics emailValidationMetrics;

    /**
     * Level 1: 기본 JPA 최적화
     * EXISTS 쿼리로 첫 번째 매치에서 즉시 반환
     */
    public boolean isEmailDuplicatedBasic(String email) {
        if (isAdminMail(email)) {
            return false;
        }
        return userRepository.existsByEmailFast(email);
    }

    /**
     * Level 2: JDBC Template을 활용한 Raw SQL 최적화
     * JPA 오버헤드 제거하고 순수 SQL로 성능 극대화
     */
    public boolean isEmailDuplicatedRaw(String email) {
        if (isAdminMail(email)) {
            return false;
        }
        
        long startTime = System.nanoTime();
        try {
            // MySQL 최적화된 쿼리: LIMIT 1로 첫 번째 매치에서 즉시 종료
            String sql = "SELECT EXISTS(SELECT 1 FROM user WHERE email = ? LIMIT 1)";
            Boolean result = jdbcTemplate.queryForObject(sql, Boolean.class, email);
            return Boolean.TRUE.equals(result);
        } finally {
            long duration = (System.nanoTime() - startTime) / 1_000_000; // ms 변환
            emailValidationMetrics.recordValidation(duration, false, false);
            log.debug("Raw SQL email check took {}ms for email: {}", duration, email);
        }
    }

    /**
     * Level 3: 비동기 배치 처리로 여러 이메일 동시 검증
     * 대량 처리 시 효율성 극대화
     */
    public CompletableFuture<Set<String>> findDuplicateEmailsAsync(Set<String> emails) {
        return CompletableFuture.supplyAsync(() -> {
            long startTime = System.nanoTime();
            try {
                // 관리자 이메일 필터링
                Set<String> filteredEmails = emails.stream()
                        .filter(email -> !isAdminMail(email))
                        .collect(java.util.stream.Collectors.toSet());
                
                if (filteredEmails.isEmpty()) {
                    return Set.of();
                }
                
                // IN 절을 사용한 배치 쿼리 - 한 번의 DB 호출로 여러 이메일 검사
                return userRepository.findExistingEmails(filteredEmails);
            } finally {
                long duration = (System.nanoTime() - startTime) / 1_000_000;
                log.debug("Batch email check took {}ms for {} emails", duration, emails.size());
            }
        });
    }

    /**
     * Level 4: 메모리 효율적인 스트리밍 방식
     * 대용량 데이터 처리 시 메모리 사용량 최소화
     */
    public boolean isEmailDuplicatedStreaming(String email) {
        if (isAdminMail(email)) {
            return false;
        }
        
        // 결과를 즉시 소비하여 메모리 사용량 최소화
        String sql = "SELECT 1 FROM user WHERE email = ? LIMIT 1";
        return jdbcTemplate.query(sql, 
            (ResultSetExtractor<Boolean>) rs -> rs.next(), // 첫 번째 row가 있으면 true
            email);
    }

    /**
     * Level 5: 쿼리 힌트를 활용한 DB 엔진 레벨 최적화
     * (MySQL 5.7+에서 지원)
     */
    public boolean isEmailDuplicatedWithHints(String email) {
        if (isAdminMail(email)) {
            return false;
        }
        
        // 쿼리 힌트로 옵티마이저에게 최적화 방향 제시
        String sql = """
            SELECT /*+ USE_INDEX_FOR_ORDER_BY(user PRIMARY) */ 
            EXISTS(
                SELECT 1 FROM user 
                WHERE email = ? 
                LIMIT 1
            )
            """;
        
        Boolean result = jdbcTemplate.queryForObject(sql, Boolean.class, email);
        return Boolean.TRUE.equals(result);
    }

    /**
     * 관리자 이메일 체크 (기존 로직 유지)
     */
    private boolean isAdminMail(String email) {
        return email.equals(appProperties.getAndReviewEmail()) || 
               email.equals(appProperties.getIosReviewEmail());
    }

    /**
     * 성능 비교를 위한 벤치마크 메서드
     */
    public void benchmarkEmailCheck(String email, int iterations) {
        log.info("Starting email check benchmark for {} iterations", iterations);
        
        // 기본 JPA 방식
        long jpaStart = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            userRepository.existsByEmail(email);
        }
        long jpaDuration = (System.nanoTime() - jpaStart) / 1_000_000;
        
        // 최적화된 JPA 방식
        long optimizedStart = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            userRepository.existsByEmailFast(email);
        }
        long optimizedDuration = (System.nanoTime() - optimizedStart) / 1_000_000;
        
        // Raw SQL 방식
        long rawStart = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            isEmailDuplicatedRaw(email);
        }
        long rawDuration = (System.nanoTime() - rawStart) / 1_000_000;
        
        log.info("Benchmark Results ({} iterations):", iterations);
        log.info("- Basic JPA: {}ms (avg: {}ms)", jpaDuration, jpaDuration / iterations);
        log.info("- Optimized JPA: {}ms (avg: {}ms)", optimizedDuration, optimizedDuration / iterations);
        log.info("- Raw SQL: {}ms (avg: {}ms)", rawDuration, rawDuration / iterations);
        log.info("- Performance improvement: {}%", 
                ((double)(jpaDuration - rawDuration) / jpaDuration) * 100);
    }
}