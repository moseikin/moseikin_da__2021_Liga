package com.example.queue.controllers;

import com.example.queue.config.JwtProvider;
import com.example.queue.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AuthControllerTest {

    MockMvc mockMvc;

    @Autowired
    private AuthController authController;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtProvider jwtProvider;


    @BeforeEach
    void setup(){
        mockMvc = MockMvcBuilders
                .standaloneSetup(authController)
                .alwaysDo(print())
                .build();
    }

    @Test
    void registerUser() throws Exception{
        mockMvc
                .perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{ \"user_id\" : 3, " +
                        "   \"name\" : \"testUserName2\", " +
                        "   \"lastname\" : \"testUserLastName2\", " +
                        "   \"login\" : \"testUser2\", " +
                        "   \"pass\" : \"testUser2\", " +
                        "   \"email\" : \"qaz@wsx.ed\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void auth()throws Exception{
        mockMvc
                .perform(post("/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("{ \"login\" : \"testUser2\", \"password\" : \"testUser2\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void authAsAdmin() throws Exception{
        mockMvc
            .perform(post("/auth")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content("{ \"login\" : \"testAdmin\", \"password\" : \"testAdmin\"}"))
            .andExpect(status().isOk());
    }

    @Test
    void getUserService() {
        assertThat(userService).isNotNull();
    }

    @Test
    void getPasswordEncoder() {
        assertThat(passwordEncoder).isNotNull();
    }

    @Test
    void getJwtProvider() {
        assertThat(jwtProvider).isNotNull();
    }
}
