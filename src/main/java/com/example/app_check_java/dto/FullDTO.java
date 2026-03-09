package com.example.app_check_java.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FullDTO {

    @NotBlank(message = "Поле category не может быть пустым")
    private String category;

    @NotBlank(message = "Поле topic не может быть пустым")
    private String topic;

    @NotBlank(message = "поле question не может быть пустым")
    private String question;

    @NotBlank(message = "поле answer не может быть пустым")
    private String answer;
}
