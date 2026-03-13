package com.example.app_check_java.service.dbService;

import com.example.app_check_java.exception.NotFoundAnswerException;
import com.example.app_check_java.model.Answer;
import com.example.app_check_java.repository.AnswerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class AnswerService {
    private final AnswerRepository answerRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public Answer getAnswerByQuestionId(long questionId) {
        log.info("Get answer by question id {}", questionId);
        Optional<Answer> answer = answerRepository.findById(questionId);
        if (answer.isPresent()) {
            log.info("Ответ на вопрос - {}", answer.toString());
            return answer.get();
        } else {
            log.info("Ответ на вопрос с ID {} не найден", questionId);
            throw new NotFoundAnswerException("Ответ на вопрос с ID - " + questionId + " не найден");
        }
    }
}
