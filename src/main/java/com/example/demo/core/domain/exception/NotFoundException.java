package com.example.demo.core.domain.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String entity, String key) {
        super(
                String.format("%s with the key %s not found", entity, key)
        );
    }
}
