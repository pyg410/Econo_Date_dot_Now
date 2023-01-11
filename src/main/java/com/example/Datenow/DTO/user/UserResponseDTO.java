package com.example.Datenow.DTO.user;

import com.example.Datenow.domain.User.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class UserResponseDTO {

    private Long id;

    private String email;

    private String password;

    private String name;

    private LocalDateTime createBy;


    public static UserResponseDTO toResponseDTO(User user){
        return UserResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .createBy(user.getCreated_at())
                .build();
    }



}
