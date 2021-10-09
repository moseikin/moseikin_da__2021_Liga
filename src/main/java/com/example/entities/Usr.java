package com.example.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usr")
@Getter
@Setter
@NoArgsConstructor
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

    @ManyToOne
    @JoinColumn(name = "school_number")
    private School school;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usr")
    private List<UserPosts> userPostsList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usr")
    private List<Friends> friendsList = new ArrayList<>();

    public Usr(String name, String lastName, Integer age) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
    }

    @Override
    public String toString() {
        return "\n Usr{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", school=" + ((school != null) ? school.getNumber() : "null")  +
                ", userPostsList=" + userPostsList +
                '}';
    }
}
