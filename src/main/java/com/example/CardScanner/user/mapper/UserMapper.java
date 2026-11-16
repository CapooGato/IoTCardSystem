package com.example.CardScanner.user.mapper;

import com.example.CardScanner.user.dto.UserDTO;
import com.example.CardScanner.user.entity.User;

public class UserMapper {
    private UserMapper(){
        /* This class should not be initialised */
    }

    public static UserDTO toDto(User user){
        return UserDTO.builder()
                .id(user.getId())
                .login(user.getLogin())
                .password(user.getPassword())
                .cardId(user.getCard() != null ? user.getCard().getId() : null)
                .build();
    }

    public static User toEntity(UserDTO userDTO){
        return User.builder()
                .id(userDTO.getId())
                .login(userDTO.getLogin())
                .password(userDTO.getPassword())
                .build();
    }
}
