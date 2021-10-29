package com.example.queue.entity.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AuthRequest {

    @NotEmpty
    private String login;

    @NotEmpty
    private String password;
}