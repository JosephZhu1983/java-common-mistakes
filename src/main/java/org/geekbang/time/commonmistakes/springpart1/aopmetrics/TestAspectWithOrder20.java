package org.geekbang.time.commonmistakes.springpart1.aopmetrics;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(20)
@Slf4j
public class TestAspectWithOrder20 {

    @Before("execution(* org.geekbang.time.commonmistakes.springpart1.aopmetrics.TestController.*(..))")
    public void before(JoinPoint joinPoint) throws Throwable {
        log.info("TestAspectWithOrder20 @Before");
    }

    @After("execution(* org.geekbang.time.commonmistakes.springpart1.aopmetrics.TestController.*(..))")
    public void after(JoinPoint joinPoint) throws Throwable {
        log.info("TestAspectWithOrder20 @After");
    }

    @Around("execution(* org.geekbang.time.commonmistakes.springpart1.aopmetrics.TestController.*(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        log.info("TestAspectWithOrder20 @Around before");
        Object o = pjp.proceed();
        log.info("TestAspectWithOrder20 @Around after");
        return o;
    }
}

