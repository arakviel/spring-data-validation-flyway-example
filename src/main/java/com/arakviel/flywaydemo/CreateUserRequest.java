package com.arakviel.flywaydemo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest(
      @NotBlank(message = "Ти забув ім'я")
      String name,
      @NotBlank(message = "Ти забув пошту")
      @Email(message = "Не вірний формат пошти")
      String email) {
}
