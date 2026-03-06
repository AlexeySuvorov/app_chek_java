package com.example.app_check_java.repository.custom;

import com.example.app_check_java.model.Question;
import com.example.app_check_java.model.Topic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
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

    private static class QestionRowMapper implements RowMapper<Question> {

        @Override
        public Question mapRow(ResultSet rs, int rowNum) throws SQLException {
            log.info("Запущен QestionRowMapper для преобразование question в объект");
            Topic topic = Topic.builder()
                    .topicId(Long.valueOf(rs.getLong("topic_id")))
                    .topicName(rs.getString("topic_name"))
                    .build();

            return Question.builder()
                    .questionId(Long.valueOf(rs.getLong("question_id")))
                    .questionName(rs.getString("question_name"))
                    .topic(topic)
                    .build();
        }
    }
}
