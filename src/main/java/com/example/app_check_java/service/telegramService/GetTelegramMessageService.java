package com.example.app_check_java.service.telegramService;

import com.example.app_check_java.dto.telegramDTO.TelegramDTO;
import com.example.app_check_java.dto.telegramDTO.UserTelegramDTO;
import com.example.app_check_java.model.Answer;
import com.example.app_check_java.model.Question;
import com.example.app_check_java.model.Topic;
import com.example.app_check_java.service.dbService.AnswerService;
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
import java.util.TreeMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetTelegramMessageService {

    private final String emojiCategory = "📁";  // Категория
    private final String emojiTopic = "📚";      // Тема
    private final String emojiQuestion = "❓";    // Вопрос
    private final String emojiAnswer = "💡";      // Ответ

    private final CategoryService categoryService;
    private final TopicService topicService;
    private final QuestionService questionService;
    private final SendTelegramMessageService sendTelegramMessageService;
    private final AnswerService answerService;

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

            TelegramDTO telegramDTO = TelegramDTO.builder()
                    .user(userTelegramDTO)
                    .build();
            forCategory(telegramDTO);
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
            switch (telegramDTO.getLevel()) {
                case 2 -> {
                    if (telegramDTO.getCategory() == 0) { //условие для кнопки возврата на предыюущий уровень
                        forCategory(telegramDTO);
                    } else {
                        forTopic(telegramDTO);
                    }
                }
                case 3 -> {
                    if (telegramDTO.getTopic() == 0) {
                        forCategory(telegramDTO);
                    } else {
                        forQuestion(telegramDTO);
                    }
                }
                case 4 -> {
                    if (telegramDTO.getQuestion() == 0) {
                        forTopic(telegramDTO);
                    } else {
                        forAnswer(telegramDTO);
                    }
                }
                case 5 -> forQuestion(telegramDTO);
            }
            sendTelegramMessageService.startKeybord(telegramDTO, 0);
        }

    }

    private TelegramDTO forCategory(TelegramDTO telegramDTO) {
        Map<String, String> newMap = categoryService.getAllCategories().stream()
                .collect(Collectors.toMap(
                        category -> String.valueOf(category.getCategoryId()),
                        category -> emojiCategory + " " + category.getCategoryName(),
                        (existing, replacement) -> existing,
                        TreeMap::new
                ));
        telegramDTO.setMap(newMap);
        telegramDTO.setLevel(1);
        telegramDTO.setMessage("Категории");
        return telegramDTO;
    }

    private TelegramDTO forTopic(TelegramDTO telegramDTO) {
        log.info("Метод forTopic, telegramDTO {}", telegramDTO);
        Map<String, String> newMap = topicService.getAllTopicsByIdCategory(telegramDTO.getCategory()).stream()
                .collect(Collectors.toMap(
                        topic -> String.valueOf(topic.getTopicId()),
                        topic -> emojiTopic + " " + topic.getTopicName(),
                        (existing, replacement) -> existing,
                        TreeMap::new
                ));
        newMap.put("0", emojiCategory + " Категории");
        telegramDTO.setMap(newMap);
        telegramDTO.setLevel(2);
        telegramDTO.setMessage(emojiTopic + " Темы");
        return telegramDTO;
    }

    private TelegramDTO forQuestion(TelegramDTO telegramDTO) {
        log.info("Метод forQuestion, telegramDTO {}", telegramDTO);
        Map<String, String> newMap = questionService.findAllQuestionByTopicId(telegramDTO.getTopic()).stream()
                .collect(Collectors.toMap(
                        question -> String.valueOf(question.getQuestionId()),
                        question -> emojiQuestion + " " + question.getQuestionName(),
                        (existing, replacement) -> existing, // если ключи дублируются, оставляем первый
                        TreeMap::new // Используем TreeMap вместо HashMap
                ));

        newMap.put("0", emojiTopic + " Темы");
        telegramDTO.setMap(newMap);
        telegramDTO.setLevel(3);
        telegramDTO.setMessage(emojiQuestion + " " + "Вопросы");
        return telegramDTO;
    }

    private TelegramDTO forAnswer(TelegramDTO telegramDTO) {
        log.info("Метод  forAnswer, telegramDTO {}", telegramDTO);
        Map<String, String> newMap = new HashMap<>();
        Answer answer = answerService.getAnswerByQuestionId(telegramDTO.getQuestion());
        Question question = answer.getQuestion();
        //Создаем сообщение в котором для ответа будет сначала описан вопрос
        String text = emojiQuestion + question.getQuestionName() + "\n" +
                "===================================" + "\n" +
                emojiAnswer + answer.getAnswerName();
        newMap.put("0", emojiQuestion + " " + "Вопросы");
        telegramDTO.setMap(newMap);
        telegramDTO.setLevel(4);
        telegramDTO.setMessage(text);
        return telegramDTO;
    }
}
