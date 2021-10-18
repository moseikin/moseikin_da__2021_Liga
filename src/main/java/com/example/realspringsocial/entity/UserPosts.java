package com.example.realspringsocial.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name = "user_posts")
@Setter
@Getter
@NoArgsConstructor
@Accessors(chain = true)
public class UserPosts {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "text", length = 2048)
    private String text;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Usr usr;

    public UserPosts(Usr usr, String text) {
        this.text = text;
        this.usr = usr;
    }

    @Override
    public String toString() {
        return "\n id=" + id +
                ", " + usr.getName() +
                " " + usr.getLastName() +
                ", " + text;
    }
}
