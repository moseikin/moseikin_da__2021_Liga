package com.example.queue.entities;

import com.example.queue.TestEntities;
import com.example.queue.repo.UserRepo;
import com.example.queue.services.CustomUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(value = "/application-test.properties")
@Sql(value = {"/before-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/after-test.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class CustomUserDetailsTest {
    private final TestEntities testEntities = new TestEntities();
    private User user;

    @Autowired PasswordEncoder passwordEncoder;
    @Autowired UserRepo userRepo;
    @Autowired CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    void setUp(){
        user = testEntities.testUser();
        user.pass(passwordEncoder.encode(user.pass()));
        userRepo.save(user);
    }

    CustomUserDetails customUserDetails = new CustomUserDetails();

    @Test
    void isAccountNonExpired() {
        assertThat(customUserDetails.isAccountNonExpired()).isTrue();
    }

    @Test
    void isAccountNonLocked() {
        assertThat(customUserDetails.isAccountNonLocked()).isTrue();
    }

    @Test
    void isCredentialsNonExpired() {
        assertThat(customUserDetails.isCredentialsNonExpired()).isTrue();
    }

    @Test
    void isEnabled() {
        assertThat(customUserDetails.isEnabled()).isTrue();
    }

    @Test
    void testGetPassword(){
        CustomUserDetails customUserDetails =
                customUserDetailsService.loadUserByUsername(user.login());
        assertThat(customUserDetails.getPassword())
                .isEqualTo(user.pass());
    }
}