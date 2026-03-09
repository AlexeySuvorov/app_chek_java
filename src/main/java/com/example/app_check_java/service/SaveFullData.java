package com.example.app_check_java.service;

import com.example.app_check_java.dto.FullDTO;
import com.example.app_check_java.exception.NotFoundDTOException;
import com.example.app_check_java.exception.NotFoundQuestionExceptioin;
import com.example.app_check_java.model.Answer;
import com.example.app_check_java.model.Category;
import com.example.app_check_java.model.Question;
import com.example.app_check_java.model.Topic;
import com.example.app_check_java.repository.AnswerRepository;
import com.example.app_check_java.repository.CategoryRepository;
import com.example.app_check_java.repository.QuestionRepository;
import com.example.app_check_java.repository.TopicRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SaveFullData {

    private final CategoryRepository categoryRepository;
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final TopicRepository topicRepository;

    @Autowired
    public SaveFullData(CategoryRepository categoryRepository, AnswerRepository answerRepository,
                        QuestionRepository questionRepository, TopicRepository topicRepository) {
        this.categoryRepository = categoryRepository;
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.topicRepository = topicRepository;
    }


    @Transactional
    public List<String> saveListFullDataDB(List<FullDTO> fullDTO) {
        log.info("Запущен метод SaveFullDataDB. FullDTO: {}", fullDTO);
        List<String> resultOperation = new ArrayList<>();
        if (fullDTO.isEmpty()) {
            log.info("fullDTO is empty");
            throw new NotFoundDTOException("Возникла ошибка, DTO не может быть пустым.");
        } else {
            for (FullDTO fullDTO1 : fullDTO) {
                resultOperation.add(saveFullDTO(fullDTO1));
            }
        }
        return resultOperation;
    }

    @Transactional
    public String saveFullDTO(FullDTO fullDTO) {
        log.info("сохранение данных fullDTO: {}", fullDTO);
        Category category = getOrCreateCategory(fullDTO.getCategory());
        Topic topic = getOrCreateTopic(fullDTO.getTopic(), category);
        Question question = getOrCreateQuestion(fullDTO.getQuestion(), topic);
        getOrCreateAnswer(fullDTO.getAnswer(), question);
        return "Вопрос " + fullDTO.getQuestion() + "добавлен";
    }

    private Category getOrCreateCategory(String categoryName) {
        log.info("Метод getOrCreateCategory, category: {}", categoryName);
        return categoryRepository.getCatgoryByName(categoryName).orElseGet(() -> {
            log.debug("getOrCreateCategory -> категория не найдена, создаю новую запись");
            Category category = Category.builder()
                    .categoryName(categoryName)
                    .addDate(LocalDateTime.now())
                    .modDate(LocalDateTime.now())
                    .build();
            return categoryRepository.save(category);
        });
    }

    private Topic getOrCreateTopic(String topicName, Category category) {
        log.info("Метод getOrCreateTopic, topic: {}, категория{} ", topicName, category);
        return topicRepository.getTopicByName(topicName).orElseGet(() -> {
            log.debug("getOrCreateCategory -> тема не найдена, создаю новую запись");
            Topic topic = Topic.builder()
                    .topicName(topicName)
                    .category(category)
                    .docAdddate(LocalDateTime.now())
                    .docModdate(LocalDateTime.now())
                    .build();
            return topicRepository.save(topic);
        });
    }

    private Question getOrCreateQuestion(String questionName, Topic topic) {
        log.info("Метод getOrCreateQuestion, question: {}, тема{}", questionName, topic);
        return questionRepository.getQuestionByName(questionName).orElseGet(() -> {
            log.debug("getOrCreateQuestion -> вопрос не найден, создаю новую запись");
            Question question = Question.builder()
                    .questionName(questionName)
                    .topic(topic)
                    .docAdddate(LocalDateTime.now())
                    .docModdate(LocalDateTime.now())
                    .build();
            return questionRepository.save(question);
        });
    }

    private Answer getOrCreateAnswer(String answerName, Question question) {
        log.info("Метод getOrCreateAnswer, answer: {}, question: {}", answerName, question);
        Answer answer = Answer.builder()
                .answerName(answerName)
                .question(question)
                .docAdddate(LocalDateTime.now())
                .docModdate(LocalDateTime.now())
                .build();
        return answerRepository.save(answer);
    }
}
