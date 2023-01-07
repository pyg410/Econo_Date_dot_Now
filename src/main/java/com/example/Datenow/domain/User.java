package com.example.Datenow.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@Builder
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String password;
    private String email;
    private String nickname;
    private LocalDate birth;
    private Long phone_num;
    private String profile_img;
    private Boolean gender;
    private LocalDateTime created_at;
    private LocalDateTime modified_at;


}
