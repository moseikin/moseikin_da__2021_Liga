package com.example.queue.entity;

import lombok.Getter;

@Getter
public enum RolesEnum {

    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String role;

    RolesEnum(String role) {
        this.role = role;
    }
}
