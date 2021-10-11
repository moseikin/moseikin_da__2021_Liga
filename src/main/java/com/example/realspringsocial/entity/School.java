package com.example.realspringsocial.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "all_schools")
@Setter
@Getter
@NoArgsConstructor
public class School {

    @Id
    @Column(name = "school_number")
    private Long number;

    @Column(name = "address", length = 100)
    private String address;

    @OneToMany(mappedBy = "school", fetch = FetchType.LAZY)
    private List<Usr> usrList;

    public School(Long number, String address) {
        this.number = number;
        this.address = address;
    }

    @Override
    public String toString() {
        return "\n Школа №" + number +
                ((address != null) ? (", " + address) : ", адрес не указан");
    }
}
