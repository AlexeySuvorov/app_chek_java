package com.example.app_check_java.repository.castom;

import com.example.app_check_java.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CustomCategoryRepositoryImpl implements CustomCategoryRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CustomCategoryRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Category> getAllCategories() {
        String sql = "select * from category";
        return jdbcTemplate.query(sql, new CategoryRowMapper());
    }

    private static class CategoryRowMapper implements RowMapper<Category> {

//        Реализует единственный метод интерфейса RowMapper. Spring вызывает этот метод для каждой строки результата запроса. Параметры:
//        rs (ResultSet) — текущая строка результата запроса, из которой можно извлекать данные по именам колонок
//        rowNum — номер текущей строки (начинается с 0)
        @Override
        public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
            Category category = new Category();

            return Category.builder()
                    .categoryId(rs.getLong("category_id"))
                    .name(rs.getString("name"))
                    .build();

        }
    }
}
