package com.sustav.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

  COUNT_EXCEED("За один раз можно получить не более 100 шуток."),
  EXTERNAL_SERVER_ERROR("Ошибка внешнего сервиса"),
  SOMETHING_WENT_WRONG("Что-то пошло не так");

  private final String message;

}
