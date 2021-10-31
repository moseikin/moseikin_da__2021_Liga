package com.example.queue.entities;

import com.example.queue.dto.UserDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "booking")
@Setter
@Getter
@Accessors(fluent = true, chain = true)
@RequiredArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "booking_time")
    private Timestamp bookingTime;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @Override
    public String toString() {
        return "\n bookId=" + bookId +
                ", bookingTime=" + bookingTime +
                ", user=" + new UserDto().toUserDto(user);
    }
}
