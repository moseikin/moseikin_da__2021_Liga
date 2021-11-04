package com.example.queue.repo;

import com.example.queue.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    User getUserByLogin(String login);
}
