package com.deutsche.tradeStoreService.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("within(com.deutsche.tradeStoreService..*)")
    public void applicationPointcut(){

    }

    @Before("applicationPointcut()")
    public void logMethodEntry(JoinPoint joinPoint) {
        logger.debug("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning("applicationPointcut()")
    public void logMethodExit(JoinPoint joinPoint) {
        logger.debug("Exit: {}.{}() :SUCCESS", joinPoint.getSignature().getDeclaringTypeName(),
                                                                     joinPoint.getSignature().getName());
    }

    @AfterThrowing("applicationPointcut()")
    public void logMethodError(JoinPoint joinPoint) {
        logger.error("Exit: {}.{}() :ERROR", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName());
    }

}
