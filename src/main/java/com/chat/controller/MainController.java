package com.chat.controller;

import com.chat.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;

@Controller
public class MainController {

    @Autowired
    TopicRepository topicRepository;

    @GetMapping("/")
    public String getMain(@RequestParam(value = "logout", required = false) String logoutParam, RedirectAttributes ra) {
        if (Objects.equals(logoutParam, "success")) {
            ra.addFlashAttribute("logoutSuccess", true);
            System.out.println("XXX");
            return "redirect:/";
        }
        return "main";
    }

    @GetMapping("/faq")
    public String getFaq() {
        return "faq";
    }
}
