package com.example.Datenow.DTO.PostDto;

import com.example.Datenow.domain.Post.Post;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class PostLikeDto
{
    private int recommendCnt;

    public static PostLikeDto fromLikePost(Post post){
        return PostLikeDto.builder()
                .recommendCnt(post.getRecommendCnt())
                .build();
    }
}