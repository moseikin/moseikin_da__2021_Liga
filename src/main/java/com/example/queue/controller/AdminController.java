package com.example.queue.controller;

import com.example.queue.entity.CustomUserDetails;
import com.example.queue.service.AdminService;
import com.example.queue.service.BookingService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Api(value = "Admin CRUD operations")
@RequestMapping(path = "/admin")
public class AdminController {

    private final BookingService bookingService;
    private final AdminService adminService;

    @PostMapping(path = "/delete-book")
    public String deleteBook(@RequestBody Long bookId,
                           @AuthenticationPrincipal CustomUserDetails userDetails){

        return bookingService.deleteBook(bookId, userDetails);
    }

    @PutMapping(path = "/set-appeared")
    public String setAppeared(long bookId){
        return adminService.setAppeared(bookId);
    }

    @PutMapping(path = "/set-completed")
    public String setCompleted(long bookId){
        return adminService.setCompleted(bookId);
    }

    @PutMapping(path = "/set-annulled")
    public String setAnnulled(long bookId){
        return adminService.setAnnulled(bookId);
    }

    @GetMapping(path = "/soon")
    public String getNearestBook(){
        return bookingService.getNearestBook();
    }

    @GetMapping(path = "/active-bookings")
    public String getAllBook(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return bookingService.getAllActiveBooks(auth);
    }
}
