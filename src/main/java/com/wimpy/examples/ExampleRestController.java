package com.wimpy.examples;

import com.wimpy.aop.annotations.HandelUnknownExceptions;
import com.wimpy.aop.annotations.LogMethodParameters;
import com.wimpy.aop.annotations.LogResponse;
import com.wimpy.aop.annotations.Timing;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

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

  @GetMapping("/methodResponse")
  @LogResponse
  public ResponseEntity<SampleClass> methodResponse() {

    return ResponseEntity.ok(
        new SampleClass("randomName", String.valueOf(1020323), LocalDateTime.now()));
  }

  @GetMapping("/superLog")
  @LogResponse
  @LogMethodParameters
  public ResponseEntity<SampleClass> superLogging(
      @RequestParam(required = false, defaultValue = "message") String first,
      @RequestParam(required = false, defaultValue = "1") Integer second,
      @RequestParam(required = false, defaultValue = "true") Boolean third) {

    return ResponseEntity.ok(
        new SampleClass("randomName", String.valueOf(1020323), LocalDateTime.now()));
  }
}
