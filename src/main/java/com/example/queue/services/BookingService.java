package com.example.queue.services;


import com.example.queue.Constants;
import com.example.queue.dto.BookingDto;
import com.example.queue.entities.Booking;
import com.example.queue.entities.BookingTime;
import com.example.queue.entities.enums.StatusesEnum;
import com.example.queue.entities.User;
import com.example.queue.repo.BookingRepo;
import com.example.queue.repo.UserRepo;
import com.example.queue.services.interfaces.Notification;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
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
    private final CalendarService calendarService;
    private final Notification notification;

    @Value(value = "${millisToConfirm}")
    private Long millisToConfirm;

    @Transactional
    public String createBooking(BookingTime bookingTime, Authentication auth) {
        // эта дата не в прошлом?
        if (!calendarService.checkDateNotInPast(bookingTime)) {
            return Constants.THIS_DAY_GONE;
        }

        // работаем в это время?
       if (!calendarService.checkIsInScheduleBounds(bookingTime)){
           return Constants.NOT_WORKING_TIME;
       }

        // преобразовать поля из сущности в timestamp
       Timestamp timestamp = calendarService.bookingTimeToTimestamp(bookingTime);

        // проверить, доступно ли время
        if (isBookingAvailable(bookingTime, timestamp)) {
            User user = userRepo.getUserByLogin(auth.getName());
            String newBook = insertBook(timestamp, user);
            return Constants.BOOKING_DONE + ": \n" + newBook;
        } else {
            return Constants.THIS_TIME_OCCUPIED;
        }
    }

    public boolean isBookingAvailable(BookingTime bookingTime, Timestamp timestamp){

        // список timestamp за весь день
        List<Timestamp> allBookings = calendarService.getAllTimeStampsThisDay(bookingTime);

        // очередь пуста, можно записываться
        if (allBookings.isEmpty()) {
            return true;
        }
        return calendarService.isThereSpaceInQueue(timestamp, allBookings);
    }

    private String insertBook(Timestamp timestamp, User user) {
        Booking booking = new Booking();
        booking.bookingTime(timestamp)
                .status(StatusesEnum.STATUS_UNCONFIRMED.getStatus())
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

    // анулирование неподтвержденного заказа
    @Transactional
    public void doAnnullingBook(long bookId) {
        Booking booking = bookingRepo.findByBookId(bookId);
        if (booking != null){
            if (booking.status().equals(StatusesEnum.STATUS_UNCONFIRMED.getStatus())) {
                booking.status(StatusesEnum.STATUS_ANNULLED.getStatus());
                bookingRepo.save(booking);
                notification.annulled(booking);
            }
        }
    }

    @Transactional
    public String deleteBook(Long bookId, Authentication auth) {
        Booking booking;
        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            booking = bookingRepo.findByBookId(bookId);
        } else {
            booking = bookingRepo.findByBookIdAndUserLogin(bookId, auth.getName());
        }

        if (booking != null) {
            return doDeleteBook(booking);
        } else {
            return Constants.CANNOT_FIND_BOOKING;
        }
    }

    public String doDeleteBook(Booking booking){
        bookingRepo.delete(booking);
        return Constants.REMOVING_SUCCEED;
    }

    public String getNearestBook() {
        Optional<BookingDto> optional = bookingRepo.findAllByStatus()
                .stream()
                .map(new BookingDto()::toBookingDto)
                .findFirst()
                .filter(bookingDto -> bookingDto.bookingTime().getTime() >= System.currentTimeMillis());
        if (optional.isPresent()) {
            return optional.get().toString();
        } else {
            return Constants.CANNOT_FIND_NEAREST_ACTIVE;
        }
    }

    public List<BookingDto> doGetAllActiveBook(Pageable pageable) {
        return bookingRepo.findAllByStatusPageable(pageable)
                .stream()
                .map(new BookingDto()::toBookingDto)
                .collect(Collectors.toList());
    }

    public String getAllActiveBooks(Authentication auth, Pageable pageable) {
        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return doGetAllActiveBook(pageable).toString();
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

        Booking booking = bookingRepo.findByBookId(Long.parseLong(bookId));
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
        booking.status(StatusesEnum.STATUS_CONFIRMED.getStatus());
        bookingRepo.save(booking);
    }

    // активные (confirmed или unconfirmed) заявки конкретного пользователя
    private String doGetUserActiveBookings(String name) {
        return bookingRepo.findAllByStatusAndUser(userRepo.getUserByLogin(name).id())
                .stream()
                .map(new BookingDto()::toBookingDto).collect(Collectors.toList()).toString();
    }


}
