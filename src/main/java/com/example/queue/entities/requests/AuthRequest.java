package com.example.queue.entities.requests;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
public class AuthRequest {

    @NotBlank
    private String login;

    @NotBlank
    private String password;
}