package com.example.app_check_java.service.dbService;

import com.example.app_check_java.exception.NotFoundTopicException;
import com.example.app_check_java.model.Topic;
import com.example.app_check_java.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;

    @Transactional(readOnly = true)
    public List<Topic> getAllTopicsByNameCategory(String topicName) {
        log.info("Получение темы по имени категории");
        List<Topic> topicList = topicRepository.getAllTopicsByNameCategory(topicName);
        if (topicList.isEmpty()) {
            log.error("В БД нет категорий");
            throw new NotFoundTopicException("Topic not found");
        } else {
            log.info("Получены категории из БД");
            return topicList;
        }
    }
}
