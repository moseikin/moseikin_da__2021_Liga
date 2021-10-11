package com.example.realspringsocial.repo;

import com.example.realspringsocial.entity.UserPosts;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserPostsRepository extends CrudRepository<UserPosts, Long> {
    List<UserPosts> findByUsrId(Long userId);
}
