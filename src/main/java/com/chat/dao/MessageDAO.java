package com.chat.dao;

import com.chat.interfaces.IDao;
import com.chat.model.Message;
import com.chat.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class MessageDAO implements IDao<Message> {

    final private MessageRepository messageRepository;

    @Autowired
    public MessageDAO(MessageRepository topicRepository) {
        this.messageRepository = topicRepository;
    }

    @Override
    public Optional<Message> get(long id) {
        return messageRepository.findById(id);
    }

    @Override
    public List<Message> getAll() {
        List<Message> messages = new ArrayList<>();
        messageRepository.findAll().spliterator().forEachRemaining(messages::add);
        return messages;
    }

    @Override
    public void save(Message message) {
        messageRepository.save(message);
    }

    @Override
    public void update(Message message, String[] params) {
        messageRepository.save(message);
    }

    @Override
    public void delete(Message message) {
        messageRepository.delete(message);
    }
}
