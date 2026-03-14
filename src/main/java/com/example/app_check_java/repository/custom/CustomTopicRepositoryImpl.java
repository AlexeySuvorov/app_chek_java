package com.example.app_check_java.repository.custom;

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
import java.util.Optional;

@Repository
@Slf4j
public class CustomTopicRepositoryImpl implements CustomTopicRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CustomTopicRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Topic> getAllTopicsByNameCategory(String categoryName) {
        log.info("Запуск метода getAllTopicsByNameCategory");
        String sql = """
            SELECT t.*, c.category_id, c.category_name FROM topic t
            JOIN category c ON c.category_id = t.category_id
            WHERE c.category_name = ?;
            """;
        log.info("params: {}", categoryName);
        log.debug("SQL запрос: {}", sql);
        return jdbcTemplate.query(sql, new TopicRowMapper(), categoryName);
    }

    @Override
    public Optional<Topic> getTopicByNameAndByCategoryId(String topicName, Long categoryId) {
        log.info("Метод getTopicByName, topicName: {}", topicName);
        String sql = """
            SELECT t.*, c.category_id, c.category_name FROM topic t
            JOIN category c ON c.category_id = t.category_id
            WHERE t.topic_name = ?
            AND c.category_id = ?;
            """;
        log.info("SQL запрос {}", sql);
        List<Topic> topicList = jdbcTemplate.query(sql, new TopicRowMapper(), topicName, categoryId);
        return topicList.stream().findFirst();
    }

    @Override
    public List<Topic> getAllTopicsByIdCategory(Integer categoryId) {
        log.info("Метод репозитория getAllTopicsByIdCategory, categoryId: {}", categoryId);
        String sql = """
                select * from topic t
                JOIN category c ON c.category_id = t.category_id
                where c.category_id = ?;
                """;
        log.debug("SQL запрос: {}", sql);
        return jdbcTemplate.query(sql, new TopicRowMapper(), categoryId);}

    @Override
    public Optional<Topic> getTopicById(Long topicId) {
        log.info("Метод репозитория getTopicById, topicId: {}", topicId);
        String sql = """
                select * from topic t 
                where t.topic_id = ?;
                """;
        log.debug("SQL: {}", sql);
        return jdbcTemplate.query(sql, new TopicRowMapper(), topicId).stream().findFirst();
    }

    private static class TopicRowMapper implements RowMapper<Topic> {

        @Override
        public Topic mapRow(ResultSet rs, int rowNum) throws SQLException {

            Category category = Category.builder()
                    .categoryId(Long.valueOf(rs.getLong("category_id")))
                    .categoryName(rs.getString("category_name"))
                    .build();

            return Topic.builder()
                    .topicId(rs.getLong("topic_id"))
                    .topicName(rs.getString("topic_name"))
                    .category(category)
                    .build();
        }
    }
}
