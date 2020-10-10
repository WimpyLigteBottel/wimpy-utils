package com.wimpy.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.tinylog.Logger;

public class HandleUnknownAspect {

  public static Object handle(ProceedingJoinPoint pjp) {

    try {
      return pjp.proceed();
    } catch (Throwable e) {
      Logger.error("Exception was not handled [type={};message={}]", e.getClass(), e.getMessage());
      LogMethodAspect.handleParameters(pjp);
    }

    // If result needs to be return rather return null (dont want to crash)
    return null;
  }
}
