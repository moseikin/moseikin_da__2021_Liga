package com.example.queue.controllers;

import com.example.queue.Constants;
import com.example.queue.entities.BookingTime;
import com.example.queue.services.BookingService;
import com.example.queue.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/user")
public class UserController {

    private final UserService userService;
    private final BookingService bookingService;


    @GetMapping
    public String getUserInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.getUserDto(auth).toString();
    }

    @GetMapping(path = "/active-bookings")
    public String getActiveBookings(@PageableDefault(sort = { "booking_time" }, direction = Sort.Direction.ASC)
                                                Pageable pageable){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return bookingService.getAllActiveBooks(auth, pageable);
    }

    @PostMapping(path = "/create-book")
    public @ResponseBody String createBook(@RequestBody @Valid BookingTime bookingTime,
                                           BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Constants.INCORRECT_BOOKING_PARAMS;
        } else {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            return bookingService.createBooking(bookingTime, auth);
        }

    }

    @PostMapping(path = "/delete-book")
    public @ResponseBody String deleteBook(@RequestParam Long bookId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return bookingService.deleteBook(bookId, auth);
    }

    @GetMapping(path = "/confirm-book")
    public String confirmBook(@RequestParam String userId, @RequestParam String bookId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return bookingService.confirmBook(userId, bookId, auth.getName());
    }
}
