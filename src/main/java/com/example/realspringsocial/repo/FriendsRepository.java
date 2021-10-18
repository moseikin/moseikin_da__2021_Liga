package com.example.realspringsocial.repo;

import com.example.realspringsocial.entity.Friends;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendsRepository extends CrudRepository<Friends, Long> {
    public Friends findByFriendsPk_OwnerId(Long id);
}
