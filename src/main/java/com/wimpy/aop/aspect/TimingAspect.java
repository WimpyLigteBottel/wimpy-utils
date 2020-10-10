package com.wimpy.aop.aspect;

import com.wimpy.aop.annotations.Timing;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.tinylog.Logger;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.Instant;

public class TimingAspect {

  /*
  Logs out the specified <TimeUnit.java> of how long method took to complete
   */

  public static Object handle(ProceedingJoinPoint pjp) {
    MethodSignature signature = (MethodSignature) pjp.getSignature();
    Method method = signature.getMethod();

    Instant before = Instant.now();
    Object answer = null;
    try {
      answer = pjp.proceed();
    } catch (Throwable throwable) {
      Logger.error(throwable.getMessage());
    }

    Object time;
    String unit;

    switch (method.getAnnotation(Timing.class).loggingType()) {
      default:
      case MILLISECONDS:
        time = Duration.between(before, Instant.now()).toMillis();
        unit = "ms";
        break;
      case SECONDS:
        time = Duration.between(before, Instant.now()).getSeconds();
        unit = "s";
        break;
      case NANOSECONDS:
        time = Duration.between(before, Instant.now()).getNano();
        unit = "ns";
        break;
    }

    Logger.debug("Method name={},time={}{}", method.getName(), time, unit);
    return answer;
  }
}
