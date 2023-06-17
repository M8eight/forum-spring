package com.chat.dao;

import com.chat.interfaces.IDao;
import com.chat.model.Topic;
import com.chat.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class TopicDAO implements IDao<Topic> {

    final private TopicRepository topicRepository;

    @Autowired
    public TopicDAO(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Override
    public Optional<Topic> get(long id) {
        return topicRepository.findById(id);
    }

    @Override
    public List<Topic> getAll() {
        List<Topic> topics = new ArrayList<>();
        topicRepository.findAll().spliterator().forEachRemaining(topics::add);
        return topics;
    }

    @Override
    public void save(Topic topic) {
        topicRepository.save(topic);
    }

    @Override
    public void update(Topic topic) {
        topicRepository.save(topic);
    }

    @Override
    public void delete(Topic topic) {
        topicRepository.delete(topic);
    }
}
