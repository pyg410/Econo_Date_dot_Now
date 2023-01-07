package com.example.Datenow.DTO.PostDto;

import com.example.Datenow.domain.*;
import com.example.Datenow.domain.Post.Post;
import com.example.Datenow.domain.Post.PostMap;
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

    
    private String hashTag; // 해시태그

    private Category category; // 카테고리

    private List<PostMap> postMapList;

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
                .postMapList(postMapList) // 아마 DTO로 입력을 같이 받는게 아니라 별도의 Uri로 입력 받아야 하지 않을까??
                .viewCnt(0)
                .recommendCnt(0)
                .imageUrl(imageUrl)
                .build();
    }
}