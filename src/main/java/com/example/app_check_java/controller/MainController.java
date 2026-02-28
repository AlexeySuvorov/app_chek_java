package com.example.app_check_java.controller;


import com.example.app_check_java.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
@Tag(name = "Controller for skill java", description = "главный контроллер для работы приложения через API")
public class MainController {

    private final CategoryService categoryService;

    @Autowired
    public MainController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping ("/test")
    public String test() {
        log.info("Запущен метод test");
        return "test";
    }

    @GetMapping("/category")
    @Operation(summary = "Method for get category", description = "Получить все категории из БД")
    public ResponseEntity<?> getAllCategories() {
        log.info("Контроллер для получения всех категорий");
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
}
