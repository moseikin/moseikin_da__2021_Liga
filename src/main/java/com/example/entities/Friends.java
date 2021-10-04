package com.example.entities;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name = "friends")
@Getter                     // Lombok
@Setter
@NoArgsConstructor
@Accessors(chain = true)    // цепочка сеттеров в одном выражении
public class Friends {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "owner_id")
    @NotNull
    private Long owner_id;

    @Column(name = "friend")
    @NotNull
    private Long friend;

    @ManyToOne
    @JoinColumn(name = "people_id")
    private People people;

}
