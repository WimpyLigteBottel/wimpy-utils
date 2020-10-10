package com.wimpy.examples;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SampleClass {

  private String name;
  private String value;
  private String now;

  public SampleClass(String name, String value, LocalDateTime now) {
    this.name = name;
    this.value = value;
    this.now = now.format(DateTimeFormatter.ISO_DATE_TIME);
  }

  public String getName() {
    return name;
  }

  public String getValue() {
    return value;
  }

  public String getNow() {
    return now.toString();
  }
}
