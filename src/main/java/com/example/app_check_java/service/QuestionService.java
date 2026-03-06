package com.example.app_check_java.service;

import com.example.app_check_java.exception.NotFoundQuestionExceptioin;
import com.example.app_check_java.model.Question;
import com.example.app_check_java.repository.QuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> findAllQuestionByTopicName(String topicName) {
        log.info("findAllQuestionByTopicName - topicName:{}", topicName);
        List<Question> questions = questionRepository.getQuestions(topicName);
        if (questions.isEmpty()) {
            log.info("questions is empty");
            throw new NotFoundQuestionExceptioin("По теме " + topicName + "вопросы не найдены");
        }
        log.info("Получены вопросы из базы данных: " + questions.toString());
        return questions;
    }

}
