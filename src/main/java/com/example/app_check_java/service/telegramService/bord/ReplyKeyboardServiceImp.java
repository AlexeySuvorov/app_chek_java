package com.example.app_check_java.service.telegramService.bord;

import com.example.app_check_java.dto.telegramDTO.TelegramDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ReplyKeyboardServiceImp implements KeyBoardService {

    //Статичная клавиатура у бота
    @Override
    public ReplyKeyboard createStartKeyboard(TelegramDTO telegramDTO) {
        log.debug("start method service.bord.ReplyKeyboardServiceImp.createStartKeyboard");

        //Создаем клавиатуру
        List<KeyboardRow> keyboardRows = new ArrayList<>();//Создается список строк клавиатуры (keyboardRows).
        KeyboardRow row1 = new KeyboardRow(); //Создается текущая строка (currentRow), в которую добавляются кнопки.
        for(String el: telegramDTO.getList()) {
            row1.add(el);
        }
        keyboardRows.add(row1);

        // 2. Настраиваем разметку клавиатуры
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup(keyboardRows);
        markup.setResizeKeyboard(true);    // Автоматический размер
        markup.setOneTimeKeyboard(false);   // Скрыть после нажатия
        return markup;
    }
}
