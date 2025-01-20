package com.ggang.be.global.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Aspect
@Component
public class LoggingAop {

    @Before("@within(org.springframework.web.bind.annotation.RestController)")
    public void methodCall(JoinPoint joinPoint) {
        HttpServletRequest request =
            ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        log.info("Start this Api request  {} : {}", request.getMethod(),
            request.getServletPath());
        log.info("Entering method : {} ", joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "@within(org.springframework.web.bind.annotation.RestController)")
    public void logMethodExit() {
        HttpServletRequest request =
            ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        log.info("End this Api request  {} : {}", request.getMethod(), request.getServletPath());
    }

}
