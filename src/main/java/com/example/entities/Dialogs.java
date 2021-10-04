package com.example.entities;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "dialogs")
@Getter                     // Lombok
@Setter
@NoArgsConstructor
@Accessors(chain = true)    // цепочка сеттеров в одном выражении
public class Dialogs {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "message_date")
    @NotNull
    private Date message_date;

    @Column(name = "first_id")
    @NotNull
    private Long firstId;

    @Column(name = "second_id")
    @NotNull
    private Long secondId;

    @Column(name = "message")
    @NotNull
    private String message;

    @ManyToOne
    @JoinColumn(name = "people_id")
    private People people;
}
