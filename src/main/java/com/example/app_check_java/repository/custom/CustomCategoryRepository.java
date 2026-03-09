package com.example.app_check_java.repository.custom;

import com.example.app_check_java.model.Category;

import java.util.List;
import java.util.Optional;

public interface CustomCategoryRepository {
    List<Category> getAllCategories();
    Optional<Category> getCategoryByName(String name);
}
