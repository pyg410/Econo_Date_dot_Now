package com.example.Datenow.DTO.PostDto.PostMapDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostMapRequestDto {

    private Long expCost; // 예상 비용

    private String keyword; // 위치 키워드

    private String content; // 위치 내용
}
