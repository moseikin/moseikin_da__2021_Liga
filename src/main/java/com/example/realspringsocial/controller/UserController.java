package com.example.realspringsocial.controller;

import com.example.realspringsocial.entity.School;
import com.example.realspringsocial.entity.Usr;
import com.example.realspringsocial.service.SchoolService;
import com.example.realspringsocial.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@AllArgsConstructor
@Controller
@RequestMapping("/")
public class UserController {

    private final SchoolService schoolService;
    private final UserService userService;

    @GetMapping
    public String main (Map<String, Object> model) {
        Iterable<Usr> users = userService.allUsers();
        model.put("users", users);

        Iterable<School> schools = schoolService.allSchools();
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
