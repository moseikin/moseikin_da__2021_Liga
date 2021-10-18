package com.example.realspringsocial.controller;

import com.example.realspringsocial.entity.School;
import com.example.realspringsocial.service.SchoolService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@Controller
@RequestMapping(path = "/school")
public class SchoolController {
    private final SchoolService schoolService;

    @PostMapping("/new")
    public School addSchool(@RequestParam String number,
                            @RequestParam String address) {
        return schoolService.findSchoolByNumber(Long.valueOf(number), address);
    }
}
