package com.example.app_check_java.service.telegramService.bord;

import com.example.app_check_java.dto.telegramDTO.TelegramDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class InlineKeyboardServiceImpl implements KeyBoardService {
    //Статичная клавиатура у бота
    @Override
    public ReplyKeyboard createStartKeyboard(TelegramDTO telegramDTO) {
        log.info("start method service.bord.InlineKeyboardService.createStartKeyboard with chatId {}", telegramDTO.getUser().getUserName());

        List<InlineKeyboardRow> keyboardRows = new ArrayList<>();//Создается список строк клавиатуры (keyboardRows).
        InlineKeyboardRow currentRow = new InlineKeyboardRow();//Создается текущая строка (currentRow), в которую добавляются кнопки.

        Map<String, String> map = telegramDTO.getMap();
        for(String elementKey : map.keySet()) {
            log.info("callbackData {}", elementKey);
            log.info("level {}", telegramDTO.getLevel());
            String category = "0";
            String topic = "0";
            String question = "0";

            switch(telegramDTO.getLevel()) {
                case 1 -> category = elementKey;
                case 2 -> {
                    category = telegramDTO.getCategory() + "";
                    topic = elementKey;
                }
                case 3 -> {
                    category = telegramDTO.getCategory() + "";
                    topic = telegramDTO.getTopic() + "";
                    question = elementKey;
                }
            }
            InlineKeyboardButton button = InlineKeyboardButton.builder()
                    .text(map.get(elementKey))
                    .callbackData((telegramDTO.getLevel() + 1) + "_"
                            + category
                            + "_"
                            + topic
                            + "_"
                            + question)
                    .build();
            log.info("mapKey: {}, mapValue: {}", elementKey,  map.get(elementKey));
            currentRow.add(button);

            //Если операция относится к вопросу, начинаем каждую строку с новой строки
//            if ("/Question/Answer".equals(rabbitMessageDTO.getPath())) {
//                keyboardRows.add(currentRow);
//                currentRow = new InlineKeyboardRow();
//            }

            // Если в текущей строке 3 кнопки, начинаем новую строку
            if (currentRow.size() == 3) {
                keyboardRows.add(currentRow);
                currentRow = new InlineKeyboardRow();
            }
        }

        // Добавляем последнюю строку, если в ней есть кнопки
        if (!currentRow.isEmpty()) {
            keyboardRows.add(currentRow);
        }

        return InlineKeyboardMarkup.builder()
                .keyboard(keyboardRows)
                .build();
    }
}
