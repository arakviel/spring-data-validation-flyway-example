package com.arakviel.flywaydemo;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    UserService userService;

    @GetMapping
    public Iterable<User> getAllUsers() {
        return userService.getAll();
    }

    @PostMapping
    public ResponseEntity<UserResponse> store(@Valid @RequestBody CreateUserRequest userRequest) {
        User user = new User();
        user.setName(userRequest.name());
        user.setEmail(userRequest.email());
        userService.save(user);
        UserResponse userResponse = new UserResponse(1, user.getName(), user.getEmail());
        return ResponseEntity
              .status(HttpStatus.CREATED)
              .body(userResponse);
    }
}
