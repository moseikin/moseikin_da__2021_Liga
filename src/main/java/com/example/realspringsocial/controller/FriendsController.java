package com.example.realspringsocial.controller;

import com.example.realspringsocial.entity.Friends;
import com.example.realspringsocial.service.FriendsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@AllArgsConstructor
@Controller
@RequestMapping(path = "/friends")
public class FriendsController {
    private final FriendsService friendsService;


    @PostMapping
    public @ResponseBody Friends friendManage(@RequestParam Long initiatorId,
                         @RequestParam Long toFriendId,
                         @RequestParam Boolean isFriend){
        return friendsService.friendsManage(initiatorId, toFriendId, isFriend);
    }
}
