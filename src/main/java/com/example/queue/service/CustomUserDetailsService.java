package com.example.queue.service;

import com.example.queue.entity.CustomUserDetails;
import com.example.queue.entity.User;
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
        return CustomUserDetails.fromUserEntityToCustomUserDetails(user);
    }
}