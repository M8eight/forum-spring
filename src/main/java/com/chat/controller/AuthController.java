package com.chat.controller;

import com.chat.helper.ValidationHelpers;
import com.chat.model.User;
import com.chat.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginView() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerView(Model model) {
        model.addAttribute("userObj", new User());
        return "auth/register";
    }

    @PostMapping("/register")
    public String addUser(@ModelAttribute("userObj") @Valid User user, BindingResult br, Model m, RedirectAttributes ra) {
        if (br.hasErrors()) {
            m.addAttribute("errors",
                    ValidationHelpers.validationParseHelper(br));
            return "auth/register";
        }
        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            Map<String, String> errors = new HashMap<>();
            errors.put("password", "Пароли не совпадают");
            m.addAttribute("errors", errors);
            return "auth/register";
        }
        if (!userService.saveUser(user)) {
            Map<String, String> errors = new HashMap<>();
            errors.put("username", "Такой пользователь уже существует");
            m.addAttribute("errors", errors);
            return "auth/register";
        }
        ra.addFlashAttribute("successFlash", "Вы успешно зарегестрировались");
        return "redirect:/login";
    }
}
