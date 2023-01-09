package com.example.Datenow.service;

import com.example.Datenow.DTO.PostDto.MapDto.MapRequestDto;
import com.example.Datenow.domain.Map;
import com.example.Datenow.domain.Post.Post;
import com.example.Datenow.repository.MapRepository;
import com.example.Datenow.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MapService {

    @Autowired private final MapRepository Repository;

    @Autowired private final PostRepository postRepository;

    @Transactional
    public Map save(MapRequestDto mapRequestDto, Long postId) throws Exception {

        Post post = postRepository.findById(postId).orElseThrow(() -> new Exception("Post가 Null값입니다."));

        Map map = Map.builder()
                .lat(mapRequestDto.getLat())
                .lng(mapRequestDto.getLng())
                .post(post)
                .build();
        
        // 양방향 연관관계 매핑 시켜줌
        map.mappingPost(post);

        Map responseMap = Repository.save(map);

        return responseMap;
    }

    // tMap 삭제
    @Transactional
    public void delete(Long MapId) throws Exception {
        Map map = Repository.findById(MapId).orElseThrow(() -> new Exception("Map이 Null값입니다."));
        Repository.delete(map);
    }
}
