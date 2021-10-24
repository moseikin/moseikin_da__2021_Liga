package com.example.queue.repo;

import com.example.queue.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {

    User getUserByLogin(String login);


}
