package com.example.app_check_java.service.telegramService;

import com.example.app_check_java.dto.telegramDTO.TelegramDTO;
import com.example.app_check_java.dto.telegramDTO.UserTelegramDTO;
import com.example.app_check_java.model.Topic;
import com.example.app_check_java.service.dbService.CategoryService;
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
                    .level(1L)
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
            TelegramDTO telegramDTO = TelegramDTO.builder()
                    .user(userTelegramDTO)
                    .message(update.getCallbackQuery().getData())
                    .build();
            List<Topic> topicList = topicService.getAllTopicsByNameCategory(telegramDTO.getMessage());
            List<String> lists = new ArrayList<>();
            topicList.forEach(topic -> {lists.add(topic.getTopicName());});
            telegramDTO.setList(lists);
            telegramDTO.setLevel(2L);
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
        telegramDTO.setLevel(1L);
        return telegramDTO;
    }

    private TelegramDTO forTopic(TelegramDTO telegramDTO) {
        List<Topic> topicList = topicService.getAllTopicsByNameCategory(telegramDTO.getMessage());
        List<String> lists = new ArrayList<>();
        topicList.forEach(topic -> {lists.add(topic.getTopicName());});
        telegramDTO.setCategory(telegramDTO.getMessage());
        telegramDTO.setList(lists);
        telegramDTO.setLevel(2L);
        return telegramDTO;
    }


}
