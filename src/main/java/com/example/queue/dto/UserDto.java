package com.example.queue.dto;

import com.example.queue.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor
public class UserDto {
    private String name;
    private String lastName;
    private String login;
    private String eMail;


    public UserDto toUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.name(user.name())
                .lastName(user.lastName())
                .login(user.login())
                .eMail(user.eMail());
        return userDto;
    }

    @Override
    public String toString() {
        return "\n Имя, Фамилия, Логин: " + name +
                ", " + lastName +
                ", " + login +
                ", email: " + eMail;
    }
}
