package com.example.realspringsocial.service;

import com.example.realspringsocial.entity.Friends;
import com.example.realspringsocial.entity.Usr;
import com.example.realspringsocial.repo.FriendsRepository;
import com.example.realspringsocial.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@AllArgsConstructor
@Service
@Transactional(readOnly = true)
public class FriendsService {
    private final UserRepository userRepository;
    private final FriendsRepository friendsRepository;

    public Friends friendsManage(Long initiatorId, Long toFriendId, Boolean isFriend){
        Optional<Usr> initiatorOpt = userRepository.findById(initiatorId);
        Optional<Usr> toFriendOpt = userRepository.findById(toFriendId);

        if (initiatorOpt.isPresent() && toFriendOpt.isPresent()){
            // ищем дружбу, где инициатором был второй
            Friends reversedFriends = friendsRepository.findByFriendsPk_OwnerId(toFriendId);
            if (reversedFriends != null) {
                // возвращаем первоначальную дружбу
                return reversedFriends;
            } else {
                return createFriendShip(initiatorId, toFriendId, isFriend);
            }
        } else{
            throw new EntityNotFoundException();
        }
    }

    @Transactional
    public Friends createFriendShip(Long initiatorId, Long toFriendId, Boolean isFriend){
        Friends friends = new Friends();
        Friends.FriendsPk friendsPk = new Friends.FriendsPk();
        friendsPk.setOwnerId(initiatorId);
        friendsPk.setUserId(toFriendId);
        friends.setAreFriends(isFriend);
        friends.setFriendsPk(friendsPk);
        friendsRepository.save(friends);
        return friends;
    }
}
