package com.example.realspringsocial.controller;

import com.example.realspringsocial.entity.Usr;
import com.example.realspringsocial.repo.SchoolRepository;
import com.example.realspringsocial.repo.UserRepository;
import com.example.realspringsocial.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NewUserController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    SchoolRepository schoolRepository;

    @Autowired
    private SchoolService schoolService;

    @PostMapping("/registration")
    public String new_user(Usr usr) {
//        Long schoolNumber = Long.valueOf(school);
//
//        School schoolFound = schoolService.findSchoolByNumber(schoolNumber, null);

//        Usr usr = new Usr(name, lastname, Integer.valueOf(age), schoolFound);
        usr.setIsActive(true);
        usr.setLastName("NEW");
        userRepository.save(usr);

        return "registration";
    }


//    @PostMapping("/new-user")
//    public String new_user(@RequestParam String name,
//                               @RequestParam String lastname,
//                               @RequestParam String age,
//                               @RequestParam String school,
//                                @RequestParam String p) {
//        Long schoolNumber = Long.valueOf(school);
//
//        School schoolFound = schoolService.findSchoolByNumber(schoolNumber, null);
//
//        Usr usr = new Usr(name, lastname, Integer.valueOf(age), schoolFound);
//
//        userRepository.save(usr);
//
//        return "newUser";
//    }

    @GetMapping("/registration")
    public String new_user() {

        return "registration";
    }


    //    @PostMapping("/new_user")
//    public @ResponseBody String add_new_user(@RequestParam String name,
//                                             @RequestParam String lastname,
//                                             @RequestParam String age,
//                                             @RequestParam String school) {
//        Long schoolNumber = Long.valueOf(school);
//
//        School schoolFound = schoolService.findSchoolByNumber(schoolNumber);
//
//        Usr usr = new Usr(name, lastname, Integer.valueOf(age), schoolFound);
//        usrRepository.save(usr);
//        return "SAVED";
//    }
}
