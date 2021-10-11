package com.example.realspringsocial.controller;

import com.example.realspringsocial.entity.UserPosts;
import com.example.realspringsocial.entity.Usr;
import com.example.realspringsocial.repo.UserPostsRepository;
import com.example.realspringsocial.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class UserController {
    private Usr usr;
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserPostsRepository userPostsRepository;

    @RequestMapping("/user")
    public String user(@ModelAttribute("userId") Usr usr,
                       Map<String, Object> model){
        if (usr.getId() == null) {
            return "redirect:/";
        } else {
            Long id = usr.getId();
            Optional<Usr> optional = userRepository.findById(id);

            optional.ifPresent(user -> {
                this.usr = user;
                model.put("user", user);
                List<UserPosts> userPosts = userPostsRepository.findByUsrId(id);
                model.put("userPosts", userPosts);
            });
        }

        return "user";
    }

    @PostMapping("/user")
    public String addPost(@RequestParam String id, @RequestParam String text) {
//        Usr usr;
        if (id != null) {
            Optional<Usr> optional = userRepository.findById(Long.valueOf(id));
            optional.ifPresent(value -> usr = value);
        }
        UserPosts userPosts = new UserPosts(usr, text);
        userPostsRepository.save(userPosts);
        return "user";
    }

//    @GetMapping("/user")
//    public String user(){
//        return "user";
//    }

}
