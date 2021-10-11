package com.example.realspringsocial.controller;

import com.example.realspringsocial.entity.School;
import com.example.realspringsocial.entity.UserPosts;
import com.example.realspringsocial.entity.Usr;
import com.example.realspringsocial.repo.UserPostsRepository;
import com.example.realspringsocial.repo.UserRepository;
import com.example.realspringsocial.service.SchoolService;
import com.example.realspringsocial.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class UserController {
    private Usr usr;
    private final Map<String, Object> reservedModel = new HashMap<>();
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPostsRepository userPostsRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SchoolService schoolService;

    @RequestMapping("/user")
    public String user(@ModelAttribute("userId") Usr usr,
                       Map<String, Object> model){
        if (usr.getId() != null) {
            Long id = usr.getId();
            Optional<Usr> optional = userRepository.findById(id);
            optional.ifPresent(user -> {
                this.usr = user;
                model.put("user", user);
                List<UserPosts> userPosts = userPostsRepository.findByUsrId(id);
                model.put("userPosts", userPosts);
                reservedModel.putAll(model);
            });
        } else {
            if (reservedModel.isEmpty()) {
                return "redirect:/";
            } else {
                model.putAll(reservedModel);
            }
        }



        }

        return "user";
    }

    @PostMapping("/user")
    public String addPost(@RequestParam String id, @RequestParam String text) {
        if (id != null) {
            Optional<Usr> optional = userRepository.findById(Long.valueOf(id));
            optional.ifPresent(value -> usr = value);
        }
        UserPosts userPosts = new UserPosts(usr, text);
        userPostsRepository.save(userPosts);
//        usr = null;
        return "user";
    }

    @PostMapping("/update")
    public String updateUser(@RequestParam String id,
                             @RequestParam String name,
                             @RequestParam String lastname,
                             @RequestParam String age,
                             @RequestParam String school) {
        School schoolToEdit = schoolService.findSchoolByNumber(Long.parseLong(school), null);
        Usr usr = new Usr(name, lastname, Integer.valueOf(age), schoolToEdit);
        userService.editUser(id, usr);

        return "user";
    }






}
