package com.example.Datenow.DTO.PostDto.PostMapDto;

import com.example.Datenow.domain.Post.PostMap;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class PostMapResponseDto {
    private Long id; // pk

    private Long expCost; // 예상 비용

    private String keyword; // 위치 키워드

    private String content; // 위치 내용

    public static PostMapResponseDto FromPostMap(PostMap postMap) {
        return PostMapResponseDto.builder()
                .id(postMap.getId())
                .expCost(postMap.getExpCost())
                .keyword(postMap.getKeyword())
                .content(postMap.getContent())
                .build();
    }

    public static List<PostMapResponseDto> fromPostMapList(List<PostMap> postMapList) {
        // 생성하기 : 스트림 인스턴스 생성.
        Stream<PostMap> stream = postMapList.stream();

        // 가공하기 : 필터링(filtering) 및 맵핑(mapping) 등 원하는 결과를 만들어가는 중간 작업(intermediate operations).
        // 맵(map)은 스트림 내 요소들을 하나씩 특정 값으로 변환해줍니다. 이 때 값을 변환하기 위한 람다를 인자로 받습니다.
        // collect()는 Stream의 데이터를 변형 등의 처리를 하고 원하는 자료형으로 변환해 줍니다.

        /*
        즉 CommentResponseDto 자료형을 가진 스트림 내 요소들을 FromComment에 맞게 바꿔준 후 하나의 리스트로 만들어준다.
         */
        return stream.map(PostMapResponseDto::FromPostMap).collect(Collectors.toList());
    }
}
