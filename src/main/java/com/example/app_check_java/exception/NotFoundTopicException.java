package com.example.app_check_java.exception;

public class NotFoundTopicException extends RuntimeException {
    public NotFoundTopicException(String message) {
        super(message);
    }
}
