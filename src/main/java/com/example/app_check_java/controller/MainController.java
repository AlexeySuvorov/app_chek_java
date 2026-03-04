package com.example.app_check_java.controller;


import com.example.app_check_java.dto.TopicDTO;
import com.example.app_check_java.exception.NotFoundCategoryException;
import com.example.app_check_java.model.Category;
import com.example.app_check_java.service.CategoryService;
import com.example.app_check_java.service.TopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Slf4j
@Tag(name = "Controller for skill java", description = "главный контроллер для работы приложения через API")
public class MainController {

    private final CategoryService categoryService;
    private final TopicService topicService;

    @Autowired
    public MainController(CategoryService categoryService,  TopicService topicService) {
        this.categoryService = categoryService;
        this.topicService =  topicService;
    }

    @GetMapping ("/test")
    public String test() {
        log.info("Запущен метод test");
        return "test";
    }

    @GetMapping("/category/all")
    @Operation(summary = "Method for get category", description = "Получить все категории из БД")
    public ResponseEntity<?> getAllCategories() {
            log.info("Контроллер для получения всех категорий");
            return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PostMapping("/Topic/All/ByIdCategory")
    @Operation(summary = "Method for get topics", description = "Получить все темы по определенной категории")
    public ResponseEntity<?> getAllTopicsByIdCategory(@Valid @RequestBody TopicDTO topicDTO, BindingResult bindingResult) {
        log.info("Контроллер для получения всех тем по категории. Метод getAllTopicsByIdCategory. TopicDTO: {}", topicDTO);
        if  (bindingResult.hasErrors()) {
            log.error("Ошибка валидации в методе - Контроллер для получения всех тем по категории");
            StringBuilder errorMessages = new StringBuilder();
            List<FieldError> listFieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : listFieldErrors) {
                errorMessages.append(fieldError.getDefaultMessage());
                errorMessages.append(";");
            }
            return ResponseEntity.badRequest().body(errorMessages.toString());
        }
        return ResponseEntity.ok(topicService.getAllTopicsByNameCategory(topicDTO.getTopicName()));
    }
}
