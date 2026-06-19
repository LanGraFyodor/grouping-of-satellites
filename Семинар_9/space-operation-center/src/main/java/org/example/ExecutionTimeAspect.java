package org.example;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Aspect
@Component
public class ExecutionTimeAspect {
    private final AtomicLong measuredInvocationCount = new AtomicLong();

    @Around("@annotation(org.example.MeasureExecutionTime) || " +
            "@within(org.example.MeasureExecutionTime)")
    public Object measure(ProceedingJoinPoint joinPoint) throws Throwable {
        long startedAt = System.nanoTime();
        try {
            return joinPoint.proceed();
        } finally {
            long elapsedNanos = System.nanoTime() - startedAt;
            measuredInvocationCount.incrementAndGet();
            System.out.printf(
                    "⏱ %s выполнен за %.3f мс%n",
                    joinPoint.getSignature().toShortString(),
                    elapsedNanos / 1_000_000.0
            );
        }
    }

    public long getMeasuredInvocationCount() {
        return measuredInvocationCount.get();
    }
}
