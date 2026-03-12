package com.example.app_check_java.service.telegramService;

import com.example.app_check_java.dto.telegramDTO.TelegramDTO;
import com.example.app_check_java.dto.telegramDTO.UserTelegramDTO;
import com.example.app_check_java.model.Topic;
import com.example.app_check_java.service.dbService.CategoryService;
import com.example.app_check_java.service.dbService.QuestionService;
import com.example.app_check_java.service.dbService.TopicService;
import com.example.app_check_java.service.telegramService.bord.InlineKeyboardServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetTelegramMessageService {

    private final CategoryService categoryService;
    private final TopicService topicService;
    private final QuestionService questionService;
    private final InlineKeyboardServiceImpl inlineKeyboardServiceImpl;
    private final SendTelegramMessageService sendTelegramMessageService;

    public void handlerTelegramMessage(Update update, String type) {
        log.info("Запущен метод - handlerTelegramMessage");
        log.info("type {}", type);
        if ("text".equals(type)) {
            log.info("Блок - text");
            UserTelegramDTO userTelegramDTO = UserTelegramDTO.builder()
                    .chatId(update.getMessage().getChatId())
                    .userName(update.getMessage().getFrom().getFirstName())
                    .lastName(update.getMessage().getFrom().getLastName())
                    .build();

            Map<String, String> newMap = categoryService.getAllCategories().stream()
                    .collect(Collectors.toMap(
                            category -> String.valueOf(category.getCategoryId()),
                            category -> category.getCategoryName()
                    ));

            TelegramDTO telegramDTO = TelegramDTO.builder()
                    .user(userTelegramDTO)
                    .map(newMap)
                    .level(1)
                    .build();
            sendTelegramMessageService.startKeybord(telegramDTO, 0);

        } else if ("callBackQuery".equals(type)) {
            log.info("блок -  callbackQuery");
            UserTelegramDTO userTelegramDTO = UserTelegramDTO.builder()
                    .chatId(update.getCallbackQuery().getMessage().getChatId())
                    .userName(update.getCallbackQuery().getFrom().getFirstName())
                    .lastName(update.getCallbackQuery().getFrom().getLastName())
                    .build();
            log.info("test update.getCallbackQuery().getData() - {}", update.getCallbackQuery().getData());
            String[] array = update.getCallbackQuery().getData().split("_");
            TelegramDTO telegramDTO = TelegramDTO.builder()
                    .user(userTelegramDTO)
                    .message(update.getCallbackQuery().getData())
                    .level(Integer.parseInt(array[0]))
                    .category(Integer.parseInt(array[1]))
                    .topic(Integer.parseInt(array[2]))
                    .question(Integer.parseInt(array[3]))
                    .build();
            switch(telegramDTO.getLevel()) {
                case 2 -> forTopic(telegramDTO);
                case 3 -> forQuestion(telegramDTO);
            }
            sendTelegramMessageService.startKeybord(telegramDTO, 0);
        }

    }

    private TelegramDTO forCategory(TelegramDTO telegramDTO) {
        Map<String, String> newMap = categoryService.getAllCategories().stream()
                .collect(Collectors.toMap(
                        category -> String.valueOf(category.getCategoryId()),
                        category -> category.getCategoryName()
                ));
        telegramDTO.setMap(newMap);
        telegramDTO.setLevel(1);
        return telegramDTO;
    }

    private TelegramDTO forTopic(TelegramDTO telegramDTO) {
        log.info("Метод forTopic, telegramDTO {}", telegramDTO);
        Map<String, String> newMap = topicService.getAllTopicsByIdCategory(telegramDTO.getCategory()).stream()
                .collect(Collectors.toMap(
                        topic -> String.valueOf(topic.getTopicId()),
                        topic -> topic.getTopicName()
                ));
        telegramDTO.setMap(newMap);
        telegramDTO.setLevel(2);
        return telegramDTO;
    }

    private TelegramDTO forQuestion(TelegramDTO telegramDTO) {
        log.info("Метод forQuestion, telegramDTO {}", telegramDTO);
        Map<String, String> newMap = questionService.findAllQuestionByTopicId(telegramDTO.getTopic()).stream()
                .collect(Collectors.toMap(
                        question -> String.valueOf(question.getQuestionId()),
                        question -> question.getQuestionName()
                ));
        telegramDTO.setMap(newMap);
        telegramDTO.setLevel(3);
        return telegramDTO;
    }
}
