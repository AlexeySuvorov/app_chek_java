package com.example.app_check_java.repository.custom;

import com.example.app_check_java.model.Question;

import java.util.List;

public interface CustomQuestionRepository {
    List<Question> getQuestions(String topicName);
}
