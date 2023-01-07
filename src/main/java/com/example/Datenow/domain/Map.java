package com.example.Datenow.domain;

import com.example.Datenow.domain.Post.Post;
import com.example.Datenow.domain.common.Date;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Map extends Date {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double lat; // 위도

    private Double lng; // 경도

    @ManyToOne(fetch = FetchType.LAZY) // 아마 PostMap.Post를 찾게되는 일은 없을 것 같은데 혹시 모를 확장에 대비해 양방향으로 해놓자
    @JoinColumn(name = "post_id")
    private Post post;


    @Builder
    public Map(Double lat, Double lng, Post post){
        this.lat = lat;
        this.lng = lng;
        this.post = post;
    }

    // 1 : N측의 경우 1측에서 List에 N측을 추가하는 메서드를 만들고, N측에서 앞의 메서드와 본인 외래키를 지정하는 코드를 작성한다.
    public void mappingPost(Post post) {
        this.post = post;
        post.mappingMap(this);
    }

}
