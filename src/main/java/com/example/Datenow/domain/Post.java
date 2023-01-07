package com.example.Datenow.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.example.Datenow.domain.User.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 생성자를 통해서 값 변경 목적으로 접근하는 메시지들 차단
@Entity
public class Post{

    @Id
    @Column(name = "board_id") // 엔티티는 타입(ex:Meber)가 있으므로, id 필드만으로 구별이 쉽지만, DB의 경우 타입이 없기에 id라고 이름을 지으면 구별하기 어렵다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 생성을 DB에 위임
    private Long id; // PK

    private String title; // 제목

    private String content; // 내용

    @ManyToOne(fetch = FetchType.LAZY) // FetchType.Lazy는 JPA의 프록시와 지연로딩의 기초적인 내용을 공부해야한다.
    @JoinColumn(name = "user_id") // FK이름 지정
    private User user;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private Category category;

    private int viewCnt; // 조회 수

    private int scrapCnt; // 스크랩 수

    private int recommendCnt; // 좋아요 수

    @CreatedDate
    private LocalDateTime createdDate = LocalDateTime.now(); // 생성일

    @LastModifiedDate
    private LocalDateTime modifiedDate; // 수정일

    @Builder
    public Post(String title, String content, User user, Category category, int viewCnt, int scrapCnt, int recommendCnt, LocalDateTime createdDate, LocalDateTime modifiedDate, String imageUrl) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.category = category;
        this.viewCnt = viewCnt;
        this.scrapCnt = scrapCnt;
        this.recommendCnt = recommendCnt;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.imageUrl = imageUrl;
    }
}