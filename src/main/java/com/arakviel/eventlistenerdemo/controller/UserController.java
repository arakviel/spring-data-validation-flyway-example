package com.arakviel.eventlistenerdemo.controller;

import com.arakviel.eventlistenerdemo.dto.CreateUserRequest;
import com.arakviel.eventlistenerdemo.dto.UpdateUserRequest;
import com.arakviel.eventlistenerdemo.dto.UserResponse;
import com.arakviel.eventlistenerdemo.service.UserService;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard/users")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    private final UserService userService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("users", userService.findAll());
        return "dashboard/users/index";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("createUserRequest", CreateUserRequest.defaultUser());
        return "dashboard/users/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute CreateUserRequest createUserRequest) {
        userService.save(createUserRequest);
        return "redirect:/dashboard/users"; // перенаправлення на список користувачів
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        UserResponse user = userService.findById(id);
        model.addAttribute("user", user);
        return "dashboard/users/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(
          @PathVariable Long id,
          @Valid @ModelAttribute UpdateUserRequest updateUserRequest,
          BindingResult bindingResult,
          Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("validationErrors", getValidationErrors(bindingResult));
            return "user/update"; // повертаємо на форму з помилками
        }

        userService.update(updateUserRequest);
        return "redirect:/dashboard/users"; // перенаправлення на список користувачів
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        userService.delete(id); // потрібно реалізувати delete у UserService
        return "redirect:/dashboard/users"; // перенаправлення на список користувачів
    }

    private List<String> getValidationErrors(BindingResult bindingResult) {
        List<String> errors = new ArrayList<>();
        bindingResult.getFieldErrors().forEach(error -> {
            String message = error.getDefaultMessage();
            errors.add(message != null ? message : "Невідома помилка");
        });
        return errors;
    }
}
