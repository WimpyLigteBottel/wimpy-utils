package com.wimpy.aop.aspect;

import com.google.gson.Gson;
import com.wimpy.aop.aspect.model.ParameterType;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.tinylog.Logger;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LogMethodAspect {

  /*
  Logs out the method name and parameters
    */

  public static void handleParameters(JoinPoint pjp) {
    Method method = ((MethodSignature) pjp.getSignature()).getMethod();

    List<ParameterType> variables = new ArrayList<>();

    for (int i = 0; i < method.getParameters().length; i++) {
      variables.add(getParameter(pjp, method, i));
    }

    Logger.debug(
        "Method details [name={};parameters={}]", method.getName(), new Gson().toJson(variables));
  }

  /*
  Handles method response and logs out the methodName + response
   */

  public static Object handleResponse(ProceedingJoinPoint pjp) {

    MethodSignature signature = (MethodSignature) pjp.getSignature();
    Method method = signature.getMethod();
    Object proceed = null;
    try {
      proceed = pjp.proceed();
    } catch (Throwable throwable) {
      HandleUnknownAspect.handle(pjp);
    }

    String s = new Gson().toJson(proceed);
    String name = method.getName();
    Logger.debug("Method Response [methodName={};response={}]", name, s);

    return proceed;
  }

  private static ParameterType getParameter(JoinPoint pjp, Method method, int i) {
    Object arg = pjp.getArgs()[i];

    if (arg instanceof LocalDateTime) {
      arg = arg.toString();
    }

    return new ParameterType(
        method.getParameters()[i].getName(), method.getParameters()[i].getType().toString(), arg);
  }
}
