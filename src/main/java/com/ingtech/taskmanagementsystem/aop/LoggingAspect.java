package com.ingtech.taskmanagementsystem.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Aspect
public class LoggingAspect {


    @AfterThrowing(value = "execution(* com.ingtech.taskmanagementsystem.service.*.*(..))",
            throwing = "ex")
    public void logMethodException(JoinPoint jp, Throwable ex) {
        log.error(
                "Exception in {}.{}() : {}",
                jp.getSignature().getDeclaringTypeName(),   // Class Name
                jp.getSignature().getName(),                // Method Name
                ex.getMessage(),                            // Exception Message
                ex                                          // Exception type, Stack trace
        );
    }

    @AfterReturning("execution(* com.ingtech.taskmanagementsystem.service.*.*(..))")
    public void logMethodSuccess(JoinPoint jp) {
        log.info("Method execution success {}.{}()",
                jp.getSignature().getDeclaringTypeName(),
                jp.getSignature().getName());
    }


}
