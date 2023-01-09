package com.example.Datenow.DTO.PostDto;

import com.example.Datenow.DTO.CommentDto.CommentResponseDto;
import com.example.Datenow.DTO.PostDto.MapDto.MapResponseDTO;
import com.example.Datenow.DTO.PostDto.PostMapDto.PostMapResponseDto;
import com.example.Datenow.domain.Category;
import com.example.Datenow.domain.Post.Post;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

// Response : Get, 조회
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class PostResponseDto {
    private Long id; // pk
    private String title; // 제목
    private String content; // 내용
    private String hashTag; // 해시태그
    private String writer; // 작성자
    private String profileImg; // 프로필 이미지
    private String imageUrl; // 이미지 경로
    private Category category; // 카테고리
    private List<CommentResponseDto> comments; // 댓글들
    private List<PostMapResponseDto> postMapList; // PostMaps
    private List<MapResponseDTO> mapList; // 위도, 경도
    private int commentCnt;
    private int viewCnt;
    private int recommendCnt;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    // entity -> dto
    // Controller에서 Response DTO 형태로 Client에 전달한다.
    public static PostResponseDto from(Post post) { // toDto로 변환
        return PostResponseDto.builder()
                .writer(post.getUser().getUsername()) //String으로 반환하기
                .category(post.getCategory())
                .postMapList(PostMapResponseDto.fromPostMapList(post.getPostMapList()))
                .imageUrl(post.getImageUrl())
                .viewCnt(post.getViewCnt())
                .recommendCnt(post.getRecommendCnt())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getUpdatedAt())
                .build();
    }
    
    // FromMany : 글 여러개 반환하기
    public static PostResponseDto fromManyPost(Post post) {
        return PostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .writer(post.getUser().getUsername())
                .profileImg(post.getUser().getProfileImg())
                .content(post.getContent())
                .hashTag(post.getHashTag())
                .imageUrl(post.getImageUrl())
                .postMapList(PostMapResponseDto.fromPostMapList(post.getPostMapList()))
                .commentCnt(post.getCommentList().size())
                .viewCnt(post.getViewCnt())
                .recommendCnt(post.getRecommendCnt())
                .category(post.getCategory())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getUpdatedAt())
                .build();
    }

    // FromMany : 글 한개 반환하기
    public static PostResponseDto fromDetailPost(Post post) {
        return PostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .hashTag(post.getHashTag())
                .writer(post.getUser().getUsername())
                .profileImg(post.getUser().getProfileImg())
                .imageUrl(post.getImageUrl())
                .postMapList(PostMapResponseDto.fromPostMapList(post.getPostMapList()))
                .mapList(MapResponseDTO.fromMapList(post.getMapList()))
                .comments(CommentResponseDto.FromCommentList(post.getCommentList()))
                .commentCnt(post.getCommentList().size())
                .viewCnt(post.getViewCnt())
                .category(post.getCategory())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getUpdatedAt())
                .build();
    }

    public static PostResponseDto fromUpdatePost(Post post) {
        return  PostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .imageUrl(post.getImageUrl())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getUpdatedAt())
                .commentCnt(post.getCommentList().size())
                .build();
    }

}
