package com.kk.validation.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * EmailSendindLogging
 */
@Aspect
@Component
public class EmailSendindLogging {
    private static final Logger logger = LoggerFactory.getLogger(EmailSendindLogging.class);

    @Pointcut("@annotation(ToSlf4j)")
    public void onEmailSending() {
    }

    @Before("onEmailSending()")
    public void beforeSendEmail(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(a -> a.toString())
                .collect(Collectors.joining(","));
        logger.info("before send email: " + jp.toString() + ", args=[" + args + "]");
    }

    @After("onEmailSending()")
    public void afterSendEmail(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(a -> a.toString())
                .collect(Collectors.joining(","));
        logger.info("after send email: " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterThrowing(pointcut = "onEmailSending()", throwing = "ex")
    public void sendEmailException(JoinPoint jp, Exception ex) {
        String args = Arrays.stream(jp.getArgs())
                .map(a -> a.toString())
                .collect(Collectors.joining(","));
        logger.error("email sending exception occured: " +
                "Exception: " + ex.getMessage() + ";" +
                jp.toString() + ", args=[" + args + "]");
    }
}
