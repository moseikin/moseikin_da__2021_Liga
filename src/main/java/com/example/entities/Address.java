package com.example.entities;

import com.sun.istack.NotNull;

import javax.persistence.*;


@Embeddable
public class Address {

//    @Id()
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "id")
//    private Long id;

    @Column(name = "country")
    @NotNull
    private String country;

    @Column(name = "city")
    @NotNull
    private String city;

    @Column(name = "street")
    @NotNull
    private String street;

    @Column(name = "building")
    @NotNull
    private Integer building;

    public Address(String country, String city, String street, Integer building) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.building = building;
    }

    public Address() {

    }
}
