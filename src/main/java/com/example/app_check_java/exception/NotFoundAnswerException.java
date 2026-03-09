package com.example.app_check_java.exception;

public class NotFoundAnswerException extends RuntimeException {
    public NotFoundAnswerException(String message) {
        super(message);
    }
}
