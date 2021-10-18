package com.example.realspringsocial.service;

import com.example.realspringsocial.entity.UserPosts;
import com.example.realspringsocial.entity.Usr;
import com.example.realspringsocial.repo.UserPostsRepository;
import com.example.realspringsocial.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class UserPostsService {

    private final UserPostsRepository userPostsRepository;
    private final UserRepository userRepository;

    public Iterable<UserPosts> findAllUserPostsById(String id){
        try {
            return userPostsRepository.findByUsrId(Long.valueOf(id));
        } catch (NumberFormatException e) {
            throw new NumberFormatException();
        }
    }

    @Transactional
    public String addPost(Long id, String text) {
            Optional<Usr> optional = userRepository.findById(id);
            if (optional.isPresent()) {
                UserPosts userPosts = new UserPosts();
                userPosts.setUsr(optional.get())
                        .setText(text);
                userPostsRepository.save(userPosts);
            } else {
                throw new EntityNotFoundException();
            }
        return "added: " + text + " of: " + id;
    }
}
