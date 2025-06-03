package com.ggang.be.api.email.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Scheduler;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthCodeCacheServiceTest {

    @Test
    void scheduler없으면_캐시가_자동_만료되지_않을_수_있다() throws InterruptedException {
        Cache<String, String> cache = Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofSeconds(5))
                .maximumSize(100)
                .recordStats()
                .build();

        cache.put("gongbaek@school.com", "123456");
        System.out.println("저장 직후 estimatedSize: " + cache.estimatedSize());

        Thread.sleep(6000);

        String result = cache.getIfPresent("gongbaek@school.com");
        System.out.println("6초 후 조회 결과 (scheduler 없음): " + result);
        System.out.println("evictionCount: " + cache.stats().evictionCount());
        System.out.println("estimatedSize: " + cache.estimatedSize());
        System.out.println("result: " + result);
    }

    @Test
    void scheduler있으면_시간이_지나면_자동으로_캐시가_지워진다() throws InterruptedException {
        Cache<String, String> cache = Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofSeconds(5))
                .maximumSize(100)
                .scheduler(Scheduler.systemScheduler()) // scheduler 적용
                .recordStats()
                .build();

        cache.put("gongbaek@school.com", "123456");
        System.out.println("저장 직후 estimatedSize: " + cache.estimatedSize());

        Thread.sleep(6000);

        String result = cache.getIfPresent("gongbaek@school.com");
        System.out.println("6초 후 조회 결과 (scheduler 없음): " + result);
        System.out.println("evictionCount: " + cache.stats().evictionCount());
        System.out.println("estimatedSize: " + cache.estimatedSize());

        assertThat(result).isNull();
    }

    @Test
    void scheduler적용_전후_메모리_사용량_차이_확인() throws InterruptedException {
        Runtime runtime = Runtime.getRuntime();

        // heap 사용량 측정 함수
        Runnable printMemory = () -> {
            long used = runtime.totalMemory() - runtime.freeMemory();
            System.out.println("Used memory: " + used / 1024 / 1024 + "MB");
        };

        Cache<String, String> cache = Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofSeconds(5))
                .maximumSize(100_000)
                .scheduler(Scheduler.systemScheduler())
                .build();

        for (int i = 0; i < 100_000; i++) {
            cache.put("gongbaek" + i + "@school.com", "code" + i);
        }

        System.out.println("1. 대량 저장 후 메모리");
        printMemory.run();

        Thread.sleep(6000);
        System.gc();
        Thread.sleep(1000); // GC 여유

        System.out.println("2. 만료 및 GC 이후 메모리");
        printMemory.run();

        System.out.println("estimatedSize: " + cache.estimatedSize());
    }
}
