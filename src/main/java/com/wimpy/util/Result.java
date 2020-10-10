package com.wimpy.util;

import java.util.Optional;

class Result {
  private boolean success;
  private Optional<Object> value;
  private boolean rollbackSuccess;

  public Result(boolean success, Optional<Object> value) {
    this(success, value, true);
  }

  public Result(boolean success, Optional<Object> value, boolean rollbackSuccess) {
    this.success = success;
    this.value = value;
    this.rollbackSuccess = rollbackSuccess;
  }

  public boolean isSuccess() {
    return success;
  }

  public Optional<Object> getValue() {
    return value;
  }

  public boolean isRollbackSuccess() {
    return rollbackSuccess;
  }
}
