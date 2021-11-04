package com.example.queue.services;

import com.example.queue.entities.CustomUserDetails;
import com.example.queue.entities.User;
import com.example.queue.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public CustomUserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepo.getUserByLogin(login);
        if (user == null) {
            user = new User();
            user.login("null").name("null").lastName("null").pass("null").eMail("null").role("ROLE_USER");
        }

        return CustomUserDetails.fromUserEntityToCustomUserDetails(user);

    }
}