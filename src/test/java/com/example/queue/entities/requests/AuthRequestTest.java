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
        assertThat(request.getLogin()).isNull();
        request.setLogin("testName");
        assertThat(request.getLogin()).isEqualTo("testName");
    }

    @Test
    void testPassword() {
        assertThat(request.getPassword()).isNull();
        request.setPassword("testName");
        assertThat(request.getPassword()).isEqualTo("testName");
    }

}