package com.example.Datenow.DTO;

import com.example.Datenow.domain.User.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserResponseDTO {

    private Long id;

    private String email;

    private String password;

    private String name;

    private LocalDateTime createBy;

    /*
    public static UserResponseDTO touserResponseDTO(User user){
        return UserResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
    */


}
