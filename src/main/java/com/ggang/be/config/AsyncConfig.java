package com.ggang.be.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Slf4j
@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "emailValidationExecutor")
    public Executor emailValidationExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        
        // 기본 스레드 수
        executor.setCorePoolSize(5);
        // 최대 스레드 수
        executor.setMaxPoolSize(10);
        // 큐 용량
        executor.setQueueCapacity(100);
        // 스레드 이름 접두사
        executor.setThreadNamePrefix("EmailValidation-");
        // 스레드 풀 종료 시 작업 완료 대기
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 종료 대기 시간 (초)
        executor.setAwaitTerminationSeconds(30);
        
        // 거부된 작업 처리 정책: CallerRunsPolicy (호출 스레드에서 실행)
        executor.setRejectedExecutionHandler((runnable, threadPoolExecutor) -> {
            log.warn("Email validation task rejected, executing in caller thread");
            runnable.run();
        });
        
        executor.initialize();
        return executor;
    }
}