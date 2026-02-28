package com.example.app_check_java.utill;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class ResponseError {

    private int code;
    private String message;
    private LocalDateTime timestamp;

    public ResponseError(HttpStatus code, String message) {
        this.code = code.value();
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

}
