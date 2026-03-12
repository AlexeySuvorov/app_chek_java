package com.example.app_check_java.dto.telegramDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TelegramDTO {

    private UserTelegramDTO user;

    @NotBlank
    private String message;

    private int level; //уровень таблицы 1-category, 2-topic, 3-question, 4-answer.

    private int category;
    private int topic;
    private int question;

    private List<String> list = new ArrayList<>();

    private Map<String, String> map = new HashMap<>();

}
