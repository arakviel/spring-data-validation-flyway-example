package com.arakviel.eventlistenerdemo.dto;

import com.arakviel.eventlistenerdemo.entity.User;
import java.time.LocalDateTime;

public record CreateUserResponse(
      Long id,
      String email,
      String avatar,
      User.Role role,
      LocalDateTime createdAt
) {

}
