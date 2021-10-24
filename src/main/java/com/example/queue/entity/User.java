package com.example.queue.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "usr")
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true, fluent = true)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @Column (name = "login", unique = true, length = 10)
    private String login;

    @Column(name = "name", length = 20)
    private String name;

    @Column(name = "lastname", length = 20)
    private String lastName;

    @Column(name = "pass")
    private String pass;

    @Column(name = "role", length = 10)
    private String role;

    @Column(name = "email", length = 50)
    private String eMail;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private List<Booking> bookings = new ArrayList<>();

    @Override
    public String toString() {
        return "\n id=" + id +
                ", login='" + login +
                ", name='" + name +
                ", lastName='" + lastName +
                ", role='" + role +
                ", eMail='" + eMail;
    }
}
