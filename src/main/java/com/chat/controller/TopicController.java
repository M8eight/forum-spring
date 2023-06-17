package com.chat.controller;

import com.chat.dao.TopicDAO;
import com.chat.error.TopicNotFoundException;
import com.chat.helper.ValidationHelpers;
import com.chat.model.Message;
import com.chat.model.Topic;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TopicController {

    final private TopicDAO topicDAO;

    @Autowired
    public TopicController(TopicDAO topicDAO) {
        this.topicDAO = topicDAO;
    }


    @GetMapping("/topics")
    public String getTopics(final Model model) {
        model.addAttribute("topicList", topicDAO.getAll());
        return "topic/topics";
    }

    @PostMapping("/topic/add")
    public String addTopic(@ModelAttribute("topicObj") @Valid Topic topicForm, BindingResult br, RedirectAttributes redirectAttributes) {
        if (br.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors",
                    ValidationHelpers.validationParseHelper(br));
            return "redirect:/topic/add";
        }
        topicDAO.save(topicForm);
        return "redirect:/topics";
    }

    @GetMapping("/topic/add")
    public String addTopicView(final Model model) {
        model.addAttribute("topicObj", new Topic());
        return "topic/topic-add";
    }

    @GetMapping("/topic/{id}/edit")
    public String editTopicView(@PathVariable(value = "id") Long id, final Model model) {
        model.addAttribute("topic", topicDAO.get(id)
                .orElseThrow(() -> new TopicNotFoundException(id)));
        model.addAttribute("topicObj", new Topic());
        return "topic/topic-edit";
    }

    @GetMapping("/topic/{id}")
    public String getTopicByIdView(@PathVariable(value = "id") Long id, final Model model) {
        Topic topic = topicDAO.get(id).orElseThrow(() -> new TopicNotFoundException(id));
        model.addAttribute("topic", topic);
        model.addAttribute("messages", topic.getMessages());
        model.addAttribute("messageObj", new Message());

        return "topic/topic";
    }

    @PostMapping("/topic/{id}")
    public String editTopic(@ModelAttribute("topicObj") Topic topic, RedirectAttributes ra, BindingResult br) {
        if (br.hasErrors()) {
            ra.addFlashAttribute("errors",
                    ValidationHelpers.validationParseHelper(br));
            return "redirect:/topic/" + topic.getId() + "/edit";
        }
        //TODO Сделать
        topicDAO.update(topic, new String[]{});
        return "redirect:/topics";
    }

    @PostMapping("/topic/{id}/delete")
    public String deleteTopicById(@PathVariable(value = "id") Topic topic) {
        topicDAO.delete(topic);
        return "redirect:/topics";
    }
    //todo сделать изменение топиков
}
