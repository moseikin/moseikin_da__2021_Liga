package com.example.queue.controller;

import com.example.queue.entity.BookingTime;
import com.example.queue.entity.CustomUserDetails;
import com.example.queue.service.BookingService;
import com.example.queue.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/user")
public class UserController {

    private final UserService userService;
    private final BookingService bookingService;


    @GetMapping
    public String getUserInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {
            return userService.getUserDto(userDetails.getUsername()).toString();
    }

    @GetMapping(path = "/active-bookings")
    public String getActiveBookings(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return bookingService.getAllActiveBooks(auth);
    }

    @PostMapping(path = "/create-book")
    public @ResponseBody String createBook(@RequestBody BookingTime bookingTime){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return bookingService.createBooking(bookingTime, auth.getName());

    }

    @PostMapping(path = "/delete-book")
    public @ResponseBody String deleteBook(@RequestBody Long bookId,
                                           @AuthenticationPrincipal CustomUserDetails userDetails){
        return bookingService.deleteBook(bookId, userDetails);
    }

    @GetMapping(path = "/confirm-book")
    public String confirmBook(@RequestParam String userId, @RequestParam String bookId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return bookingService.confirmBook(userId, bookId, auth.getName());
    }
}
