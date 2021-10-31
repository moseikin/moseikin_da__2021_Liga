package com.example.queue.entities.enums;

import lombok.Getter;

@Getter
public enum StatusesEnum {
    STATUS_UNCONFIRMED("unconfirmed"),
    STATUS_CONFIRMED("confirmed"),
    STATUS_APPEARED("appeared"),
    STATUS_COMPLETED("completed"),
    STATUS_ANNULLED("annulled");

    private final String status;

    StatusesEnum(String status) {
        this.status = status;
    }
}
