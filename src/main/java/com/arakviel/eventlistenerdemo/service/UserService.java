package com.arakviel.eventlistenerdemo.service;

import com.arakviel.eventlistenerdemo.dto.CreateUserRequest;
import com.arakviel.eventlistenerdemo.dto.CreateUserResponse;
import com.arakviel.eventlistenerdemo.dto.UpdateUserRequest;
import com.arakviel.eventlistenerdemo.dto.UserResponse;
import com.arakviel.eventlistenerdemo.entity.User;
import com.arakviel.eventlistenerdemo.event.CreatedUserEvent;
import com.arakviel.eventlistenerdemo.exception.UserAlreadyExistsException;
import com.arakviel.eventlistenerdemo.exception.UserNotFoundException;
import com.arakviel.eventlistenerdemo.mapper.UserMapper;
import com.arakviel.eventlistenerdemo.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ApplicationEventPublisher publisher;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public CreateUserResponse save(CreateUserRequest createUserRequest) {
        // Перевірка на наявність користувача з таким же email
        if (userRepository.existsByEmail(createUserRequest.email())) {
            throw new UserAlreadyExistsException("Користувач з таким email вже існує.");
        }

        // Хешування пароля
        String hashedPassword = passwordEncoder.encode(createUserRequest.password());

        // Створення нового користувача
        User user = userMapper.map(createUserRequest);
        user.setPassword(hashedPassword);  // Збереження хешованого пароля

        user = userRepository.save(user);

        // Публікація події створення користувача
        var createdUserEvent = new CreatedUserEvent(this, user);
        publisher.publishEvent(createdUserEvent);

        return userMapper.map(user);
    }

    public List<UserResponse> findAll() {
        return userMapper.map(userRepository.findAll());
    }

    public UserResponse findById(Long id) {
        User user = userRepository
              .findById(id)
              .orElseThrow(() ->
                    new UserNotFoundException("Користувача з ID %s не знайдено.".formatted(id)));
        return userMapper.mapToUserResponse(user);
    }

    public void update(UpdateUserRequest updateUserRequest) {
        User user = userRepository.findById(updateUserRequest.id()).orElseThrow(
              () -> new UserNotFoundException("Користувача з таким id не знайдено."));
        user.setEmail(updateUserRequest.email());
        updateUserRequest.avatar()
              .ifPresent(user::setAvatar);
        updateUserRequest.password()
              .ifPresent(password -> user.setPassword(passwordEncoder.encode(password)));
        user.setRole(updateUserRequest.role());
        userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
