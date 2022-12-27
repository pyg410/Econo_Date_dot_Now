package com.example.Datenow.DTO;

import com.example.Datenow.domain.Category;
import com.example.Datenow.domain.Comment;
import com.example.Datenow.domain.Post.Post;
import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;

// Response : Get, 조회
@Getter
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

    // entity -> dto
    // Controller에서 Response DTO 형태로 Client에 전달한다.
    public static PostResponseDto From(Post post) { // toDto로 변환
        return PostResponseDto.builder()
                .title(post.getTitle())
                .content(post.getContent())
                //.writer(post.getUser().getUsername()) String으로 반환하기
                .category(post.getCategory())
                .map(post.getPostMapList())
                .viewCnt(post.getViewCnt())
                .scrapCnt(post.getScrapCnt())
                .recommendCnt(post.getRecommendCnt())
                .build();
    }
    
    // FromMany : 글 여러개 반환하기
    public static PostResponseDto FromManyPost(Post post) {
        return PostResponseDto.builder()
                .title(post.getTitle())
                .content(post.getContent())
                //.writer(post.getUser().getUsername())
                .recommendCnt(post.getRecommendCnt())
                .build();
    }

    // FromMany : 글 한개 반환하기
    public static PostResponseDto FromDetailPost(Post post) {
        return PostResponseDto.builder()
                .title(post.getTitle())
                .content(post.getContent())
                //.writer(post.getUser().getUsername())
                .comments(CommentResponseDto.FromCommentList(post.getCommentList()))
                .viewCnt(post.getViewCnt())
                .build();
    }
}