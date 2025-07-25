package ru.jwt.springsecurityjwt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.jwt.springsecurityjwt.dto.JwtRequest;
import ru.jwt.springsecurityjwt.dto.JwtResponse;
import ru.jwt.springsecurityjwt.dto.RegistrationUserDto;
import ru.jwt.springsecurityjwt.dto.UserDto;
import ru.jwt.springsecurityjwt.entities.User;
import ru.jwt.springsecurityjwt.exceptions.AppError;
import ru.jwt.springsecurityjwt.utils.JwtTokenUtils;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getLogin(), authRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Неправильный логин или пароль"), HttpStatus.UNAUTHORIZED);
        }

        UserDetails userDetails = userService.loadUserByUsername(authRequest.getLogin());
        String accessToken = jwtTokenUtils.generateAccessToken(userDetails);
        String refreshToken = jwtTokenUtils.generateRefreshToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(accessToken, refreshToken));
    }

    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        if (!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Пароли не совпадают"), HttpStatus.BAD_REQUEST);
        }
        if (userService.findByLogin(registrationUserDto.getLogin()).isPresent()) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Пользователь с указанным именем уже существует"), HttpStatus.BAD_REQUEST);
        }
        User user = userService.createNewUser(registrationUserDto);
        return ResponseEntity.ok(new UserDto(user.getId(), user.getLogin(), user.getEmail()));
    }

    public ResponseEntity<?> refreshToken(String refreshToken) {
        if (jwtTokenUtils.validateToken(refreshToken)) {
            String login = jwtTokenUtils.getUsername(refreshToken);
            UserDetails userDetails = userService.loadUserByUsername(login);
            String newAccessToken = jwtTokenUtils.generateAccessToken(userDetails);
            return ResponseEntity.ok(new JwtResponse(newAccessToken, refreshToken));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired refresh token");
        }
    }
}
