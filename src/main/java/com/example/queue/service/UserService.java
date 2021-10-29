package com.example.queue.service;

import com.example.queue.config.JwtProvider;
import com.example.queue.dto.UserDto;
import com.example.queue.entity.RolesEnum;
import com.example.queue.entity.request.AuthRequest;
import com.example.queue.entity.request.RegistrationRequest;
import com.example.queue.entity.User;
import com.example.queue.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public UserDto getUserDto(Authentication auth){
        return new UserDto().toUserDto(userRepo.getUserByLogin(auth.getName()));
    }

    public User findByLoginAndPassword(String login, String password) {
        User user = userRepo.getUserByLogin(login);
        if (user != null) {
            if (passwordEncoder.matches(password, user.pass())) {
                return user;
            }
        }
        return null;
    }

    // requst and responce убрать из сервиса
    public String logIn(AuthRequest request, HttpServletResponse response) {
        User user = findByLoginAndPassword(request.getLogin(), request.getPassword());
        String token = jwtProvider.generateToken(user.login());
        Cookie cookie = new Cookie("jwt", token);
        cookie.setMaxAge(1296000);
        response.addCookie(cookie);

        return token;
    }

    // requst убрать из сервиса
    @Transactional
    public UserDto registerNewUser(RegistrationRequest registrationRequest) {
        User user = new User();
        user.pass(passwordEncoder.encode(registrationRequest.getPassword()))
                .login(registrationRequest.getLogin())
                .name(registrationRequest.getName())
                .lastName(registrationRequest.getLastname())
                .eMail(registrationRequest.getEmail())
                .role(RolesEnum.USER.getRole());

        userRepo.save(user);
        return new UserDto().toUserDto(user);
    }
}
