package com.example.app_check_java.repository.castom;

import com.example.app_check_java.model.Topic;

import java.util.List;

public interface CustomTopicRepository {
    List<Topic> getAllTopicsByNameCategory(String categoryName);
}
