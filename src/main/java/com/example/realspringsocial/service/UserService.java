package com.example.realspringsocial.service;

import com.example.realspringsocial.entity.Usr;
import com.example.realspringsocial.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void editUser(String id, Usr sourceData) {
        Optional<Usr> optional = userRepository.findById(Long.valueOf(id));
        optional.ifPresent(usr -> {
            usr
                    .setName(sourceData.getName())
                    .setLastName(sourceData.getLastName())
                    .setAge(sourceData.getAge())
                    .setSchool(sourceData.getSchool());
            userRepository.save(usr);
        });


    }
}
