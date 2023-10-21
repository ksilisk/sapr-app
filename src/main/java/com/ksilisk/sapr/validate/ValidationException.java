package com.ksilisk.sapr.validate;

public class ValidationException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Constructor parameters is invalid";

    public ValidationException() {
        super(DEFAULT_MESSAGE);
    }

    public ValidationException(String message) {
        super(message);
    }
}
