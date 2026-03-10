package com.example.app_check_java.dto.telegramDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserTelegramDTO {
    @NotNull
    private Long chatId;
    @NotBlank
    private String userName;
    @NotBlank
    private String lastName;
}
