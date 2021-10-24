package com.example.queue.controller;

import com.example.queue.entity.request.AuthRequest;
import com.example.queue.entity.request.RegistrationRequest;
import com.example.queue.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Setter
@Getter
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public String registerUser(@RequestBody @Valid RegistrationRequest registrationRequest) {
        return userService.registerNewUser(registrationRequest);
    }

    @PostMapping("/auth")
    public String auth(@RequestBody AuthRequest request,
                             HttpServletResponse response) {
        return userService.logIn(request, response);
    }
}