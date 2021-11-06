package com.example.queue.entities.requests;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthRequestTest {

    AuthRequest request = new AuthRequest();

    @Test
    void testLogin() {
        assertThat(request.login()).isNull();
        request.login("testName");
        assertThat(request.login()).isEqualTo("testName");
    }

    @Test
    void testPassword() {
        assertThat(request.password()).isNull();
        request.password("testName");
        assertThat(request.password()).isEqualTo("testName");
    }

}