package com.example.queue.repo;

import com.example.queue.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;


@Repository
public interface BookingRepo extends JpaRepository<Booking, Long> {

    Booking findByBookId(Long bookId);

    @Query("select b.bookingTime from Booking b where b.bookingTime >= :start and b.bookingTime <= :end order by b.bookingTime")
    List<Timestamp> findAllBookingTime(@Param("start") Timestamp start, @Param("end") Timestamp end);


    Booking findByBookIdAndUserLogin(Long bookId, String login);


    // админ получает отсортированные по времени заявки от всех пользователей.
    // Нужна пагинация=======================================================================
    @Query("select b from Booking b where b.status = 'unconfirmed' or b.status = 'confirmed'")
    Page<Booking> findAllByStatusPageable(Pageable pageable);


    @Query("select b from Booking b where b.status = 'unconfirmed' or b.status = 'confirmed' order by b.bookingTime")
    List<Booking> findAllByStatus();

    // отсортированные по времени активные заявки пользователя
    @Query("select b from Booking b where (b.user.id = :userId) and " +
            "(b.status = 'unconfirmed' or b.status = 'confirmed') order by b.bookingTime")
    List<Booking> findAllByStatusAndUser(@Param("userId") long userId);


    Booking findFirstByBookingTime(Timestamp timestamp);




}
