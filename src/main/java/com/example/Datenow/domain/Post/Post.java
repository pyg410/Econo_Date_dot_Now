package com.example.Datenow.domain.Post;

import java.util.ArrayList;

import java.util.List;
import javax.persistence.*;

import com.example.Datenow.domain.Category;
import com.example.Datenow.domain.Comment;
import com.example.Datenow.domain.Map;
import com.example.Datenow.domain.User;
import com.example.Datenow.domain.common.Date;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/*
@NoArgsConstructor 어노테이션은 파라미터가 없는 기본 생성자를 생성해주고,
@AllArgsConstructor 어노테이션은 모든 필드 값을 파라미터로 받는 생성자를 만들어줍니다.
마지막으로 @RequiredArgsConstructor 어노테이션은 final이나 @NonNull인 필드 값만 파라미터로 받는 생성자를 만들어줍니다.
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 생성자를 통해서 값 변경 목적으로 접근하는 메시지들 차단
@EntityListeners(AuditingEntityListener.class) // 이게 있어야 @CreatedDate 활성화
@Entity
public class Post extends Date {

    @Id
    @Column(name = "post_id") // 엔티티는 타입(ex:Member)가 있으므로, id 필드만으로 구별이 쉽지만, DB의 경우 타입이 없기에 id라고 이름을 지으면 구별하기 어렵다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 생성을 DB에 위임
    private Long id; // PK

    private String title; // 제목

    private String content; // 내용

    private String hashTag; // 해시태그
    
    @ManyToOne(fetch = FetchType.LAZY) // FetchType.Lazy는 JPA의 프록시와 지연로딩의 기초적인 내용을 공부해야한다.
    @JoinColumn(name = "user_id") // FK이름 지정
    private User user; // User 객체 자체를 저장받고, 반환할 때는 Post에 저장된 USER.getUsername 반환

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = CascadeType.REMOVE) // 부모쪽에 cascade = CascadeType.REMOVE 설정
    private List<Comment> commentList = new ArrayList<>(); // 댓글들

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<PostLike> postLikeList = new ArrayList<>(); // 좋아요 리스트

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<PostMap> postMapList = new ArrayList<>(); // postMap 리스트

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Map> MapList = new ArrayList<>(); // Map 리스트

    private String imageUrl; // 이미지 경로

    @Enumerated(value = EnumType.STRING)
    private Category category; // 카테고리

    private int viewCnt; // 조회 수

    private int recommendCnt; // 좋아요 수

    @Builder
    public Post(String title, String content, String hashTag, User user, Category category, List<Comment> commentList, List<PostMap> postMapList,
                int viewCnt, int recommendCnt, String imageUrl
                ) {
        this.title = title;
        this.content = content;
        this.hashTag = hashTag;
        this.user = user;
        this.category = category;
        this.postMapList = postMapList;
        this.commentList = commentList;
        this.viewCnt = viewCnt;
        this.recommendCnt = recommendCnt;
        this.imageUrl = imageUrl;
    }

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeImageUrl(String url) {
        this.imageUrl = url;
    }

    public void changeContents(String contents) {
        this.content = contents;
    }

    public void mappingPostLike(PostLike postLike) {
        this.postLikeList.add(postLike);
    }

    // 단방향 연관관계의 경우 이렇게 구현한다. -> 그러나 비추 :: Notion 참조
    // N측에서 단순히 해당 메서드를 호출해주기만 하면 된다.
//    public void addMap(Map map){
//        if(map != null) {
//            MapList.add(map);
//        }
//    }
    public void mappingMap(Map map) {
        this.MapList.add(map);
    }

    public void mappingUser(User user) {
        this.user = user;
        user.mappingPost(this);
    }

    public void mappingPostMap(PostMap postmap) {
        this.postMapList.add(postmap);
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