package com.example.app_check_java.dto.dbDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class QuestionDTO {
    @NotBlank(message = "Тема не может быть пустой")
    private String questionName;
}
