package com.example.Datenow.domain.Post;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.*;

import com.example.Datenow.DTO.CommentResponseDto;
import com.example.Datenow.domain.Category;
import com.example.Datenow.domain.Comment;
import com.example.Datenow.domain.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 생성자를 통해서 값 변경 목적으로 접근하는 메시지들 차단
@Entity
public class Post{

    @Id
    @Column(name = "post_id") // 엔티티는 타입(ex:Member)가 있으므로, id 필드만으로 구별이 쉽지만, DB의 경우 타입이 없기에 id라고 이름을 지으면 구별하기 어렵다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 생성을 DB에 위임
    private Long id; // PK

    private String title; // 제목

    private String content; // 내용

    @ManyToOne(fetch = FetchType.LAZY) // FetchType.Lazy는 JPA의 프록시와 지연로딩의 기초적인 내용을 공부해야한다.
    @JoinColumn(name = "user_id") // FK이름 지정
    private User user; // User 객체 자체를 저장받고, 반환할 때는 Post에 저장된 USER.getUsername 반환


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = CascadeType.REMOVE) // 부모쪽에 cascade = CascadeType.REMOVE 설정
    private List<Comment> commentList = new ArrayList<>(); // 댓글들

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<PostLike> postLikeList = new ArrayList<>(); // 좋아요 리스트

    @ElementCollection
    private List<HashMap<Double, Double>> postMapList = new ArrayList<>(); // 맵 위치 리스트

    private String imageUrl; // 이미지 경로

    @Enumerated(value = EnumType.STRING)
    private Category category; // 카테고리

    private int viewCnt; // 조회 수

    private int scrapCnt; // 스크랩 수

    private int recommendCnt; // 좋아요 수

    @CreatedDate
    private LocalDateTime createdDate = LocalDateTime.now(); // 생성일

    @LastModifiedDate
    private LocalDateTime modifiedDate; // 수정일

    @Builder
    public Post(String title, String content, User user, Category category, List<Comment> commentList, List<HashMap<Double, Double>> postMapList, int viewCnt, int scrapCnt, int recommendCnt, String imageUrl) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.category = category;
        this.postMapList = postMapList;
        this.commentList = commentList;
        this.viewCnt = viewCnt;
        this.scrapCnt = scrapCnt;
        this.recommendCnt = recommendCnt;
        this.imageUrl = imageUrl;
    }

    public void mappingPostLike(PostLike postLike) {
        this.postLikeList.add(postLike);
    }

    public void mappingUser(User user) {
        this.user = user;
        user.mappingPost(this);
    }

    public void mappingComment(Comment comment) {
        this.commentList.add(comment);
    }

    public void addViewCount() {
        this.viewCnt += 1;
    }

    public void discountLike(PostLike postLike) {
        this.postLikeList.remove(postLike);
    }

    public void updateLikeCount() {
        this.recommendCnt = (int) this.postLikeList.size();
    }

}