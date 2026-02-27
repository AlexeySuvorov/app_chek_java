package com.example.app_check_java.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
@Tag(name = "Controller for skill java", description = "главный контроллер для работы приложения через API")
public class MainController {

    @GetMapping ("/test")
    public String test() {
        log.info("Запущен метод test");
        return "test";
    }

}
