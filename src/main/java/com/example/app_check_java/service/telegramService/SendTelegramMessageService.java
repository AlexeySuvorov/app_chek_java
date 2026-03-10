package com.example.app_check_java.service.telegramService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Service
@Slf4j
public class SendTelegramMessageService {

    private TelegramClient telegramClient; //клиент взаимодействия с Telegram API

    @Autowired
    public SendTelegramMessageService(@Value("${bot.token}") String botToken) {
        this.telegramClient = new OkHttpTelegramClient(botToken);
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
