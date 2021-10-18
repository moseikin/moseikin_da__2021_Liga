package com.example.realspringsocial.repo;

import com.example.realspringsocial.entity.UserPosts;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPostsRepository extends CrudRepository<UserPosts, Long> {
    List<UserPosts> findByUsrId(Long userId);
}
