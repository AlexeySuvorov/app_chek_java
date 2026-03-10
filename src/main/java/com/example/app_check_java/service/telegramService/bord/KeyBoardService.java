package com.example.app_check_java.service.telegramService.bord;

import com.example.app_check_java.dto.telegramDTO.TelegramDTO;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public interface KeyBoardService {
    ReplyKeyboard createStartKeyboard(TelegramDTO telegramDTO);
}
