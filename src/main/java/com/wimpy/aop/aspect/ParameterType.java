package com.wimpy.aop.aspect;

public class ParameterType {

    private final String variableName;
    private final Object value;
    private final String type;

    ParameterType(String variableName, String type, Object value) {
        this.variableName = variableName;
        this.type = type;
        this.value = value;
    }
}
