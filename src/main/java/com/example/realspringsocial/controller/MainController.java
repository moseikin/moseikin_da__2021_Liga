package com.example.realspringsocial.controller;

import com.example.realspringsocial.entity.School;
import com.example.realspringsocial.entity.Usr;
import com.example.realspringsocial.repo.SchoolRepository;
import com.example.realspringsocial.repo.UserPostsRepository;
import com.example.realspringsocial.repo.UserRepository;
import com.example.realspringsocial.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class MainController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    UserPostsRepository userPostsRepository;

    @Autowired
    private UserService userService;

    @GetMapping
    public String main (Map<String, Object> model) {
            Iterable<Usr> users = userRepository.findAll();
            model.put("users", users);

            Iterable<School> schools = schoolRepository.findAll();
            model.put("schools", schools);
            return "main";
    }


    @PostMapping
    public @ResponseBody String addUser(@RequestBody Usr usr){
        return userService.addUser(usr);
    }

    @DeleteMapping("/delete-user")
    public @ResponseBody String deleteUser(@RequestParam String id){
        return userService.deleteUser(id);
    }

    @PutMapping("/edit-user")
    public @ResponseBody String editUser(@RequestBody Usr usr){
        return userService.editUser(usr);
    }
}
