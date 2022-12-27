package com.example.Datenow.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Setter
@Getter
@Entity
@Table(name="DATEUSER")
public class User {

    @Id
    @Column(name = "user_id")
    private Long id; // PK

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
//    private List<Post> posts = new ArrayList<>(); // 게시글들
}
