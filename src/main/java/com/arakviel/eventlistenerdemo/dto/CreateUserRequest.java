package com.arakviel.eventlistenerdemo.dto;

import com.arakviel.eventlistenerdemo.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
      @Email(message = "Неправильний формат електронної пошти")
      @NotBlank(message = "Електронна пошта не може бути порожньою")
      String email,

      @NotBlank(message = "Пароль не може бути порожнім")
      @Size(min = 8, message = "Пароль повинен містити щонайменше 8 символів")
      String password,

      String avatar,

      @NotNull(message = "Роль користувача повинна бути вказана")
      User.Role role) {

    public static CreateUserRequest defaultUser() {
        return new CreateUserRequest("", "", "", User.Role.USER);
    }
}

