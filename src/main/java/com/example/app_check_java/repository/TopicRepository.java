package com.example.app_check_java.repository;

import com.example.app_check_java.model.Topic;
import com.example.app_check_java.repository.castom.CustomTopicRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long>, CustomTopicRepository {

}
