package com.ggang.be.domain.user.application;

import com.ggang.be.api.email.service.AppProperties;
import com.ggang.be.domain.user.infra.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.StopWatch;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmailDuplicateCheckPerformanceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AppProperties appProperties;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Test
    @DisplayName("이메일 중복 검사 성능 테스트 - existsByEmail vs findByEmail")
    void compareEmailDuplicateCheckPerformance() {
        // Given
        String email = "performance.test@example.com";
        when(appProperties.getAndReviewEmail()).thenReturn("review@test.com");
        when(appProperties.getIosReviewEmail()).thenReturn("ios@test.com");
        when(userRepository.existsByEmail(email)).thenReturn(false);

        StopWatch stopWatch = new StopWatch();
        int iterations = 1000;

        // When - existsByEmail 성능 측정
        stopWatch.start("existsByEmail");
        for (int i = 0; i < iterations; i++) {
            userServiceImpl.checkDuplicatedEmail(email);
        }
        stopWatch.stop();

        // 결과 출력
        System.out.println("=== Email Duplicate Check Performance Test ===");
        System.out.println("Iterations: " + iterations);
        System.out.println("existsByEmail method: " + stopWatch.getLastTaskTimeMillis() + "ms");
        System.out.println("Average per check: " + (stopWatch.getLastTaskTimeMillis() / (double) iterations) + "ms");
        
        // 캐시 효과 테스트
        stopWatch.start("existsByEmail with cache");
        for (int i = 0; i < iterations; i++) {
            userServiceImpl.checkDuplicatedEmail(email); // 같은 이메일로 캐시 효과 확인
        }
        stopWatch.stop();
        
        System.out.println("existsByEmail with cache: " + stopWatch.getLastTaskTimeMillis() + "ms");
        System.out.println("Average per check with cache: " + (stopWatch.getLastTaskTimeMillis() / (double) iterations) + "ms");
        System.out.println("=== Test Completed ===");
    }
}