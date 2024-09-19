package com.example.demo.auth.infrastructure.spring;

import com.example.demo.core.domain.exception.BadRequestException;
import com.example.demo.core.domain.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AuthControllerExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    ResponseEntity<?> badRequestExceptionException(BadRequestException e) {
        return ResponseEntity.badRequest().build();
    }
}

