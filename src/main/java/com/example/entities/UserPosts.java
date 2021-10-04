package com.example.entities;


import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table (name = "people_posts")
@Getter                     // Lombok
@Setter
@NoArgsConstructor
@Accessors(chain = true)    // цепочка сеттеров в одном выражении
public class UserPosts {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "people_id")
    @NotNull
    private Long people_id;

    @Column(name = "heading", length = 100)
    @NotNull
    private String heading;

    @Column(name = "content", length = 2048)
    @NotNull
    private String content;

    @ManyToOne
    @JoinColumn(name = "people_id", insertable = false, updatable = false)
    private People people;

}
