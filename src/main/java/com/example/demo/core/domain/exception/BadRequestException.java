package com.example.demo.core.domain.exception;

public class BadRequestException extends IllegalArgumentException {
    public BadRequestException(String message) { super(message); }
}
