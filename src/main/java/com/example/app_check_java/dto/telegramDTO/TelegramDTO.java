package com.example.app_check_java.dto.telegramDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TelegramDTO {

    private UserTelegramDTO user;

    private String message; //сообщение для кнопок в телеграм

    private int level; //уровень таблицы 1-category, 2-topic, 3-question, 4-answer.

    private int category;
    private int topic;
    private int question;

    private List<String> list = new ArrayList<>();

    private Map<Long, String> map;

}
