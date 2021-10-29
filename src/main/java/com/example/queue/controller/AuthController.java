package com.example.queue.controller;

import com.example.queue.Constants;
import com.example.queue.entity.request.AuthRequest;
import com.example.queue.entity.request.RegistrationRequest;
import com.example.queue.service.UserService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@Setter
@Getter
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public String registerUser(@RequestBody @Valid RegistrationRequest registrationRequest,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Constants.INCORRECT_REGISTRATION_DATA;
        } else {
            return userService.registerNewUser(registrationRequest).toString();
        }
    }

    // не отпралять запрос и ответ в сервис=====================================
    @PostMapping("/auth")
    public String auth( @RequestBody @Valid AuthRequest request,
                             HttpServletResponse response) {
        return userService.logIn(request, response);
    }
}