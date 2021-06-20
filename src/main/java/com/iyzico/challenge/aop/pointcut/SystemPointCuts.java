package com.iyzico.challenge.aop.pointcut;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class SystemPointCuts {

    @Pointcut("@annotation(com.iyzico.challenge.aop.annotation.ControllerLogging)")
    public void logController() {
    }
}
