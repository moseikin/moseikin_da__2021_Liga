package com.example.queue.controllers;

import com.example.queue.TestEntities;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(value = "/application-test.properties")
@Sql(value = {"/before-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/after-test.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class MainControllerTest {
    private final TestEntities testEntities = new TestEntities();
    private User user;
    private Booking booking;
    private final Calendar calendar = Calendar.getInstance();

    @Autowired MockMvc mockMvc;
    @Autowired UserRepo userRepo;
    @Autowired BookingRepo bookingRepo;


    @BeforeEach
    void setUp(){
        user = testEntities.testUser();
        userRepo.save(user);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis() + 60000);

        booking = testEntities.testBooking(timestamp, user);
        bookingRepo.save(booking);
    }

    @AfterEach
    void tearDown(){
        bookingRepo.delete(booking);
        userRepo.delete(user);

    }

    @Test
    void getThisDayBookings() throws Exception{
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/bookings")
                            .param("year", String.valueOf(calendar.get(Calendar.YEAR)))
                .param("month", String.valueOf(calendar.get(Calendar.MONTH)))
                .param("day", String.valueOf(calendar.get(Calendar.DATE))))
            .andReturn();
        String result = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertThat(result.substring(1, result.length() - 1))
                .isEqualTo(booking.bookingTime().toString());
    }
}