package com.example.queue.repo;

import com.example.queue.entity.Booking;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;


@Repository
public interface BookingRepo extends CrudRepository<Booking, Long> {

    List<Booking> findAllByIsConfirmedTrue();

    @Query("select b.bookingTime from Booking b")
    List<Timestamp> findAllBookingTime();

    Booking findByBookIdAndUserLogin(Long bookId, String login);


    @Query("select b from Booking b where b.status = 'unconfirmed' or b.status = 'confirmed'")
    List<Booking> findAllByStatus();

}
