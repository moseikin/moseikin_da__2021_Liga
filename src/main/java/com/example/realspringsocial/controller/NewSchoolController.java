package com.example.realspringsocial.controller;

import com.example.realspringsocial.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NewSchoolController {
    @Autowired
    private SchoolService schoolService;

    @GetMapping("/new-school")
        public String newSchool() {

        return "newSchool";
    }

    @PostMapping("/new-school")
    public String addSchool(@RequestParam String number,
                            @RequestParam String address) {
        schoolService.findSchoolByNumber(Long.valueOf(number), address);

        return "newSchool";
    }
}
