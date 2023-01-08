package com.example.Datenow.domain.User;

import com.example.Datenow.domain.Comment;
import com.example.Datenow.domain.Post;
import com.example.Datenow.domain.PostLike;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String password;
    private String email;
    private String name;
    private LocalDate birth;
    @Column(name = "phone_num")
    private Long phoneNum;

    @Column(name = "profile_img")
    private String profileImg;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @CreatedDate
    private LocalDateTime created_at;
    @LastModifiedDate
    private LocalDateTime modified_at;

    @Builder
    public User(String password, String email, String name, LocalDate birth, Long phoneNum, String profileImg, Gender gender, LocalDateTime created_at) {
        this.password = password;
        this.email = email;
        this.name = name;
        this.birth = birth;
        this.phoneNum = phoneNum;
        this.profileImg = profileImg;
        this.gender = gender;
        this.created_at = created_at;
    }

    /*
    // Post
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Post> postList = new ArrayList<>();

    // Comment
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<>();

    // PostLike
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<PostLike> postLikeList = new ArrayList<>();
     */
}
