package com.example.Datenow.domain.Post;

import com.example.Datenow.domain.User;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 생성자를 통해서 값 변경 목적으로 접근하는 메시지들 차단
@Entity
public class PostMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double lat; // 위도

    private Double lng; // 경도

    private Long expCost; // 예상 비용
    
    private String keyword; // 위치 키워드

    private String content; // 위치 내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public PostMap(Double lat, Double lng, Long expCost, String keyword, String content, Post post, User user){
        this.lat = lat;
        this.lng = lng;
        this.expCost = expCost;
        this.keyword = keyword;
        this.content = content;
        this.post = post;
        this.user = user;
    }

    public void changeLatLng(Double lat, Double lng) {
        this.lat = lat;
        this.lng = lng;
    }


    public void changeExpCost(Long expCost) {
        this.expCost = expCost;
    }


    public void changeKeyword(String keyword) {
        this.keyword = keyword;
    }


    public void changeContent(String content) {
        this.content = content;
    }

    public void mappingPost(Post post) {
        this.post = post;
        post.mappingPostMap(this);
    }

    public void mappingUser(User user) {
        this.user = user;
        user.mappingPostMap(this);
    }




}
