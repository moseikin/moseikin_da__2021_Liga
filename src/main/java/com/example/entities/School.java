package com.example.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name = "school")
@Getter                     // Lombok
@Setter
@NoArgsConstructor
@Accessors(chain = true)    // цепочка сеттеров в одном выражении

public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column()
    private Long schoolNumber;

    @Embedded
    private Address address;

    @OneToOne(cascade = CascadeType.REMOVE)
    private People people;

    public School(
//            Long schoolNumber,
            Address address) {
//        this.schoolNumber = schoolNumber;
        this.address = address;
    }
}
