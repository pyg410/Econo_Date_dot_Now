package com.example.Datenow.DTO;

import com.example.Datenow.domain.Post.Post;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class PostCreateResponseDto {
    private String title;
    private String content;

    public static PostCreateResponseDto fromCreatePost(Post post){
        return PostCreateResponseDto.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }
}