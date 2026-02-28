package com.example.app_check_java.exception;

import com.example.app_check_java.utill.ResponseError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(NotFoundCategoryException.class) //перехватывает исключение NotFoundCategoryException в контроллере
    @ResponseStatus(HttpStatus.NOT_FOUND) //устанавливает HTTP-статус ответа, который будет отправлен клиенту.
    public ResponseError handleNotFoundCategoryException(NotFoundCategoryException e) {
        log.error("Категория не найдена: " + e.getMessage());
        return new ResponseError(HttpStatus.NOT_FOUND, e.getMessage());
    }
}
