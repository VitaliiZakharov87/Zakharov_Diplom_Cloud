package ru.netology.cloudservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.netology.cloudservice.exceptions.CloudExceptionHandlerAdvice;
import ru.netology.cloudservice.logging.Logback;
import ru.netology.cloudservice.dto.ResultMessageDto;
import ru.netology.cloudservice.model.User;
import ru.netology.cloudservice.service.AuthService;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping
@RequiredArgsConstructor
@CloudExceptionHandlerAdvice
public class AuthController {
    private final AuthService authService;

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User.TokenDto> login(@Valid @RequestBody User.Credentials credentials) {
        User.TokenDto tokenDto = authService.login(credentials);
        return Logback.log(ResponseEntity.ok().body(tokenDto));
    }

    @PostMapping("/logout")
    public ResponseEntity<ResultMessageDto> logout(@RequestHeader("auth-token") Optional<String> headerAuthToken) {
        ResultMessageDto resultMessageDto = authService.logout(headerAuthToken);
        return Logback.log(ResponseEntity.ok().body(resultMessageDto));
    }
}
