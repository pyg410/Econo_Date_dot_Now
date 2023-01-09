package com.example.Datenow.DTO.PostDto;

import com.example.Datenow.domain.Post.Post;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class PostCreateResponseDto {
    private Long id;
    private String title;
    private String content;
    private String imageUrl;
    private String hashTag;
    private LocalDateTime createdAt;


    public static PostCreateResponseDto fromCreatePost(Post post){
        return PostCreateResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .hashTag(post.getHashTag())
                .createdAt(post.getCreatedAt())
                .imageUrl(post.getImageUrl())
                .build();
    }
}