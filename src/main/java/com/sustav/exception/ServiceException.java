package com.sustav.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ServiceException extends RuntimeException {

  private final String code;
  private final String message;

  public static ServiceException from(ErrorCode errorCode) {
    return new ServiceException(errorCode.name(), errorCode.getMessage());
  }

}
