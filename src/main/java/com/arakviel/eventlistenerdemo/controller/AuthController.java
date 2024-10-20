package com.arakviel.eventlistenerdemo.controller;

import com.arakviel.eventlistenerdemo.dto.CreateUserRequest;
import com.arakviel.eventlistenerdemo.dto.CreateUserResponse;
import com.arakviel.eventlistenerdemo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    private String loginPage() {
        return "users/login";
    }

    @GetMapping("/signup")
    private String signupPage() {
        return "users/signup";
    }

    @PostMapping("/signup")
    private String signup(
          @Valid CreateUserRequest createUserRequest,
          BindingResult bindingResult,
          Model model) {

        if (bindingResult.hasErrors()) {
            var validationErrors = bindingResult.getFieldErrors().stream()
                  .map(FieldError::getDefaultMessage)
                  .toList();
            model.addAttribute("validationErrors", validationErrors);
            return "users/signup"; // Ім'я шаблону
        }

        CreateUserResponse createdUser = userService.save(createUserRequest);
        model.addAttribute("user", createdUser);

        return "redirect:/users?success";
    }
}
