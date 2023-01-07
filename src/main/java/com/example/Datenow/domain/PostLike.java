package com.example.Datenow.domain;

<<<<<<< HEAD:src/main/java/com/example/Datenow/domain/PostLike.java
public class PostLike {
=======
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


>>>>>>> 270ab00d53731baf2d47731e8cbfa0408230fdc6:src/main/java/com/example/Datenow/domain/User.java
}
