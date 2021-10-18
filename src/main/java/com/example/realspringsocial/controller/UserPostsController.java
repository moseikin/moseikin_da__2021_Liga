package com.example.realspringsocial.controller;

import com.example.realspringsocial.entity.UserPosts;
import com.example.realspringsocial.service.UserPostsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@AllArgsConstructor
@Controller
@RequestMapping(path = "/posts")
public class UserPostsController {

    private final UserPostsService userPostsService;

    @RequestMapping("/{id}")
    public String getPosts(@PathVariable("id") String id, Map<String, Object> model){
        Iterable<UserPosts> userPosts = userPostsService.findAllUserPostsById(id);
        model.put("userPosts", userPosts);
        return "posts";
    }

    @PostMapping
    public @ResponseBody String addPost(@RequestParam Long id, @RequestParam String text) {
        System.out.println("id = " + id);
        System.out.println("text = " + text);
        return userPostsService.addPost(id, text);
    }
}
