package com.chat.controller;

import com.chat.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    TopicRepository topicRepository;

    @GetMapping("/")
    public String getMain(Model model) {
        return "main";
    }

    @GetMapping("/faq")
    public String getFaq() {
        return "faq";
    }
}
