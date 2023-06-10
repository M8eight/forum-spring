package com.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class ChaterForumApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChaterForumApplication.class, args);
    }
}
