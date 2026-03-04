package com.example.app_check_java.repository.castom;

import com.example.app_check_java.model.Category;
import com.example.app_check_java.model.Topic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@Slf4j
public class CustomTopicRepositoryImpl implements CustomTopicRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Topic> getAllTopicsByNameCategory(String categoryName) {
        log.info("Запуск метода getAllTopicsByNameCategory");
        String sql = """
            SELECT t.* FROM topic t
            JOIN category c ON c.category_id = t.category_id
            WHERE c.name = ?
            """;
        log.info(sql);
        log.info("params: {}", categoryName);
        return jdbcTemplate.query(sql, new TopicRowMapper(), categoryName);
    }

    private static class TopicRowMapper implements RowMapper<Topic> {

        @Override
        public Topic mapRow(ResultSet rs, int rowNum) throws SQLException {
            Topic topic = new Topic();
            return Topic.builder()
                    .topicId(rs.getLong("topic_id"))
                    .topicName(rs.getString("topic_name"))
                    .docAdddate(rs.getDate("doc_adddate").toLocalDate())
                    .docModdate(rs.getDate("doc_moddate").toLocalDate())
                    .build();


        }
    }
}
