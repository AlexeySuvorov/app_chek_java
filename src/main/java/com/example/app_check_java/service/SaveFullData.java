package com.example.app_check_java.service;

import com.example.app_check_java.dto.FullDTO;
import com.example.app_check_java.exception.NotFoundDTOException;
import com.example.app_check_java.exception.NotFoundQuestionExceptioin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class SaveFullData {

    private final CategoryService categoryService;
    private final TopicService topicService;
    private final QuestionService questionService;
    private final AnswerService answerService;

    @Autowired
    public SaveFullData(CategoryService categoryService, TopicService topicService,
                        QuestionService questionService, AnswerService answerService) {
        this.categoryService = categoryService;
        this.topicService = topicService;
        this.questionService = questionService;
        this.answerService = answerService;
    }

    @Transactional
    public void SaveFullDataDB(List<FullDTO> fullDTO) {
        log.info("Запущен метод SaveFullDataDB. FullDTO: {}", fullDTO);
        if (fullDTO.isEmpty()) {
            log.info("fullDTO is empty");
            throw new NotFoundDTOException("Возникла ошибка, DTO не может быть пустым.");
        }
    }
}
