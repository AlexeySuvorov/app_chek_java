package com.example.app_check_java.controller;

import com.example.app_check_java.service.telegramService.GetTelegramMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramController implements LongPollingSingleThreadUpdateConsumer {

    private final GetTelegramMessageService getTelegramMessageService;

    @Override
    public void consume(Update update) {
        log.info("start consume - update{}", update);
        String type = new String();
        if (update.hasMessage() && update.getMessage().hasText()) {
            log.info("update has text: {}", update.getMessage().getText());
            type = "text";
        } else if (update.hasCallbackQuery()) {
            String callbackQueryText = update.getCallbackQuery().getData();
            log.info("callbackQuery has text: {}", callbackQueryText);
            type = "callBackQuery";
        } else {
            log.info("update has no data");
        }
        getTelegramMessageService.handlerTelegramMessage(update, type);
    }
}
