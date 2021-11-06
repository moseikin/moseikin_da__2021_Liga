package com.example.queue.services;

import com.example.queue.Constants;
import com.example.queue.TestEntities;
import com.example.queue.config.JwtFilter;
import com.example.queue.config.JwtProvider;
import com.example.queue.dto.UserDto;
import com.example.queue.entities.User;
import com.example.queue.entities.enums.RolesEnum;
import com.example.queue.entities.requests.AuthRequest;
import com.example.queue.entities.requests.RegistrationRequest;
import com.example.queue.repo.UserRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(value = "/application-test.properties")
@Sql(value = {"/before-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/after-test.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@PropertySource(value = {"classpath:queue.properties"})
class UserServiceTest {
    TestEntities testEntities = new TestEntities();
    User user;
    Authentication auth;

    @Autowired UserService userService;
    @Autowired UserRepo userRepo;
    @Autowired PasswordEncoder passwordEncoder;
    @Autowired JwtProvider jwtProvider;
    @Autowired JwtFilter jwtFilter;

    @BeforeEach
    void setUp(){
        user = testEntities.testUser();
    }

    @AfterEach
    void tearDown(){
        if (user != null) {
            userRepo.delete(user);
        }
    }

    @Test
    void getUserDto() {
        userRepo.save(user);
        initAuth(user.login());
        assertThat(userService.getUserDto(auth)).isEqualTo(new UserDto().toUserDto(user).toString());
    }

    @Test
    void getUserDto_ExpectUserNotFound() {
        user.role(RolesEnum.DELETED.getRole());
        userRepo.save(user);
        initAuth(user.login());
        assertThat(userService.getUserDto(auth)).isEqualTo(Constants.USER_NOT_FOUND);
    }

    @Test
    void findByLoginAndPassword() {
        String password = user.pass();
        user.pass(passwordEncoder.encode(password));
        userRepo.save(user);
        AuthRequest request = new AuthRequest().login(user.login()).password(password);
        User found = userService.findByLoginAndPassword(request);
        assertThat(found).isNotNull();
    }

    @Test
    void findByLoginAndPassword_ExpectNull() {
        AuthRequest request = new AuthRequest().login("none").password("none");
        User found = userService.findByLoginAndPassword(request);
        assertThat(found).isNull();
    }

    @Test
    void registerNewUser() {
        RegistrationRequest registrationRequest = new RegistrationRequest();
                registrationRequest.setName(user.name());
                registrationRequest.setLastname(user.lastName());
                registrationRequest.setLogin(user.login());
                registrationRequest.setPassword(user.pass());
                registrationRequest.setEmail(user.eMail());
        userService.registerNewUser(registrationRequest);
        assertThat(userRepo.getUserByLogin(user.login())).isNotNull();
    }

    void initAuth(String login){
        String token = jwtProvider.generateToken(login);
        jwtFilter.setAuthentication(token);
        auth = SecurityContextHolder.getContext().getAuthentication();
    }
}