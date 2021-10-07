package com.example.entities;

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

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "school", fetch = FetchType.LAZY)
    private List<Usr> usrList;

    public School(Long number, String address) {
        this.number = number;
        this.address = address;
    }
}
