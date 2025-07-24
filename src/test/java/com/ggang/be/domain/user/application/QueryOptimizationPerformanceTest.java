package com.ggang.be.domain.user.application;

import com.ggang.be.api.email.service.AppProperties;
import com.ggang.be.domain.user.infra.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StopWatch;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class QueryOptimizationPerformanceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AppProperties appProperties;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private EmailValidationMetrics emailValidationMetrics;

    @InjectMocks
    private QueryOptimizedEmailService queryOptimizedEmailService;

    @Test
    @DisplayName("쿼리 최적화 성능 비교 테스트")
    void compareQueryOptimizationPerformance() {
        // Given
        String testEmail = "test@example.com";
        when(appProperties.getAndReviewEmail()).thenReturn("admin@test.com");
        when(appProperties.getIosReviewEmail()).thenReturn("ios@test.com");
        
        // 각 메서드별 다른 결과 설정
        when(userRepository.existsByEmail(testEmail)).thenReturn(false);
        when(userRepository.existsByEmailFast(testEmail)).thenReturn(false);
        when(jdbcTemplate.queryForObject(anyString(), eq(Boolean.class), eq(testEmail))).thenReturn(false);

        StopWatch stopWatch = new StopWatch();
        
        // When & Then
        stopWatch.start("Basic JPA Method");
        boolean basicResult = queryOptimizedEmailService.isEmailDuplicatedBasic(testEmail);
        stopWatch.stop();
        
        stopWatch.start("Raw SQL Method");
        boolean rawResult = queryOptimizedEmailService.isEmailDuplicatedRaw(testEmail);
        stopWatch.stop();
        
        // 결과 검증
        assertThat(basicResult).isFalse();
        assertThat(rawResult).isFalse();
        
        // 성능 로그 출력
        System.out.println("=== 쿼리 최적화 성능 비교 ===");
        System.out.println(stopWatch.prettyPrint());
        
        // 각 메서드가 호출되었는지 검증
        verify(userRepository).existsByEmailFast(testEmail);
        verify(jdbcTemplate).queryForObject(anyString(), eq(Boolean.class), eq(testEmail));
    }

    @Test
    @DisplayName("관리자 이메일 제외 테스트")
    void shouldExcludeAdminEmails() {
        // Given
        String adminEmail = "admin@test.com";
        when(appProperties.getAndReviewEmail()).thenReturn(adminEmail);
        when(appProperties.getIosReviewEmail()).thenReturn("ios@test.com");

        // When
        boolean result = queryOptimizedEmailService.isEmailDuplicatedBasic(adminEmail);

        // Then
        assertThat(result).isFalse();
        
        // 관리자 이메일은 DB 조회하지 않음을 검증
        verify(userRepository, never()).existsByEmailFast(adminEmail);
    }

    @Test
    @DisplayName("쿼리 최적화 벤치마크 시뮬레이션")
    void simulateBenchmark() {
        // Given
        String testEmail = "benchmark@test.com";
        when(appProperties.getAndReviewEmail()).thenReturn("admin@test.com");
        when(appProperties.getIosReviewEmail()).thenReturn("ios@test.com");
        when(userRepository.existsByEmail(testEmail)).thenReturn(false);
        when(userRepository.existsByEmailFast(testEmail)).thenReturn(false);
        when(jdbcTemplate.queryForObject(anyString(), eq(Boolean.class), eq(testEmail))).thenReturn(false);

        // When
        queryOptimizedEmailService.benchmarkEmailCheck(testEmail, 100);

        // Then
        verify(userRepository, times(100)).existsByEmail(testEmail);
        verify(userRepository, times(100)).existsByEmailFast(testEmail);
        verify(jdbcTemplate, times(100)).queryForObject(anyString(), eq(Boolean.class), eq(testEmail));
    }

    @Test
    @DisplayName("스트리밍 방식 이메일 중복 검사 테스트")
    void testStreamingEmailCheck() {
        // Given
        String testEmail = "streaming@test.com";
        when(appProperties.getAndReviewEmail()).thenReturn("admin@test.com");
        when(appProperties.getIosReviewEmail()).thenReturn("ios@test.com");
        
        // JdbcTemplate query 메서드가 false를 반환하도록 설정 (ResultSetExtractor 사용)
        when(jdbcTemplate.query(anyString(), any(org.springframework.jdbc.core.ResultSetExtractor.class), eq(testEmail)))
                .thenReturn(false);

        // When
        boolean result = queryOptimizedEmailService.isEmailDuplicatedStreaming(testEmail);

        // Then
        assertThat(result).isFalse(); // false 그대로
        verify(jdbcTemplate).query(anyString(), any(org.springframework.jdbc.core.ResultSetExtractor.class), eq(testEmail));
    }
}