package com.example.Datenow.domain.Post;

import com.example.Datenow.domain.User;
import lombok.*;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    // Optional<T>는 null이 올 수 있는 값을 감싸는 Wrapper 클래스로, 참조하더라도 NPE가 발생하지 않도록 도와준다.
    public static boolean isVotedPost(Optional<PostLike> optionalPostLike) {
        return optionalPostLike.isPresent(); // - Optional 객체가 값을 가지고 있다면 true, 값이 없다면 false 리턴, 즉 좋아요를 눌렀으면 true
    }

    // 매핑 연관 관계 메소드를 왜 만들어야 하는가?
//    public void mappingUser(User user) {
//        this.user = user;
//        user.mappingPostLike(this);
//    }
//
//    public void mappingPost(Post post) {
//        this.post = post;
//        post.mappingPostLike(this);
//    }

}
