package com.example.app_check_java.repository.custom;

import com.example.app_check_java.model.Answer;

import java.util.Optional;

public interface CustomAnswerRepository {
    Optional<Answer> getAnswersByIdQuestion(Long questionId);
}
