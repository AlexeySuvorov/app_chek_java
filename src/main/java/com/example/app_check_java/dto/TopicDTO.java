package com.example.app_check_java.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TopicDTO {

    @NotBlank(message = "категория не может быть пустой")
    private String topicName;
}
