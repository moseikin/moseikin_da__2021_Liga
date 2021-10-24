package com.example.queue.service;


import com.example.queue.Constants;
import com.example.queue.dto.BookingDto;
import com.example.queue.entity.Booking;
import com.example.queue.entity.BookingTime;
import com.example.queue.entity.User;
import com.example.queue.repo.BookingRepo;
import com.example.queue.repo.UserRepo;
import com.example.queue.service.interfaces.Notification;
import com.example.queue.component.BookingAbility;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Setter
@Getter
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
@PropertySource(value = {"classpath:queue.properties"})
public class BookingService {

    private final UserRepo userRepo;
    private final BookingRepo bookingRepo;
    private final ScheduledService scheduledService;
    private final BookingAbility ability;
    private final Notification notification;

    @Value(value = "${millisToConfirm}")
    private Long millisToConfirm;

    @Transactional
    public String createBooking(BookingTime bookingTime, String login) {
        // эта дата не в прошлом?
        if (!ability.checkDateNotInPast(bookingTime)) {
            return Constants.THIS_DAY_GONE;
        }

        // работаем в это время?
       if (!ability.checkIsInScheduleBounds(bookingTime)){
           return Constants.NOT_WORKING_TIME;
       }

        // преобразовать поля из сущности в timestamp
       Timestamp timestamp = ability.bookingTimeToTimestamp(bookingTime);

        // проверить, доступно ли время
        if (isBookingAvailable(timestamp)) {
            User user = userRepo.getUserByLogin(login);
            String newBook = insertBook(timestamp, user);
            return Constants.BOOKING_DONE + ": \n" + newBook;
        } else {
            return Constants.THIS_TIME_OCCUPIED;
        }
    }

    private String insertBook(Timestamp timestamp, User user) {
        // если заказ делается на время, меньшее, чем дается для подтверждения заказа, то подтверждение не нужно
        Boolean isConfirmed = ability.isNeedToConfirm(timestamp);

        Booking booking = new Booking();
        booking.bookingTime(timestamp)
                .isConfirmed(isConfirmed)
                .status(Constants.STATUS_UNCONFIRMED)
                .user(user);
        bookingRepo.save(booking);

        // высылаем письмо с ссылкой для подтверждения
        notification.confirmation(booking);

        // анулирование неподтвержденной заявки
        delayedAnnulling(booking.bookId());

        return booking.toString();
    }

    private void delayedAnnulling(long bookId) {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(()-> doAnnullingBook(bookId), millisToConfirm, TimeUnit.MINUTES);
    }

    public void doAnnullingBook(long bookId) {
        Optional<Booking> optional = bookingRepo.findById(bookId);
        if (optional.isPresent()){
            Booking booking = optional.get();
            if (booking.status().equals(Constants.STATUS_UNCONFIRMED)) {
                booking.status(Constants.STATUS_ANNULLED);
                bookingRepo.save(booking);
            }
        }
    }

    public boolean isBookingAvailable(Timestamp timestamp){
        List<Timestamp> allBookings = bookingRepo.findAllBookingTime();
        allBookings.sort(Comparator.comparingLong(Timestamp::getTime));
        // очередь пуста, можно записываться
        if (allBookings.isEmpty()) {
            return true;
        }

        return ability.isThereSpaceInQueue(timestamp, allBookings);
    }

    @Transactional
    public String deleteBook(Long bookId, UserDetails userDetails) {
        Booking booking;
        if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            booking = findById(bookId);
        } else {
            booking = bookingRepo.findByBookIdAndUserLogin(bookId, userDetails.getUsername());
        }
        if (booking != null) {
            return doRemoveBook(booking);
        } else {
            return Constants.CANNOT_FIND_BOOKING;
        }

    }

    public Booking findById(long bookId) {
        Optional<Booking> optional = bookingRepo.findById(bookId);
        return optional.orElse(null);
    }

    public String doRemoveBook(Booking booking){
        bookingRepo.delete(booking);
        return Constants.REMOVING_SUCCEED;
    }

    public String getNearestBook() {
        List <BookingDto> bookings = doGetAllActiveBook();
        for(BookingDto bookingDto : bookings) {
            if (bookingDto.bookingTime().getTime() >= System.currentTimeMillis()) {
                return bookingDto.toString();
            }
        }
        return Constants.CANNOT_FIND_NEAREST_ACTIVE;
    }

    public List<BookingDto> doGetAllActiveBook() {
        List <Booking> bookings = bookingRepo.findAllByStatus();
        bookings.sort(Comparator.comparingLong(o -> o.bookingTime().getTime()));
       return bookings.stream()
                .map(new BookingDto()::toBookingDto)
                .collect(Collectors.toList());
    }

    public String getAllActiveBooks(Authentication auth) {
        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return doGetAllActiveBook().toString();
        } else {
            return doGetUserActiveBookings(auth.getName());
        }
    }

    @Transactional
    public String confirmBook(String userId, String bookId, String name) {
        // id из письма и авторизованного юзера не совпадают
        User user = userRepo.getUserByLogin(name);
        if (!Long.valueOf(userId).equals(user.id())) {
            return Constants.LOGIN_UNTO_SAME_USER;
        }

        Booking booking = findById(Long.parseLong(bookId));
        if (booking == null) {
            return Constants.CANNOT_FIND_BOOKING;
        } else {
            setConfirmed(booking);
            // уведомление о том, что заявка подтверждена
            notification.confirmed(booking);
        }

        return Constants.CONFIRMED;
    }

    @Transactional
    public void setConfirmed(Booking booking){
        booking.status(Constants.STATUS_CONFIRMED);
        bookingRepo.save(booking);
    }

    // заявки пользователя. Dto
    private String doGetUserActiveBookings(String name) {
        List<BookingDto> booking = bookingRepo.findAllByStatus()
                .stream()
                .filter(b -> b.user().id().equals(userRepo.getUserByLogin(name).id()))
                .map(new BookingDto()::toBookingDto)
                .collect(Collectors.toList());
        return booking.toString();
    }
}
