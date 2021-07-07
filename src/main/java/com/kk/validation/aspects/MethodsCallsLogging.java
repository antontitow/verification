package com.kk.validation.aspects;

import com.kk.validation.service.ValidationEmail;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
public class MethodsCallsLogging {
    private static final Logger logger = LoggerFactory.getLogger(ValidationEmail.class);

    @Pointcut("execution(* com.kk.validation.controller.*.*(..))")
    public void onRestMethodsCall() {
    }

    @Before("onRestMethodsCall()")
    public void beforeCallsRestMethods(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(a -> a.toString())
                .collect(Collectors.joining(","));
        logger.info("before call restmethod:" + jp.toString() + ", args=[" + args + "]");
    }

    @After("onRestMethodsCall()")
    public void afterCallsRestMethods(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(a -> a.toString())
                .collect(Collectors.joining(","));
        logger.info("after call restmethod:" + jp.toString() + ", args=[" + args + "]");
    }
}
