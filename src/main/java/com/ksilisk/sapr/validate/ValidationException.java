package com.ksilisk.sapr.validate;

public class ValidationException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Invalid data";

    public ValidationException() {
        super(DEFAULT_MESSAGE);
    }

    public ValidationException(String message) {
        super(message);
    }
}
