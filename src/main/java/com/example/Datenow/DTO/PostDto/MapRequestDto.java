package com.example.Datenow.DTO.PostDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MapRequestDto {

    private Double lat; // 위도

    private Double lng; // 경도

}
