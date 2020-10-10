package com.wimpy.util;

import org.tinylog.Logger;

import java.util.function.Supplier;

public class Retry {

  public static Object run(int retries, Supplier function) {
    try {
      return function.get();
    } catch (Exception e) {
      return retry(retries, function);
    }
  }


  private static Object retry(int maxRetries, Supplier function) throws RuntimeException {
    int retryCounter = 0;
    while (retryCounter < maxRetries) {
      try {
        return function.get();
      } catch (Exception ex) {
        retryCounter++;
        Logger.debug("Command failed on retry {} of {} [error={}]", retryCounter, maxRetries, ex);
        if (retryCounter >= maxRetries) {
          Logger.debug("Max retries exceeded.");
          break;
        }
      }
    }
    throw new RuntimeException("Command failed on all of " + maxRetries + " retries");
  }
}
