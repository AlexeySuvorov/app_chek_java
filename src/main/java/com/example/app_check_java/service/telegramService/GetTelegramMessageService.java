package com.example.app_check_java.service.telegramService;

import com.example.app_check_java.dto.telegramDTO.TelegramDTO;
import com.example.app_check_java.service.dbService.CategoryService;
import com.example.app_check_java.service.telegramService.bord.InlineKeyboardServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetTelegramMessageService {

    private final CategoryService categoryService;
    private final InlineKeyboardServiceImpl inlineKeyboardServiceImpl;

    public void handlerTelegramMessage(Update update, String type) {
        log.info("Запущен метод - handlerTelegramMessage");
        TelegramDTO telegramDTO = TelegramDTO.builder()
                .userName(update.getMessage().getFrom().getUserName())
                .build();
        if (type.equals("text")) {
            log.info("Блок - text");
            telegramDTO.setList(categoryService.getAllCategories());
            inlineKeyboardServiceImpl.createStartKeyboard(telegramDTO);
        }
    }


}
