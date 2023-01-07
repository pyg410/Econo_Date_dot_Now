package com.example.Datenow.DTO.CommentDto;

import com.example.Datenow.domain.Comment;
import com.example.Datenow.domain.Post.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Builder
public class CommentResponseDto {

    private String content;

    private String post;

    private String writer;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static CommentResponseDto FromComment(Comment comment) {
        return CommentResponseDto.builder()
                .content(comment.getContent())
                .post(comment.getPost().getTitle())
                .writer(comment.getUser().getUsername())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }

    // 댓글 List DTO
    public static List<CommentResponseDto> FromCommentList(List<Comment> commentList) {
        // 생성하기 : 스트림 인스턴스 생성.
        Stream<Comment> stream = commentList.stream();

        // 가공하기 : 필터링(filtering) 및 맵핑(mapping) 등 원하는 결과를 만들어가는 중간 작업(intermediate operations).
        // 맵(map)은 스트림 내 요소들을 하나씩 특정 값으로 변환해줍니다. 이 때 값을 변환하기 위한 람다를 인자로 받습니다.
        // collect()는 Stream의 데이터를 변형 등의 처리를 하고 원하는 자료형으로 변환해 줍니다.

        /*
        즉 List<Comment> 자료형을 가진 스트림 내 요소들을 FromComment에 맞게 바꿔준 후 List<CommentResponseDto> 자료형의 하나의 리스트로 만들어준다.
         */
        return stream.map(CommentResponseDto::FromComment).collect(Collectors.toList());
    }
}
