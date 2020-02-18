package com.wimpy.aop.aspect;

import com.google.gson.Gson;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.tinylog.Logger;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class LogMethodAspect {

  public static void handle(JoinPoint pjp) {
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

    Logger.info(
        "Method details [name={};parameters={}]", method.getName(), new Gson().toJson(variables));
  }
}
