package com.example.queue.services;

import com.example.queue.Constants;
import com.example.queue.dto.UserDto;
import com.example.queue.entities.User;
import com.example.queue.entities.enums.RolesEnum;
import com.example.queue.entities.requests.RegistrationRequest;
import com.example.queue.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public String getUserDto(Authentication auth){
        if (auth.getAuthorities().contains(new SimpleGrantedAuthority(RolesEnum.DELETED.getRole()))){
            return Constants.USER_NOT_FOUND;
        }
        return new UserDto().toUserDto(userRepo.getUserByLogin(auth.getName())).toString();
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
