package com.example.queue.controllers;

import com.example.queue.Constants;
import com.example.queue.TestEntities;
import com.example.queue.config.JwtProvider;
import com.example.queue.dto.UserDto;
import com.example.queue.entities.User;
import com.example.queue.entities.requests.AuthRequest;
import com.example.queue.entities.requests.RegistrationRequest;
import com.example.queue.repo.UserRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(value = "/application-test.properties")
@Sql(value = {"/before-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/after-test.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class AuthControllerTest {
    private final AuthRequest request = new AuthRequest();
    private final TestEntities testEntities = new TestEntities();
    private User user;

    @Autowired AuthController authController;
    @Autowired ObjectMapper objectMapper;
    @Autowired MockMvc mockMvc;
    @Autowired UserRepo userRepo;
    @Autowired PasswordEncoder passwordEncoder;
    @Autowired JwtProvider jwtProvider;


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
    void registerUser() throws Exception {
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setName(user.name());
        registrationRequest.setLastname(user.lastName());
        registrationRequest.setLogin(user.login());
        registrationRequest.setPassword(user.pass());
        registrationRequest.setEmail(user.eMail());
        String request = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(registrationRequest);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(request))
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertThat(result).isEqualTo(new UserDto().toUserDto(user).toString());
    }

    @Test
    void registerUser_ExpectIncorrectRegistrationData() throws Exception {
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setName(null);
        registrationRequest.setLastname(null);
        registrationRequest.setLogin(null);
        registrationRequest.setPassword(null);
        registrationRequest.setEmail("notLikeEmail");
        String request = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(registrationRequest);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(request))
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertThat(result).isEqualTo(Constants.INCORRECT_REGISTRATION_DATA);
    }

    @Test
    void auth() throws Exception {
        String rawPassword = user.pass();
        user.pass(passwordEncoder.encode(user.pass()));
        userRepo.save(user);
        String token = jwtProvider.generateToken(user.login());

        request.setLogin(user.login());
        request.setPassword(rawPassword);
        String request = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(this.request);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(request))
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertThat(result).isEqualTo(token);
    }

    @Test
    void auth_ExpectUserNotFound() throws Exception {
        request.setLogin("test");
        request.setPassword("test");
        String s = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(request);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(s))
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertThat(result).isEqualTo(Constants.USER_NOT_FOUND);
    }

    @Test
    void auth_ExpectIncorrectRegistrationData() throws Exception {
        request.setLogin(null);
        request.setPassword(null);
        String s = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(request);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(s))
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertThat(result).isEqualTo(Constants.INCORRECT_REGISTRATION_DATA);
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