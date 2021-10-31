package com.example.queue.controllers;

import com.example.queue.Constants;
import com.example.queue.config.JwtProvider;
import com.example.queue.entities.User;
import com.example.queue.entities.requests.AuthRequest;
import com.example.queue.entities.requests.RegistrationRequest;
import com.example.queue.services.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Setter
@Getter
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @PostMapping("/register")
    public String registerUser(@RequestBody @Valid RegistrationRequest registrationRequest,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Constants.INCORRECT_REGISTRATION_DATA;
        } else {
            return userService.registerNewUser(registrationRequest).toString();
        }
    }

    @PostMapping("/auth")
    public String auth(@RequestBody @Valid AuthRequest request, BindingResult bindingResult,
                         HttpServletResponse response) {
        if (bindingResult.hasErrors()){
            return Constants.INCORRECT_REGISTRATION_DATA;
        } else {
            User user = userService.findByLoginAndPassword(request.getLogin(), request.getPassword());
            if (user == null) {
                return Constants.USER_NOT_FOUND;
            } else {
                String token = jwtProvider.generateToken(user.login());
                Cookie cookie = new Cookie("jwt", token);
                cookie.setMaxAge(1296000);
                response.addCookie(cookie);
                return token;
            }
        }
    }
}