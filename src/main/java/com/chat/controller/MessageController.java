package com.chat.controller;

import com.chat.dao.MessageDAO;
import com.chat.helper.ValidationHelpers;
import com.chat.model.Message;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MessageController {
    private final MessageDAO messageDAO;

    @Autowired
    public MessageController(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    @PostMapping("/messages")
    public String addMessage(@ModelAttribute("messageObj") @Valid Message message, BindingResult br, RedirectAttributes ra) {
        Long topicId = message.getTopic().getId();
        if (br.hasErrors()) {
            ra.addFlashAttribute("errors",
                    ValidationHelpers.validationParseHelper(br));
            return "redirect:/topic/" + topicId;
        }
        messageDAO.save(message);
        return "redirect:/topic/" + topicId;
    }

}
