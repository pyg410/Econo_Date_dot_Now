package com.example.Datenow.domain;

import com.example.Datenow.domain.Post.Post;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED) // 파라미터가 없는 기본 생성자를 생성
@Getter
@Table(name = "comments")
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@Column(columnDefinition = "TEXT") // 특정 필드의 타입을 지정하여 데이터를 추출
    private String content; // 댓글 내용

    @Column(name = "created_date")
    @CreatedDate
    private LocalDateTime createdDate; // 댓글 작성 날짜

    @Column(name = "modified_date")
    @LastModifiedDate
    private LocalDateTime modifiedDate; // 댓글 수정 날짜

    @ManyToOne
    @JoinColumn(name = "posts_id")
    private Post post;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user; // 작성자

    @Builder
    public Comment(String content, Post post, User user){
        this.content = content;
        this.post = post;
        this.user = user;
    }

    public void mappingPostAndUser(Post post, User user) {
        this.post = post;
        this.user = user;

        post.mappingComment(this);
        user.mappingComment(this);
    }

    public void changeContents(String contents) {
        this.content = contents;
    }

}
