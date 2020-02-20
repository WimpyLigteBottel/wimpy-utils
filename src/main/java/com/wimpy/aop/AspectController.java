package com.wimpy.aop;

import com.google.gson.Gson;
import com.wimpy.aop.aspect.HandleUnknownAspect;
import com.wimpy.aop.aspect.LogMethodAspect;
import com.wimpy.aop.aspect.TimingAspect;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.tinylog.Logger;

import java.lang.reflect.Method;

@Aspect
@Configuration
public class AspectController {

  /*
     Methods with this annotation will be timed
  */

  @Around("@annotation(com.wimpy.aop.annotations.Timing)")
  public Object timingMethod(ProceedingJoinPoint pjp) {
    return TimingAspect.handle(pjp);
  }

  @Around("@annotation(com.wimpy.aop.annotations.HandelUnknownExceptions)")
  public Object handelUnknownExceptions(ProceedingJoinPoint pjp) {
    return HandleUnknownAspect.handle(pjp);
  }

  @Around("@annotation(com.wimpy.aop.annotations.LogResponse)")
  public Object logMethodResponse(ProceedingJoinPoint pjp) {
    MethodSignature signature = (MethodSignature) pjp.getSignature();
    Method method = signature.getMethod();
    Object proceed = null;
    try {
      proceed = pjp.proceed();
    } catch (Throwable throwable) {
      handelUnknownExceptions(pjp);
    }

    Logger.debug(
        "Method Response [methodName={};response={}]",
        method.getName(),
        new Gson().toJson(proceed));

    return proceed;
  }

  @Before("@annotation(com.wimpy.aop.annotations.LogMethodParameters)")
  public void logMethodParameters(JoinPoint pjp) {
    LogMethodAspect.handle(pjp);
  }
}
