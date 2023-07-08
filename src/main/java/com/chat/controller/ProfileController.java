package com.chat.controller;

import com.chat.helper.ValidationHelpers;
import com.chat.model.User;
import com.chat.repository.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    @PostMapping("/profile/edit/username/check")
    ResponseEntity<?> userExits(@RequestParam("username") String newUsername) {
        if (userRepository.findByUsername(newUsername) == null) {
            return new ResponseEntity<>(HttpStatus.ACCEPTED); //Разрешенно создавать
        }
        return new ResponseEntity<>(HttpStatus.OK); //Запрещено
    }

//    @PostMapping("/profile/edit/username")
//    String editProfileUsername(@RequestParam("new-username") String newUsername,
//                               @Size(min = 5, message = "Пароль должен быть больше 5 символов") @RequestParam("password") String password,
//                               @AuthenticationPrincipal User user,
//                               RedirectAttributes redirectAttributes,
//                               BindingResult br,
//                               Model m) {
//        if (br.hasErrors()) {
//            m.addAttribute("errors",
//                    ValidationHelpers.validationParseHelper(br));
//            return "profile/profile-edit";
//        }
//
//        if (userRepository.findByUsername(newUsername) == null && passwordEncoder.matches(newUsername, user.getPassword())) {
//            redirectAttributes.addFlashAttribute("success", "Имя пользователя изменено");
//            return "redirect:/profile/settings";
//        }
//        //#TODO Сденлать изменение имени
//
//        redirectAttributes.addFlashAttribute("error", "Неправильно введен старый пароль");
//        return "redirect:/profile/settings";
//    }

    @PostMapping("/profile/edit/password")
    String editProfilePassword(@Size(min = 5) @RequestParam("old-password") String oldPassword,
                               @Size(min = 5) @RequestParam("new-password") String newPassword,
                               @Size(min = 5) @RequestParam("confirm-new-password") String confirmNewPassword,
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
