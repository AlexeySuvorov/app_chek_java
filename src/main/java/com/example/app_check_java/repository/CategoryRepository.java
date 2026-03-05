package com.example.app_check_java.repository;

import com.example.app_check_java.model.Category;
import com.example.app_check_java.repository.custom.CustomCategoryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, CustomCategoryRepository {
}
