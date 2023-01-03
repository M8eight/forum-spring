package com.chat.error;

public class TopicNotFoundException extends RuntimeException{

    public TopicNotFoundException(Long id) {
        super("Could not found topic " + id);
    }

}
