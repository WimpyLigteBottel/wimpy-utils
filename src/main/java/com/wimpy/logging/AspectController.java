package com.wimpy.logging;

import com.google.gson.Gson;
import com.wimpy.logging.annotations.Timing;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Aspect
@Configuration
public class AspectController {

  private static final Logger logger = LoggerFactory.getLogger(AspectController.class);

  /*
     Methods with this annotation will be timed
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

  @Around("@annotation(com.wimpy.logging.annotations.HandelUnknownExceptions)")
  public Object handelUnknownExceptions(ProceedingJoinPoint pjp) {

    Method method = ((MethodSignature) pjp.getSignature()).getMethod();
    Parameter[] parameters = method.getParameters();

    try {
      return pjp.proceed();
    } catch (Throwable e) {
      logger.error("Exception was not handled [type={};message={}]", e.getClass(), e.getMessage());
      logMethodParameters(pjp);
    }

    // If result needs to be return rather return null (dont want to crash)
    return null;
  }

  final class ParameterType implements Serializable {
    private final String variableName;
    private final Object value;
    private final String type;

    ParameterType(String variableName, String type, Object value) {
      this.variableName = variableName;
      this.type = type;
      this.value = value;
    }
  }

  @Before("@annotation(com.wimpy.logging.annotations.LogMethodParameters)")
  public void logMethodParameters(JoinPoint pjp) {

    Method method = ((MethodSignature) pjp.getSignature()).getMethod();

    List<ParameterType> variables = new ArrayList<>();
    Object[] args = pjp.getArgs();

    for (int i = 0; i < method.getParameters().length; i++) {
      ParameterType parameter =
          new ParameterType(
              method.getParameters()[i].getName(),
              method.getParameters()[i].getType().toString(),
              args[i]);

      variables.add(parameter);
    }

    logger.info(
        "Method details [name={};parameters={}]", method.getName(), new Gson().toJson(variables));
  }
}
