package com.example.Datenow.DTO.PostDto;

import com.example.Datenow.DTO.CommentResponseDto;
import com.example.Datenow.domain.Category;
import com.example.Datenow.domain.Post.Post;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

// Response : Get, 조회
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class PostResponseDto {
    private String title; // 제목
    private String content; // 내용
    private String writer; // 작성자
    private Category category; // 카테고리
    private List<CommentResponseDto> comments; // 댓글들
    private List<HashMap<Double, Double>> map; // 위도, 경도
    private int viewCnt;
    private int scrapCnt;
    private int recommendCnt;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    // entity -> dto
    // Controller에서 Response DTO 형태로 Client에 전달한다.
    public static PostResponseDto from(Post post) { // toDto로 변환
        return PostResponseDto.builder()
                .writer(post.getUser().getUsername()) //String으로 반환하기
                .category(post.getCategory())
                .map(post.getPostMapList())
                .viewCnt(post.getViewCnt())
                .scrapCnt(post.getScrapCnt())
                .recommendCnt(post.getRecommendCnt())
                .createdDate(post.getCreatedDate())
                .modifiedDate(post.getModifiedDate())
                .build();
    }
    
    // FromMany : 글 여러개 반환하기
    public static PostResponseDto fromManyPost(Post post) {
        return PostResponseDto.builder()
                .title(post.getTitle())
                .writer(post.getUser().getUsername())
                .recommendCnt(post.getRecommendCnt())
                .category(post.getCategory())
                .createdDate(post.getCreatedDate())
                .modifiedDate(post.getModifiedDate())
                .build();
    }

    // FromMany : 글 한개 반환하기
    public static PostResponseDto fromDetailPost(Post post) {
        return PostResponseDto.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .writer(post.getUser().getUsername())
                .comments(CommentResponseDto.FromCommentList(post.getCommentList()))
                .viewCnt(post.getViewCnt())
                .category(post.getCategory())
                .createdDate(post.getCreatedDate())
                .modifiedDate(post.getModifiedDate())
                .build();
    }

    public static PostResponseDto fromUpdatePost(Post post) {
        return  PostResponseDto.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .createdDate(post.getCreatedDate())
                .modifiedDate(post.getModifiedDate())
                .build();
    }

}

