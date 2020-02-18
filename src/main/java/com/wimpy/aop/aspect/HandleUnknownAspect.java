package com.wimpy.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.tinylog.Logger;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class HandleUnknownAspect {

  public static Object handle(ProceedingJoinPoint pjp) {
    Method method = ((MethodSignature) pjp.getSignature()).getMethod();
    Parameter[] parameters = method.getParameters();

    try {
      return pjp.proceed();
    } catch (Throwable e) {
      Logger.error("Exception was not handled [type={};message={}]", e.getClass(), e.getMessage());
      LogMethodAspect.handle(pjp);
    }

    // If result needs to be return rather return null (dont want to crash)
    return null;
  }
}
