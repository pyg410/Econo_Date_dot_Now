package com.example.Datenow.domain;

import javax.persistence.*;

import com.example.Datenow.domain.Post.Post;
import com.fasterxml.jackson.annotation.JsonBackReference;

// lombok을 씀으로써 getter, setter 지정을 하지 않아도 된다.
// 어노테이션으로 명시하면 된다.
import lombok.*;

import java.util.Optional;

@Table(name = "scrap")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 파라미터가 없는 기본 생성자를 생성
@Entity
public class Scrap {

    @Id
    @Column(name = "scrap_id") // 엔티티는 타입(ex:Meber)가 있으므로, id 필드만으로 구별이 쉽지만, DB의 경우 타입이 없기에 id라고 이름을 지으면 구별하기 어렵다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 생성을 DB에 위임
    private Long id; // PK

    @ManyToOne
    @JsonBackReference // 순환 참조를 방지하는 어노테이션
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "post_id")
    private Post post;

    public static boolean isScrapedPost(Optional<Scrap> optionalPostScrap) {
        return optionalPostScrap.isPresent(); // - Optional 객체가 값을 가지고 있다면 true, 값이 없다면 false 리턴, 즉 스크랩을 눌렀으면 true
    }

    @Builder
    public Scrap(User user, Post post){
        this.user = user;
        this.post = post;
    }
}