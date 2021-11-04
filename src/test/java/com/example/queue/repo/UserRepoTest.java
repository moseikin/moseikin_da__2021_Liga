package com.example.queue.repo;

import com.example.queue.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@TestPropertySource(value = "/application-test.properties")
@Sql(value = {"../../../../../resources/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"../../../../../resources/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class UserRepoTest {

    @Autowired
    private UserRepo userRepo;

    @Test
    void getUserByLogin() {
        User user = new User();
        user
            .name("testUserName")
            .lastName("testUserLastName")
            .login("testUser")
            .pass("testUser")
            .eMail("testUser@testUser.ru")
            .role("ROLE_USER");
        userRepo.save(user);

        assertEquals(user, userRepo.getUserByLogin(user.login()));
    }
}