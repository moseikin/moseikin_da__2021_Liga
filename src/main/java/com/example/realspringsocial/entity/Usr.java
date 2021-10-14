package com.example.realspringsocial.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usr")
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class Usr {

    @Id
    @GeneratedValue
    @Column(name = "usr_id")
    private Long id;

    @Column(name = "name", length = 20)
    private String name;

    @Column(name = "lastname", length = 20)
    private String lastName;

    @Column(name = "age")
    private Integer age;

    @Column(name = "p")
    private String p;

    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "school_number")
    private School school;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usr")
    private List<UserPosts> userPostsList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usr")
    private List<Friends> friendsList = new ArrayList<>();

    public Usr(String name, String lastName, Integer age, School school) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.school = school;
    }

    @Override
    public String toString() {
        return "\n id " + id + " " +
                name + " " + lastName +
                ", " + age + " лет" +
                ((school != null) ? (", " + school.getNumber() + " школа") : "");
    }
}
