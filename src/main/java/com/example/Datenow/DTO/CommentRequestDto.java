package com.example.Datenow.DTO;

import com.example.Datenow.domain.Comment;
import com.example.Datenow.domain.Post.Post;
import com.example.Datenow.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentRequestDto {

    private Long id;

    @NotBlank
    @Length(max = 100)
    private String content;

    private Post post;

    private User user;

    public Comment toEntity() {
        return Comment.builder()
                .content(content)
                .post(post)
                .user(user)
                .build();
    }
}
