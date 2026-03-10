package com.example.app_check_java.service.telegramService;

import com.example.app_check_java.dto.telegramDTO.TelegramDTO;
import com.example.app_check_java.service.telegramService.bord.InlineKeyboardServiceImpl;
import com.example.app_check_java.service.telegramService.bord.KeyBoardService;
import com.example.app_check_java.service.telegramService.bord.ReplyKeyboardServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SendTelegramMessageService {

    private TelegramClient telegramClient; //клиент взаимодействия с Telegram API

    @Autowired
    public SendTelegramMessageService(@Value("${bot.token}") String botToken) {
        this.telegramClient = new OkHttpTelegramClient(botToken);
    }

    //создание кнопок
    public void startKeybord(TelegramDTO telegramDTO, int flagBoard) {
        KeyBoardService keyBoardService;
        //выбираем тип кнопки
        if (flagBoard == 0) {
            keyBoardService = new InlineKeyboardServiceImpl();
        } else {
            keyBoardService = new ReplyKeyboardServiceImp();
        }
        log.info("Запущен метод startKeybord для сообщения - {}", telegramDTO);
        if (telegramDTO != null) {
            log.info("Сообщение rabbitMessageDTO не пустое");
            Long chatId = telegramDTO.getUser().getChatId();
            ReplyKeyboard markup = null;
            List<String> markupList = new ArrayList<>();
            if (!checkMainMenu(telegramDTO)) { //проверяем, добавлять ли кнопку перехода на главное меню
                telegramDTO.getList().add("Категории");
            }
            markup = keyBoardService.createStartKeyboard(telegramDTO);
            methodSendMessage(chatId, "================================", markup);
        }
    }

    private boolean checkMainMenu(TelegramDTO telegramDTO) {
        return telegramDTO.getLevel() == 1;
    }


    //Отправка сообщений в Телеграм
    private void methodSendMessage(Long chatId, String text, ReplyKeyboard markup) {
        log.info("Отправляем сообщение в телеграм");
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .replyMarkup(markup)
                .build();
        try {
            telegramClient.execute(message);
            log.info("service.message_processing.MessageProcessing.methodSendMessage - SUCCESS");
        } catch (Exception e) {
            log.error("invalid method service.message_processing.MessageProcessing.methodSendMessage ", e);
        }
    }
}
