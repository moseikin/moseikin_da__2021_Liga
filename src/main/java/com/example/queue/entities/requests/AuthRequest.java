package com.example.queue.entities.requests;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
@Accessors(chain = true, fluent = true)
public class AuthRequest {

    @NotBlank
    private String login;

    @NotBlank
    private String password;
}