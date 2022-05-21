package com.waracle.cakemanagerserver.controller.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Log method invocation.
 */
@Aspect
@Component
@Slf4j
public class MethodLoggingAspect {

    /**
     * Show logging for method invocation.
     *
     * @param joinPoint joint point
     * @return method return
     * @throws Throwable exception from method
     */
    @Around("@annotation(MethodLogging)")
    public final Object logging(final ProceedingJoinPoint joinPoint) throws Throwable {

        final var start = LocalDateTime.now();
        final String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        final String methodName = joinPoint.getSignature().getName();

        log.info("{} - class: {}, method: {}", Thread.currentThread().getId(), className, methodName);

        Object proceed;
        try {
            proceed = joinPoint.proceed();
        } catch (final Throwable throwable) {
            log.info("{} - class: {}, method: {}, exception: {}", Thread.currentThread().getId(), className, methodName, throwable.getMessage());
            throw throwable;
        }

        final var end = LocalDateTime.now();
        final long diff = ChronoUnit.MILLIS.between(start, end);
        log.info("{} - class: {}, method: {}, duration: {} ms", Thread.currentThread().getId(), className, methodName, diff);

        return proceed;
    }

}