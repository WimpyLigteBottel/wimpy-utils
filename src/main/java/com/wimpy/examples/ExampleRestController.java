package com.wimpy.examples;

import com.wimpy.aop.annotations.HandelUnknownExceptions;
import com.wimpy.aop.annotations.LogMethodParameters;
import com.wimpy.aop.annotations.Timing;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleRestController {

  @GetMapping("/timing")
  @Timing
  public void testingTimingOnMethod() {
    System.out.println("hello world");
  }

  @GetMapping("/exception")
  @HandelUnknownExceptions
  public void testExceptionHandeling() {
    throw new RuntimeException("I was asked to throw this");
  }

  @GetMapping("/methodLogging")
  @LogMethodParameters
  public void methodLogging(
      @RequestParam(required = false, defaultValue = "message") String first,
      @RequestParam(required = false, defaultValue = "1") Integer second,
      @RequestParam(required = false, defaultValue = "true") Boolean third) {}
}
