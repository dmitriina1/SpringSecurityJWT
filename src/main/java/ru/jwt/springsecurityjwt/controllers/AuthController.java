package ru.jwt.springsecurityjwt.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.jwt.springsecurityjwt.dto.JwtRequest;
import ru.jwt.springsecurityjwt.dto.JwtResponse;
import ru.jwt.springsecurityjwt.dto.RegistrationUserDto;
import ru.jwt.springsecurityjwt.dto.UserDto;
import ru.jwt.springsecurityjwt.entities.User;
import ru.jwt.springsecurityjwt.exceptions.AppError;
import ru.jwt.springsecurityjwt.service.AuthService;
import ru.jwt.springsecurityjwt.service.UserService;
import ru.jwt.springsecurityjwt.utils.JwtTokenUtils;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        return authService.createAuthToken(authRequest);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        return authService.createNewUser(registrationUserDto);
    }
}
