package com.example.queue.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SecurityConfigTest {

    @Autowired SecurityConfig securityConfig;

    @Test
    void getJwtFilter() {
        assertThat(securityConfig.getJwtFilter()).isNotNull();
    }

    @Test
    void getJwtProvider() {
        assertThat(securityConfig.getJwtProvider()).isNotNull();
    }
}