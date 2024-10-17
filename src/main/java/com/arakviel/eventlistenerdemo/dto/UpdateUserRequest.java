package com.arakviel.eventlistenerdemo.dto;

import com.arakviel.eventlistenerdemo.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Optional;

public record UpdateUserRequest(
      Long id,

      @Email(message = "Неправильний формат електронної пошти.")
      String email,

      Optional<String> avatar,

      @Size(min = 6, message = "Пароль має містити принаймні 6 символів.")
      Optional<String> password,

      @NotNull(message = "Роль не може бути порожньою.")
      User.Role role
) {

}