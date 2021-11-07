package com.example.queue.entities.requests;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
// @Accessors causes InvalidDefinitionException: No serializer found for class
public class AuthRequest {

    @NotBlank
    private String login;

    @NotBlank
    private String password;
}