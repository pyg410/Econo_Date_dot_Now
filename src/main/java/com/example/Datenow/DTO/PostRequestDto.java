package com.example.Datenow.DTO;

import com.example.Datenow.domain.*;
import com.example.Datenow.domain.Post.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.List;


@Getter
@Builder
// Dto class에 toEntity 함수를 정의해서 entity로 바꿀수 있다. (DB에 등록할때 쓰임)
public class PostRequestDto {

    private String title; // 제목
    private String content; // 내용

    private User writer; // 작성자

    private Category category;

    private Double lat;

    private Double lng;

    private List<HashMap<Double, Double>> map;

    private String imageUrl;

    // dto -> entity
    // 중간 (변환기)역할 -> dto를 엔티티로 만들어준다.
    // toEntity() 메서드를 통해서 DTO에서 필요한 부분을 이용해 Entity로 만든다.
    public Post toEntity() {
        return Post.builder()
                .title(title)
                .content(content)
                .user(writer)
                .category(category)
                .postMapList(map)
                .viewCnt(0)
                .scrapCnt(0)
                .recommendCnt(0)
                .imageUrl(imageUrl)
                .build();
    }
}