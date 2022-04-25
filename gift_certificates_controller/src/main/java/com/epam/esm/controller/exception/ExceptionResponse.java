package com.epam.esm.controller.exception;

import java.util.Objects;

public class ExceptionResponse {
  private String errorMessage;
  private String errorCode;

  public ExceptionResponse() {}

  public ExceptionResponse(String errorMessage, String errorCode) {
    this.errorMessage = errorMessage;
    this.errorCode = errorCode;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ExceptionResponse that = (ExceptionResponse) o;
    return Objects.equals(errorMessage, that.errorMessage)
        && Objects.equals(errorCode, that.errorCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(errorMessage, errorCode);
  }

  @Override
  public String toString() {
    return "ExceptionResponse{"
        + "errorMessage='"
        + errorMessage
        + '\''
        + ", errorCode="
        + errorCode
        + '}';
  }
}
