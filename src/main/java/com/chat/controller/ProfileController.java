package com.chat.controller;

import com.chat.model.User;
import com.chat.repository.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Validated
public class ProfileController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/profile/settings")
    String getProfile(Model model) {
        model.addAttribute("username", SecurityContextHolder.getContext().getAuthentication().getName());
        return "profile/profile-edit";
    }

    @PostMapping("/profile/edit/password")
    String editProfilePassword(@RequestParam("old-password") String oldPassword,
                               @Size(min = 5) @RequestParam("new-password") String newPassword,
                               @RequestParam("confirm-new-password") String confirmNewPassword,
                               @AuthenticationPrincipal User user,
                               RedirectAttributes redirectAttributes) {

        if (passwordEncoder.matches(oldPassword, user.getPassword())) {
            if (!newPassword.isBlank() && newPassword.equals(confirmNewPassword)) {
                user.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(user);
            } else {
                redirectAttributes.addFlashAttribute("error", "Пароли не совпадают");
                return "redirect:/profile/settings";
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "Неправильно введен старый пароль");
            return "redirect:/profile/settings";
        }

        redirectAttributes.addFlashAttribute("success", "Пароль изменен");
        return "redirect:/profile/settings";
    }
}
