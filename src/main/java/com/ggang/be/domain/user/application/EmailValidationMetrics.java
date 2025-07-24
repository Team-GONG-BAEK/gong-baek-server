package com.ggang.be.domain.user.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Component
public class EmailValidationMetrics {
    
    private final AtomicLong totalValidations = new AtomicLong(0);
    private final AtomicLong cacheHits = new AtomicLong(0);
    private final AtomicLong cacheMisses = new AtomicLong(0);
    private final AtomicLong duplicateEmails = new AtomicLong(0);
    private final AtomicLong totalValidationTime = new AtomicLong(0);
    
    public void recordValidation(long durationMs, boolean cacheHit, boolean isDuplicate) {
        totalValidations.incrementAndGet();
        totalValidationTime.addAndGet(durationMs);
        
        if (cacheHit) {
            cacheHits.incrementAndGet();
        } else {
            cacheMisses.incrementAndGet();
        }
        
        if (isDuplicate) {
            duplicateEmails.incrementAndGet();
        }
        
        // 100건마다 통계 로그 출력
        if (totalValidations.get() % 100 == 0) {
            logStatistics();
        }
    }
    
    public void logStatistics() {
        long total = totalValidations.get();
        long hits = cacheHits.get();
        long misses = cacheMisses.get();
        long duplicates = duplicateEmails.get();
        long avgTime = total > 0 ? totalValidationTime.get() / total : 0;
        
        double hitRate = total > 0 ? (double) hits / total * 100 : 0;
        double duplicateRate = total > 0 ? (double) duplicates / total * 100 : 0;
        
        log.info("Email Validation Metrics - Total: {}, Cache Hit Rate: {:.2f}%, " +
                "Duplicate Rate: {:.2f}%, Avg Time: {}ms", 
                total, hitRate, duplicateRate, avgTime);
    }
    
    public long measureExecutionTime(Runnable operation) {
        LocalDateTime start = LocalDateTime.now();
        try {
            operation.run();
        } finally {
            return ChronoUnit.MILLIS.between(start, LocalDateTime.now());
        }
    }
    
    // Getter methods for external monitoring
    public long getTotalValidations() { return totalValidations.get(); }
    public long getCacheHits() { return cacheHits.get(); }
    public long getCacheMisses() { return cacheMisses.get(); }
    public long getDuplicateEmails() { return duplicateEmails.get(); }
    public long getAverageValidationTime() { 
        long total = totalValidations.get();
        return total > 0 ? totalValidationTime.get() / total : 0; 
    }
}