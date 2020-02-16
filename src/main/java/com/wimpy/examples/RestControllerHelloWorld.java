package com.wimpy.examples;

import com.wimpy.logging.annotations.HandelUnknownExceptions;
import com.wimpy.logging.annotations.Timing;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestControllerHelloWorld {

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
}
