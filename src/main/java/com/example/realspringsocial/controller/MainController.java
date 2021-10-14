package com.example.realspringsocial.controller;

import com.example.realspringsocial.entity.School;
import com.example.realspringsocial.entity.UserPosts;
import com.example.realspringsocial.entity.Usr;
import com.example.realspringsocial.repo.SchoolRepository;
import com.example.realspringsocial.repo.UserPostsRepository;
import com.example.realspringsocial.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class MainController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    UserPostsRepository userPostsRepository;

//    @GetMapping
//    public String main (@ModelAttribute("main")
//            final Object mainMappingObject,
//            final BindingResult bindingResult,
//            final RedirectAttributes redirectAttributes,
//            @RequestParam(name="id", required = false) String id,
//                        Map<String, Object> model) {
//        model.put("id", id);
//
//        if (id == null) {
//            Iterable<Usr> users = userRepository.findAll();
//            model.put("users", users);
//
//            Iterable<School> schools = schoolRepository.findAll();
//            model.put("schools", schools);
//            return "main";
//        } else {
//            Long userId = Long.valueOf(id);
//            Optional<Usr> optional = userRepository.findById(userId);
//            if (optional.isPresent()){
//                Usr usr = optional.get();
//                redirectAttributes.addFlashAttribute("userId", usr);
//                return "redirect:user";
//            } else {
//                return "main";
//            }
//
//        }
//
//    }

    @GetMapping
    public String main(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        auth.getAuthorities();
        auth.getCredentials();
        auth.getDetails();
        auth.getPrincipal();
        System.out.println("USER = " + username);
        return "main";
    }

//    @PostMapping(/login)
//    public String login


//    @PostMapping
//    public @ResponseBody String getUserDetails(@RequestParam String user, @RequestParam String password){
//
//        return "password = " + password;
//    }








}
