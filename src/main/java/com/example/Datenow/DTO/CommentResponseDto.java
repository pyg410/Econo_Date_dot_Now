package com.example.Datenow.DTO;

import com.example.Datenow.domain.Comment;
import com.example.Datenow.domain.Post.Post;
import com.example.Datenow.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Builder
public class CommentResponseDto {

    private String content;

    private Post post;

    private User writer;

    public static CommentResponseDto FromComment(Comment comment) {
        return CommentResponseDto.builder()
                .content(comment.getContent())
                //.writer(comment.getUser().getUsername())
                .build();
    }

    public static List<CommentResponseDto> FromCommentList(List<Comment> commentList) {

        Stream<Comment> stream = commentList.stream();

        return stream.map(CommentResponseDto::FromComment).collect(Collectors.toList());
    }
}
