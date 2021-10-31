package com.example.queue.controllers;

import com.example.queue.services.AdminService;
import com.example.queue.services.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/admin")
public class AdminController {

    private final BookingService bookingService;
    private final AdminService adminService;

    @PostMapping(path = "/delete-book")
    public String deleteBook(@RequestParam Long bookId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return bookingService.deleteBook(bookId, auth);
    }

    @PutMapping(path = "/set-appeared")
    public String setAppeared(@RequestParam Long bookId){
        return adminService.setAppeared(bookId);
    }

    @PutMapping(path = "/set-completed")
    public String setCompleted(@RequestParam Long bookId){
        return adminService.setCompleted(bookId);
    }

    @PutMapping(path = "/set-annulled")
    public String setAnnulled(@RequestParam Long bookId){
        return adminService.setAnnulled(bookId);
    }

    @GetMapping(path = "/soon")
    public String getNearestBook(){
        return bookingService.getNearestBook();
    }

    @GetMapping(path = "/active-bookings")
    public String getAllBook(@PageableDefault(sort = "bookingTime", direction = Sort.Direction.ASC, size = 4) Pageable pageable){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return bookingService.getAllActiveBooks(auth, pageable);
    }
}
