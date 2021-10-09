package com.example.entities;

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
    @GeneratedValue
//    @Column(name = "id")
    private Long id;

    @Column(name = "text", length = 2048)
    private String text;

    @ManyToOne
    @JoinColumn(name = "usr_id")
    private Usr usr;

    public UserPosts(Usr usr, String text) {
        this.text = text;
        this.usr = usr;
//        this.user_id = users.getId();
    }
}
