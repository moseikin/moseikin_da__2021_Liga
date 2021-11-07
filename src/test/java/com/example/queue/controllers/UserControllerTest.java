package com.example.queue.controllers;

import com.example.queue.CommonTestMethods;
import com.example.queue.Constants;
import com.example.queue.TestEntities;
import com.example.queue.config.JwtFilter;
import com.example.queue.config.JwtProvider;
import com.example.queue.dto.BookingDto;
import com.example.queue.dto.UserDto;
import com.example.queue.entities.Booking;
import com.example.queue.entities.BookingTime;
import com.example.queue.entities.User;
import com.example.queue.repo.BookingRepo;
import com.example.queue.repo.UserRepo;
import com.example.queue.services.CalendarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Calendar;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(value = "/application-test.properties")
@Sql(value = {"/before-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/after-test.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class UserControllerTest {
    private final TestEntities testEntities = new TestEntities();
    private final CommonTestMethods methods = new CommonTestMethods();
    private User user;
    private Booking booking;

    @Autowired MockMvc mockMvc;
    @Autowired UserRepo userRepo;
    @Autowired BookingRepo bookingRepo;
    @Autowired JwtFilter jwtFilter;
    @Autowired JwtProvider jwtProvider;
    @Autowired ObjectMapper objectMapper;
    @Autowired CalendarService calendarService;

    @BeforeEach
    void setUp(){
        user = testEntities.testUser();
        userRepo.save(user);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis() + 60000);

        booking = testEntities.testBooking(timestamp, user);

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
        bookingRepo.save(booking);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user/active-bookings"))
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertThat(result.substring(1, result.length() - 1))
                .isEqualTo(new BookingDto().toBookingDto(booking).toString());
    }

    @Test
    void createBook() throws Exception{
        Calendar calendar = Calendar.getInstance();
        BookingTime bookingTime = new BookingTime();
        bookingTime.setYear(calendar.get(Calendar.YEAR));
        bookingTime.setMonth(calendar.get(Calendar.MONTH));
        // it must be working time and not the past
        bookingTime.setDay(calendar.get(Calendar.DATE) + 1);
        bookingTime.setHour(12);
        bookingTime.setMinute(0);
        booking.bookingTime(calendarService.bookingTimeToTimestamp(bookingTime));

        String requestBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(bookingTime);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/create-book")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andReturn();

        String result = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);

        booking.bookId(methods.ejectId(result));

        assertThat(result).isEqualTo(Constants.BOOKING_DONE + ": \n" + booking);
    }

    @Test
    void createBook_ExpectIncorrectBookingParams() throws Exception{
        BookingTime bookingTime = new BookingTime();
        String requestBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(bookingTime);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/create-book")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8))
                .isEqualTo(Constants.INCORRECT_BOOKING_PARAMS);
    }

    @Test
    void deleteBook() throws Exception{
        bookingRepo.save(booking);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/user/delete-book")
                .param("bookId", String.valueOf(booking.bookId())))
                .andDo(print())
                .andReturn();
        assertThat(result.getResponse().getContentAsString(StandardCharsets.UTF_8))
                .isEqualTo(Constants.REMOVING_SUCCEED);
    }

    @Test
    void confirmBook() throws Exception{
        bookingRepo.save(booking);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user/confirm-book")
                .param("userId", String.valueOf(user.id()))
                .param("bookId", String.valueOf(booking.bookId())))
                .andReturn();
        assertThat(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8))
                .isEqualTo(Constants.CONFIRMED);
    }
}