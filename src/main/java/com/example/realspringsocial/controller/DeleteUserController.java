package com.example.realspringsocial.controller;

import com.example.realspringsocial.entity.Usr;
import com.example.realspringsocial.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class DeleteUserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/delete-user")
    public String deleteUser(Map<String, Object> model){

        Iterable<Usr> users = userRepository.findAll();
        model.put("users", users);

        return "deleteUser";
    }

    @PostMapping("/delete-user")
    public String deleteUser(@RequestParam String id){
        Optional<Usr> optional = userRepository.findById(Long.valueOf(id));
        optional.ifPresent(usr -> userRepository.delete(usr));
        return "deleteUser";


    }
}
