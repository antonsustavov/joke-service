package com.sustav.exception;

import org.springframework.http.HttpStatus;

public record ExceptionRule(Class<?> exceptionClass, HttpStatus status){}

