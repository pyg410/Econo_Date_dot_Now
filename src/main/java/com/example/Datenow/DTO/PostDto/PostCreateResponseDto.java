package com.example.Datenow.DTO.PostDto;

import com.example.Datenow.domain.Post.Post;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class PostCreateResponseDto {
    private String title;
    private String content;
    private String imageUrl;
    private LocalDateTime createdDate;


    public static PostCreateResponseDto fromCreatePost(Post post){
        return PostCreateResponseDto.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .createdDate(post.getCreatedDate())
                .imageUrl(post.getImageUrl())
                .build();
    }
}