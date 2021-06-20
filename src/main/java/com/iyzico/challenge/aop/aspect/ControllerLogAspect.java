package com.iyzico.challenge.aop.aspect;

import com.iyzico.challenge.aop.model.ControllerLog;
import com.iyzico.challenge.util.ExceptionUtil;
import com.iyzico.challenge.util.JsonUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.time.LocalDateTime;

@Aspect
@Component
public class ControllerLogAspect {

    private Logger logger = LoggerFactory.getLogger(ControllerLogAspect.class);

    private ControllerLog controllerLog;
    private JsonUtil jsonUtil;

    public ControllerLogAspect(JsonUtil jsonUtil) {
        this.controllerLog = new ControllerLog();
        this.jsonUtil = jsonUtil;
    }

    @Before("com.iyzico.challenge.aop.pointcut.SystemPointCuts.logController()")
    public void before(JoinPoint joinPoint) {
        try {
            Annotation[][] annotationMatrix = ((MethodSignature) joinPoint.getSignature()).getMethod().getParameterAnnotations();
            controllerLog.setRequest(getArgs(joinPoint.getArgs(), annotationMatrix));
            controllerLog.setSignature(joinPoint.getSignature().toString());
            controllerLog.setStartTime(LocalDateTime.now());
        } catch (Exception e) {
            logger.error(ExceptionUtil.getErrorString(e));
        }
    }

    @AfterReturning(pointcut = "com.iyzico.challenge.aop.pointcut.SystemPointCuts.logController()", returning = "result")
    public void afterReturning(Object result) {
        try {
            controllerLog.setEndTime(LocalDateTime.now());
            controllerLog.setResponse(result);
            logger.info(jsonUtil.convertToJson(controllerLog));
        } catch (Exception e) {
            logger.error(ExceptionUtil.getErrorString(e));
        }
    }

    @AfterThrowing(value = "com.iyzico.challenge.aop.pointcut.SystemPointCuts.logController()", throwing = "exception")
    public void afterThrowing(Exception exception) {
        try {
            controllerLog.setEndTime(LocalDateTime.now());
            controllerLog.setException(exception.getMessage());
            logger.info(jsonUtil.convertToJson(controllerLog));
        } catch (Exception e) {
            logger.error(ExceptionUtil.getErrorString(e));
        }
    }
    
    private Object getArgs(Object[] args, Annotation[][] annotationMatrix) {
        for (int i = 0; i < args.length; i++) {
            boolean hasLoggableAnnotation = false;
            for (Annotation annotation : annotationMatrix[i]) {
                if (annotation.annotationType().getPackage().getName().startsWith("org.springframework.web.bind.annotation.RequestBody")) {
                    hasLoggableAnnotation = true;
                    break;
                }
            }
            if (!hasLoggableAnnotation)
                return args[i];
        }
        return null;
    }
}
