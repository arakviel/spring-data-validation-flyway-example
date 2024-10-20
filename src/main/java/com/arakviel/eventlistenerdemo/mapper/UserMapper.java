package com.arakviel.eventlistenerdemo.mapper;

import com.arakviel.eventlistenerdemo.dto.CreateUserRequest;
import com.arakviel.eventlistenerdemo.dto.CreateUserResponse;
import com.arakviel.eventlistenerdemo.dto.UserResponse;
import com.arakviel.eventlistenerdemo.entity.User;
import java.util.List;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface UserMapper {

    User map(CreateUserRequest request);

    CreateUserResponse map(User user);

    List<UserResponse> map(List<User> user);

    UserResponse mapToUserResponse(User user);
}