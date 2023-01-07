package com.example.Datenow.DTO.CommentDto;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class CommentRequestDto {
    private String content;
}
