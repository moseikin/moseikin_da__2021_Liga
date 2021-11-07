package com.example.queue.controllers;

import com.example.queue.Constants;
import com.example.queue.TestEntities;
import com.example.queue.config.JwtFilter;
import com.example.queue.config.JwtProvider;
import com.example.queue.dto.UserDto;
import com.example.queue.entities.Booking;
import com.example.queue.entities.User;
import com.example.queue.repo.BookingRepo;
import com.example.queue.repo.UserRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(value = "/application-test.properties")
@Sql(value = {"/before-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/after-test.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class UserControllerTest {
    private final TestEntities testEntities = new TestEntities();
    private User user;
    private Booking booking;

    @Autowired MockMvc mockMvc;
    @Autowired UserRepo userRepo;
    @Autowired BookingRepo bookingRepo;
    @Autowired JwtFilter jwtFilter;
    @Autowired JwtProvider jwtProvider;

    @BeforeEach
    void setUp(){
        user = testEntities.testUser();
        userRepo.save(user);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis() + 60000);

        booking = testEntities.testBooking(timestamp, user);
        bookingRepo.save(booking);

        jwtFilter.setAuthentication(jwtProvider.generateToken(user.login()));
    }

    @AfterEach
    void tearDown(){
        bookingRepo.delete(booking);
        userRepo.delete(user);
    }

    @Test
    void getUserInfo() throws Exception{

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user"))
                .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8))
                .isEqualTo(new UserDto().toUserDto(user).toString());
    }

    @Test
    void getActiveBookings() throws Exception{

    }

    @Test
    void createBook() throws Exception{
    }

    @Test
    void deleteBook() throws Exception{
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/user/delete-book")
                .param("bookId", String.valueOf(booking.bookId())))
                .andDo(print())
                .andReturn();
        assertThat(result.getResponse().getContentAsString(StandardCharsets.UTF_8))
                .isEqualTo(Constants.REMOVING_SUCCEED);
    }

    @Test
    void confirmBook() throws Exception{
    }
}