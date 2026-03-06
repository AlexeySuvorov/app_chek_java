package com.example.app_check_java.repository.custom;

import com.example.app_check_java.model.Question;
import com.example.app_check_java.model.Topic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@Slf4j
public class CustomQuestionRepositoryImpl implements CustomQuestionRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CustomQuestionRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Question> getQuestions(String topicName) {
        log.info("Запущен метод getQuestions: topicName={}", topicName);
        String sql = """
                select q.*, t.topic_id, t.topic_name from question q
                join topic t on q.topic_id = t.topic_id
                where t.topic_name = ?;
                """;
        log.debug("SQL запрос: {}", sql);
        log.info("params: {}", topicName);
        return jdbcTemplate.query(sql, new QestionRowMapper(), topicName);
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
