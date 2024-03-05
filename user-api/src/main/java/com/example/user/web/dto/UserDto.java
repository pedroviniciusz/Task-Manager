package com.example.user.web.dto;

import com.example.user.core.entity.User;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class UserDto {

    private final int id;
    private final String username;
    private final String cpf;
    private final String email;

    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.cpf = user.getCpf();
        this.email = user.getEmail();
    }

    public static UserDto transferToDto(User user) {
        return new UserDto(user);
    }

    public static List<UserDto> transferToDtoList(List<User> users) {
        return users.stream().map(UserDto::new).collect(Collectors.toList());
    }

}
