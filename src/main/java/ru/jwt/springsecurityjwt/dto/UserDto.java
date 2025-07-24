package ru.jwt.springsecurityjwt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.jwt.springsecurityjwt.entities.Role;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String login;
    private String email;
    private List<String> roles;

    public UserDto(Long id, String login, String email) {
        this.id = id;
        this.login = login;
        this.email = email;
    }
}
