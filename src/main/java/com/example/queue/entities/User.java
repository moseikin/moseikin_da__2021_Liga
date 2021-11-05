package com.example.queue.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @NotBlank(message = "blank role")
    @Column(name = "role", length = 10)
    private String role;

    @NotBlank(message = "blank email")
    @Email(regexp = ".+@.+\\..+")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) && login.equals(user.login) &&
                name.equals(user.name) && lastName.equals(user.lastName) &&
                pass.equals(user.pass) && role.equals(user.role) && eMail.equals(user.eMail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, name, lastName, pass, role, eMail);
    }
}
