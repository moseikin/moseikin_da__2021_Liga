package com.example.queue.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserDtoTest {

    UserDto userDto = new UserDto();

    @Test
    void testName() {
        assertThat(userDto.name()).isNull();
        userDto.name("testName");
        assertThat(userDto.name()).isNotNull();
    }

    @Test
    void testLastName() {
        assertThat(userDto.lastName()).isNull();
        userDto.lastName("testName");
        assertThat(userDto.lastName()).isNotNull();
    }

    @Test
    void testLogin() {
        assertThat(userDto.login()).isNull();
        userDto.login("testName");
        assertThat(userDto.login()).isNotNull();
    }

    @Test
    void testEMail() {
        assertThat(userDto.eMail()).isNull();
        userDto.eMail("testName@test.test");
        assertThat(userDto.eMail()).isNotNull();
    }
}