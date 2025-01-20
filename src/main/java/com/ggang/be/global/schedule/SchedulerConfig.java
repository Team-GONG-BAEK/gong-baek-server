package com.ggang.be.global.schedule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class SchedulerConfig {

    @Bean
    public Executor asyncEveryGroupUpdater() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setKeepAliveSeconds(30);
        executor.setThreadNamePrefix("egUpdater");
        executor.initialize();
        return executor;
    }

    @Bean
    public Executor asyncOnceGroupUpdater() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setKeepAliveSeconds(30);
        executor.setThreadNamePrefix("ogUpdater");
        executor.initialize();
        return executor;
    }

}
