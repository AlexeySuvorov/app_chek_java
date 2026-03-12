package com.example.app_check_java.repository.custom;

import com.example.app_check_java.model.Question;

import java.util.List;
import java.util.Optional;

public interface CustomQuestionRepository {
    List<Question> getQuestions(String topicName);
    Optional<Question> getQuestionByNameAndTopicId(String questionName, Long topicId);
    List<Question> getQuestionsByTopicId(Integer topicId);
}
