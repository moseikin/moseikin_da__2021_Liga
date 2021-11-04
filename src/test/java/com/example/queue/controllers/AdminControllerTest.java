package com.example.queue.controllers;

import com.example.queue.config.JwtFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AdminControllerTest {

    MockMvc mockMvc;

    @Autowired
    AdminController adminController;

    @Autowired
    JwtFilter jwtFilter;

    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(adminController)
                .alwaysDo(print())
                .build();
    }

    @Test
    void deleteBook() {
    }

    @Test
    void setAppeared() {
    }

    @Test
    void setCompleted() {
    }

    @Test
    void setAnnulled() {
    }

    @Test
    void getNearestBook() throws Exception {
        mockMvc.perform(get("/admin/soon"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllBook() throws Exception {
        // no primary constructor exception
        String page = "0";
        String size = "4";
        mockMvc.perform(get("/admin/active-bookings")
                    .param("page", page)
                    .param("size", size))
                .andExpect(status().isOk());
    }
}


