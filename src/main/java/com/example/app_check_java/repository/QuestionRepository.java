package com.example.app_check_java.repository;

import com.example.app_check_java.model.Question;
import com.example.app_check_java.repository.custom.CustomQuestionRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long>, CustomQuestionRepository {
}
