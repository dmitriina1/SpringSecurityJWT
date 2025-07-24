package ru.jwt.springsecurityjwt.dto;

import lombok.Data;

@Data
public class RegistrationUserDto {
    private String login;
    private String password;
    private String confirmPassword;
    private String email;
}
