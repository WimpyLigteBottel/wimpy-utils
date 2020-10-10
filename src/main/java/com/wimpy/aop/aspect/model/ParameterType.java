package com.wimpy.aop.aspect.model;

public class ParameterType {

  private final String variableName;
  private final Object value;
  private final String type;

  public ParameterType(String variableName, String type, Object value) {
    this.variableName = variableName;
    this.type = type;
    this.value = value;
  }

  public String getVariableName() {
    return variableName;
  }

  public Object getValue() {
    return value;
  }

  public String getType() {
    return type;
  }
}
