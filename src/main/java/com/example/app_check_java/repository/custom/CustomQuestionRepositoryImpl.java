package com.example.app_check_java.repository.custom;

import com.example.app_check_java.model.Question;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class CustomQuestionRepositoryImpl implements CustomQuestionRepository {
    @Override
    public List<Question> getQuestions(String topicName) {
        log.info("Запущен метод getQuestions: topicName={}", topicName);
        String sql = """
                select q.* from question q
                join topic t on q.topic_id = t.topic_id
                where q.topic_name = ?;
                """;
        return List.of();
    }

    String sql = """
            SELECT t.*, c.category_id, c.category_name FROM topic t
            JOIN category c ON c.category_id = t.category_id
            WHERE c.category_name = ?;
            """;
}
