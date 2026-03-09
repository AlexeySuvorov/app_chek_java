package com.example.app_check_java.repository.custom;

import com.example.app_check_java.model.Topic;

import java.util.List;
import java.util.Optional;

public interface CustomTopicRepository {
    List<Topic> getAllTopicsByNameCategory(String categoryName);
    Optional<Topic> getTopicByNameAndByCategoryId(String topicName, Long categoryId);
}
