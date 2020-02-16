package com.wimpy.logging;

import com.wimpy.logging.annotations.Timing;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.Instant;

@Aspect
@Configuration
public class AspectController {

  private static final Logger logger = LoggerFactory.getLogger(AspectController.class);

  /*
      Methods with this annotation will be timing
   */

  @Around("@annotation(com.wimpy.logging.annotations.Timing)")
  public Object timingMethod(ProceedingJoinPoint pjp) {

    MethodSignature signature = (MethodSignature) pjp.getSignature();
    Method method = signature.getMethod();

    Instant before = Instant.now();
    Object answer = null;
    try {
      answer = pjp.proceed();
    } catch (Throwable throwable) {
      logger.error(throwable.getMessage());
    }

    Object time = null;
    String unit = "";

    switch (method.getAnnotation(Timing.class).loggingType()) {
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

    logger.info("Method name={},time={}{}", method.getName(), time, unit);
    return answer;
  }
}
