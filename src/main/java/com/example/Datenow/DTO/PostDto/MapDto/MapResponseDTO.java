package com.example.Datenow.DTO.PostDto.MapDto;

import com.example.Datenow.domain.Map;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class MapResponseDTO {
    private Long id;

    private Double lat; // 위도

    private Double lng; // 경도

    public static MapResponseDTO fromMap(Map map){
        return MapResponseDTO.builder()
                .id(map.getId())
                .lat(map.getLat())
                .lng(map.getLng())
                .build();
    }

    public static List<MapResponseDTO> fromMapList(List<Map> mapList) {
        // 생성하기 : 스트림 인스턴스 생성.
        Stream<Map> stream = mapList.stream();

        // 가공하기 : 필터링(filtering) 및 맵핑(mapping) 등 원하는 결과를 만들어가는 중간 작업(intermediate operations).
        // 맵(map)은 스트림 내 요소들을 하나씩 특정 값으로 변환해줍니다. 이 때 값을 변환하기 위한 람다를 인자로 받습니다.
        // collect()는 Stream의 데이터를 변형 등의 처리를 하고 원하는 자료형으로 변환해 줍니다.

        /*
        즉 CommentResponseDto 자료형을 가진 스트림 내 요소들을 FromComment에 맞게 바꿔준 후 하나의 리스트로 만들어준다.
         */
        return stream.map(MapResponseDTO::fromMap).collect(Collectors.toList());
    }
}
