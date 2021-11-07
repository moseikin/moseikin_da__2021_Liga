package com.example.queue.controllers;

import com.example.queue.Constants;
import com.example.queue.TestEntities;
import com.example.queue.config.JwtFilter;
import com.example.queue.config.JwtProvider;
import com.example.queue.dto.BookingDto;
import com.example.queue.entities.Booking;
import com.example.queue.entities.User;
import com.example.queue.repo.BookingRepo;
import com.example.queue.repo.UserRepo;
import com.example.queue.services.AdminService;
import com.example.queue.services.BookingService;
import com.example.queue.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(value = "/application-test.properties")
@Sql(value = {"/before-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/after-test.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class AdminControllerTest {
    private final TestEntities testEntities = new TestEntities();
    private Booking booking;

    @Autowired MockMvc mockMvc;
    @Autowired BookingService bookingService;
    @Autowired UserService userService;
    @Autowired UserRepo userRepo;
    @Autowired BookingRepo bookingRepo;
    @Autowired AdminService adminService;
    @Autowired ObjectMapper objectMapper;
    @Autowired JwtProvider jwtProvider;
    @Autowired JwtFilter jwtFilter;

    @BeforeEach
    void setUp(){
        User user = testEntities.testUser();
        userRepo.save(user);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis() + 60000);
        booking = testEntities.testBooking(timestamp, user);
        bookingRepo.save(booking);
        User admin = adminService.addAdmin();

        jwtFilter.setAuthentication(jwtProvider.generateToken(admin.login()));
    }

    @AfterEach
    void tearDown(){
        bookingRepo.delete(booking);
        userRepo.delete(booking.user());
    }

    @Test
    void deleteBook() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/admin/delete-book")
                .param("bookId", String.valueOf(booking.bookId())))
                .andDo(print())
                .andReturn();
        assertThat(result.getResponse().getContentAsString(StandardCharsets.UTF_8))
                .isEqualTo(Constants.REMOVING_SUCCEED);
    }

    @Test
    void setAppeared() throws Exception{
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/admin/set-appeared")
                .param("bookId", String.valueOf(booking.bookId())))
                .andReturn();
        assertThat(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8))
                .isEqualTo(Constants.APPEARED);
    }

    @Test
    void setCompleted() throws Exception{
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/admin/set-completed")
                .param("bookId", String.valueOf(booking.bookId())))
                .andReturn();
        assertThat(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8))
                .isEqualTo(Constants.COMPLETED);
    }

    @Test
    void setAnnulled() throws Exception{
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/admin/set-annulled")
                .param("bookId", String.valueOf(booking.bookId())))
                .andReturn();
        assertThat(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8))
                .isEqualTo(Constants.ANNULLED);
    }

    @Test
    void getNearestBook() throws Exception{
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/admin/soon"))
                .andReturn();
        assertThat(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8))
                .isEqualTo(new BookingDto().toBookingDto(booking).toString());
    }

    @Test
    void getAllBook() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/admin/active-bookings"))
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertThat(result.substring(1, result.length() - 1))
                .isEqualTo(new BookingDto().toBookingDto(booking).toString());
    }
}