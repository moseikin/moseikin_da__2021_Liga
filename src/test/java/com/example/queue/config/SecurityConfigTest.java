package com.example.queue.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SecurityConfigTest {

    @Autowired SecurityConfig securityConfig;

    @Test
    void getJwtFilter() {
        assertThat(securityConfig.getJwtFilter()).isNotNull();
    }

}