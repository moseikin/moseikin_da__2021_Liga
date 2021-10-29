package com.example.queue.entity.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequest {

    @NotBlank
    private String login;

    @NotBlank
    private String password;

    @NotEmpty
    private String name;

    @NotBlank
    private String lastname;

    @NotBlank
    @Email
    private String email;
}