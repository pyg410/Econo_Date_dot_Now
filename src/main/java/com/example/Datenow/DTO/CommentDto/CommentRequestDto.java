package com.example.Datenow.DTO.CommentDto;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class CommentRequestDto {
    private String content;

    // 왜 이걸 지우니까 잘 동작하지???? -> 아마 Comment Entity의 Builder는 content, post, user를 입력받지만 여기 엔티티는 content만 입력받기 때문이지 않을까??
    /*public Comment toEntity() {
        return Comment.builder()
                .content(content)
                .build();
    }*/
}
