package com.example.app_check_java.repository.custom;

import com.example.app_check_java.model.Answer;
import com.example.app_check_java.model.Question;
import com.example.app_check_java.model.Topic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class CustomAnswerRepositoryImpl implements CustomAnswerRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CustomAnswerRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Transactional(readOnly = true)
    @Override
    public Optional<Answer> getAnswersByIdQuestion(Long questionId) {
        log.info("Метод getAnswersByIdQuestion: questionId={}", questionId);
        String sql = """
                select a.*, q.question_id, q.question_name from answer a
                join question q on q.question_id = a.question_id
                where a.question_id = ?;
                """;
        log.debug("SQL запрос: {}", sql);
        log.info("params: {}", questionId);
        try {
            Answer answer = jdbcTemplate.queryForObject(sql, new AnswerRowMapper(), questionId);
            return Optional.ofNullable(answer);
        } catch (EmptyResultDataAccessException e) {
            log.info("Ответ не найден");
            return Optional.empty();
        }
    }

    private static class AnswerRowMapper implements RowMapper<Answer> {
        @Override
        public Answer mapRow(ResultSet rs, int rowNum) throws SQLException {
            log.info("Метод mapRow rs={}, rowNum={} ", rs, rowNum);
            Question question = Question.builder()
                    .questionId(Long.valueOf(rs.getLong("question_id")))
                    .questionName(rs.getString("question_name"))
                    .build();
            return Answer.builder()
                    .answerId(Long.valueOf(rs.getLong("answer_id")))
                    .answerName(rs.getString("answer_name"))
                    .question(question)
                    .build();
        }
    }
}