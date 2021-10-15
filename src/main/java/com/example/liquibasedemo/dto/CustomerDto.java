package com.example.liquibasedemo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Setter
@Getter
@Accessors(chain = true)
public class CustomerDto {

    private UUID id;
    private String name;
    private Long rating;

}
