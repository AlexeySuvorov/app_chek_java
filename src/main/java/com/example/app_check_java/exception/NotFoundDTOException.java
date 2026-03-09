package com.example.app_check_java.exception;

public class NotFoundDTOException extends RuntimeException {
    public NotFoundDTOException(String message) {
        super(message);
    }
}
