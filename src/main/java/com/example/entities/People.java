package com.example.entities;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "people")
// добавлять кэши?
@Getter                     // Lombok
@Setter
@NoArgsConstructor
@Accessors(chain = true)    // цепочка сеттеров в одном выражении
public class People {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "people_id")
    private Long id;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private Integer schoolNumber;

    @OneToOne(mappedBy = "people")
    @JoinColumn(name = "schoolNumber")
    private School school;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Dialogs> dialogsList;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Friends> friendsList;

    public People(String name, String surname, Integer schoolNumber) {
        this.name = name;
        this.surname = surname;
        this.schoolNumber = schoolNumber;
    }
}
