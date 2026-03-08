package com.example.app_check_java.repository;

import com.example.app_check_java.model.Answer;
import com.example.app_check_java.repository.custom.CustomAnswerRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends CrudRepository<Answer, Long>, CustomAnswerRepository {
}
