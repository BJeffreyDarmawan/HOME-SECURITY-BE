package com.enrico.dg.home.security.libraries.exception;

import java.util.List;

public class InvalidPasswordException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public InvalidPasswordException(String code, String message, List<String> errors) {
    this.code = code;
    this.message = message;
    this.errors = errors;
  }

  private String code;
  private String message;
  private List<String> errors;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public List<String> getErrors() {
    return errors;
  }

  public void setErrors(List<String> errors) {
    this.errors = errors;
  }

  @Override
  public String toString() {
    return "InvalidPasswordException{" +
        "code='" + code + '\'' +
        ", message='" + message + '\'' +
        ", errors=" + errors +
        '}';
  }
}
