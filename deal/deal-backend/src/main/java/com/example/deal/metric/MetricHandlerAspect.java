package com.example.deal.metric;

import com.example.deal.db.enums.ApplicationStatus;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@RequiredArgsConstructor
public class MetricHandlerAspect {

    private final MetricService metricService;

    @Around("@annotation(MetricMonitoring)")
    public Object sendMonitoringAction(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Arrays.stream(args)
                .filter(ApplicationStatus.class::isInstance)
                .map(ApplicationStatus.class::cast)
                .forEach(this::increment);

        return joinPoint.proceed();
    }

    public void increment(ApplicationStatus status) {
        metricService.incrementStatusCounter(status);
    }
}