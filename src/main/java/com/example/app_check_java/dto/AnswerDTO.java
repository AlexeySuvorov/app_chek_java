package com.example.app_check_java.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AnswerDTO {
    @NotBlank(message = "поле answerId не может быть пустым")
    private Long answerId;

    private String answerName;
}
