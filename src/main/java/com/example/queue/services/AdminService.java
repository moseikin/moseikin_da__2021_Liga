package com.example.queue.services;

import com.example.queue.Constants;
import com.example.queue.entities.Booking;
import com.example.queue.entities.enums.RolesEnum;
import com.example.queue.entities.enums.StatusesEnum;
import com.example.queue.entities.User;
import com.example.queue.repo.BookingRepo;
import com.example.queue.repo.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final BookingRepo bookingRepo;

    @Transactional
    public User addAdmin(){
        User user = new User();
        user.login("admin")
                .pass(passwordEncoder.encode(("admin")))
                .name("Administrator")
                .lastName("Adminov")
                .role(RolesEnum.ADMIN.getRole())
                .eMail("111111@dsfds.ru");
        userRepo.save(user);
        return user;
    }

    @Transactional
    public String setAppeared(long bookId) {
        Booking booking = bookingRepo.findByBookId(bookId);
        if (booking != null){
            booking.status(StatusesEnum.STATUS_APPEARED.getStatus());
            bookingRepo.save(booking);
            return Constants.APPEARED;
        } else {
            return Constants.CANNOT_FIND_BOOKING;
        }
    }

    @Transactional
    public String setCompleted(long bookId) {
        Booking booking = bookingRepo.findByBookId(bookId);
        if (booking != null){
            booking.status(StatusesEnum.STATUS_COMPLETED.getStatus());
            bookingRepo.save(booking);
            return Constants.COMPLETED;
        } else {
            return Constants.CANNOT_FIND_BOOKING;
        }
    }

    @Transactional
    public String setAnnulled(long bookId) {
        Booking booking = bookingRepo.findByBookId(bookId);
        if (booking != null){
            booking.status(StatusesEnum.STATUS_ANNULLED.getStatus());
            bookingRepo.save(booking);
            return Constants.ANNULLED;
        } else {
            return Constants.CANNOT_FIND_BOOKING;
        }
    }
}
