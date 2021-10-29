package com.example.queue.entity;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @NotBlank
    @Column (name = "login", unique = true, length = 10)
    private String login;

    @NotBlank
    @Column(name = "name", length = 20)
    private String name;

    @NotBlank
    @Column(name = "lastname", length = 20)
    private String lastName;

    @NotBlank
    @Column(name = "pass")
    private String pass;

    @NotBlank
    @Column(name = "role", length = 10)
    private String role;

    @NotBlank
    @Email
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
