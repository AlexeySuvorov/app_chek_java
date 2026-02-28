package com.example.app_check_java.service;

import com.example.app_check_java.exception.NotFoundCategoryException;
import com.example.app_check_java.model.Category;
import com.example.app_check_java.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Map<Long, String> getAllCategories() {
        log.info("Получить все категории");
        List<Category> listCategory = categoryRepository.findAll();
        if (listCategory.isEmpty()) {
            log.info("В БД нет категорий");
            throw new NotFoundCategoryException("Category not found");
        } else {
            log.info("getAllCategories");
            return listCategory.stream()
                    .collect(Collectors.toMap(
                            Category::getCategoryId,
                            Category::getName));
        }
    }
}
