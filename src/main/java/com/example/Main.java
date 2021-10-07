package com.example;

import com.example.service.UserPostsService;
import com.example.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
//        AddContent.addSchool();
//        AddContent.addUser();
        UserPostsService.addPost();
        AddContent.allUsrInSchool();
        UserService.getAllUsers();
//        UserService.deleteAllUsers();


    }
}
