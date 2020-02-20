package com.wimpy.examples;

import java.time.LocalDateTime;

public class SampleClass {

    String name;
    String value;
    LocalDateTime now;

    public SampleClass(String name, String value, LocalDateTime now) {
        this.name = name;
        this.value = value;
        this.now = now;
    }

    @Override
    public String toString() {
        return "SampleClass{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", now=" + now +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LocalDateTime getNow() {
        return now;
    }

    public void setNow(LocalDateTime now) {
        this.now = now;
    }
}
