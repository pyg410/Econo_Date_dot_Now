package com.example.Datenow.DTO.PostDto;

import com.example.Datenow.DTO.CommentDto.CommentResponseDto;
import com.example.Datenow.domain.Category;
import com.example.Datenow.domain.Post.Post;
import com.example.Datenow.domain.Post.PostMap;
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
    private String imageUrl; // 이미지 경로
    private Category category; // 카테고리
    private List<CommentResponseDto> comments; // 댓글들
    private List<PostMapResponseDto> map; // 위도, 경도
    private int commentCnt;
    private int viewCnt;
    private int recommendCnt;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    // entity -> dto
    // Controller에서 Response DTO 형태로 Client에 전달한다.
    public static PostResponseDto from(Post post) { // toDto로 변환
        return PostResponseDto.builder()
                .writer(post.getUser().getUsername()) //String으로 반환하기
                .category(post.getCategory())
                .map(PostMapResponseDto.fromPostMapList(post.getPostMapList()))
                .imageUrl(post.getImageUrl())
                .viewCnt(post.getViewCnt())
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
                .imageUrl(post.getImageUrl())
                .commentCnt(post.getCommentList().size())
                .viewCnt(post.getViewCnt())
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
                .imageUrl(post.getImageUrl())
                .comments(CommentResponseDto.FromCommentList(post.getCommentList()))
                .commentCnt(post.getCommentList().size())
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
                .imageUrl(post.getImageUrl())
                .createdDate(post.getCreatedDate())
                .modifiedDate(post.getModifiedDate())
                .commentCnt(post.getCommentList().size())
                .build();
    }

}

