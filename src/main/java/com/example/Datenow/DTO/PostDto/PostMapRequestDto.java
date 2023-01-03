package com.example.Datenow.DTO.PostDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostMapRequestDto {

    private Double lat; // 위도

    private Double lng; // 경도

    private Long expCost; // 예상 비용

    private String keyword; // 위치 키워드

    private String content; // 위치 내용
}