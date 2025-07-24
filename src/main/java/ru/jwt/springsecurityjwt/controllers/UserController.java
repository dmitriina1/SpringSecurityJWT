package ru.jwt.springsecurityjwt.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.jwt.springsecurityjwt.dto.UserDto;
import ru.jwt.springsecurityjwt.entities.Role;
import ru.jwt.springsecurityjwt.entities.User;
import ru.jwt.springsecurityjwt.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/info")
    public ResponseEntity<UserDto> getUserInfo(Principal principal) {
        String login = principal.getName();
        User user = userService.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

        List<String> roleNames = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());
        UserDto dto = new UserDto(user.getId(), user.getLogin(), user.getEmail(), roleNames);

        return ResponseEntity.ok(dto);
    }
}
