package com.example.Datenow.DTO.CommentDto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;


@Getter
@Builder
public class CommentRequestDto {

    @NotBlank(message = "댓글 내용은 필수 입력값입니다.")
    private String content;
}
