package com.example.app_check_java.configuration;

import com.example.app_check_java.controller.TelegramController;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;

@Configuration
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TelegramBotConfig implements SpringLongPollingBot {
    @Value("${bot.token}")
    private String token;

    private final TelegramController telegramController;


    @Override
    public String getBotToken() {
        return token;
    }

    //Возвращает объект mainTelegramController, который будет обрабатывать все входящие сообщения.
    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return telegramController;
    }
}
