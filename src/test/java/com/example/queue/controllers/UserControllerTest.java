package com.example.queue.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@EnableTransactionManagement
@TestPropertySource("/application-test.properties")
@Sql(value = {"../../../../../resources/com/example/queue/repo/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"../../../../../resources/com/example/queue/repo/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class UserControllerTest {
    MockMvc mockMvc;

    @Autowired
    private UserController userController;

    @BeforeEach
    void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void getUserInfo() throws Exception{
        mockMvc.perform(get("/user"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void getActiveBookings() {
    }

    @Test
    void createBook() {
    }

    @Test
    void deleteBook() {
    }

    @Test
    void confirmBook() {
    }
}