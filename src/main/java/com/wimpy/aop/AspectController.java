package com.wimpy.aop;

import com.wimpy.aop.aspect.HandleUnknownAspect;
import com.wimpy.aop.aspect.LogMethodAspect;
import com.wimpy.aop.aspect.TimingAspect;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;

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

  @Before("@annotation(com.wimpy.aop.annotations.LogMethodParameters)")
  public void logMethodParameters(JoinPoint pjp) {
    LogMethodAspect.handle(pjp);
  }
}
