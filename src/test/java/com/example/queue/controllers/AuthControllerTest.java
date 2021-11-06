package com.example.queue.controllers;

import com.example.queue.entities.requests.AuthRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired AuthController authController;
    @Autowired ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Test
    void registerUser() {
    }

    @Test
    void auth() throws Exception {
//        AuthRequest request = new AuthRequest().password("test").login("test");
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/auth")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                .param(objectMapper.writeValueAsString(request))
//                )
//                .andExpect(status().isOk());
    }

    @Test
    void getUserService() {
        assertThat(authController.getUserService()).isNotNull();
    }

    @Test
    void getPasswordEncoder() {
        assertThat(authController.getPasswordEncoder()).isNotNull();
    }

    @Test
    void getJwtProvider() {
        assertThat(authController.getJwtProvider()).isNotNull();
    }
}