package com.example.app_check_java.dto.dbDTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AnswerDTO {
    @NotNull(message = "поле answerId не может быть пустым")
    private Long questionId;

    private String answerName;
}
