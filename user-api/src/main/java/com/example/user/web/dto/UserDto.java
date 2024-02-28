package com.example.user.web.dto;

import com.example.user.core.entity.User;
import lombok.Getter;

@Getter
public class UserDto {

    private final int id;
    private final String username;
    private final String email;

    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }

    public static UserDto transferToDto(User user) {
        return new UserDto(user);
    }

}
