package com.example.Datenow.controller;


import com.example.Datenow.DTO.PostDto.*;
import com.example.Datenow.DTO.PostDto.MapDto.MapRequestDto;
import com.example.Datenow.DTO.PostDto.MapDto.MapResponseDTO;
import com.example.Datenow.DTO.PostDto.PostMapDto.PostMapResponseDto;
import com.example.Datenow.domain.Map;
import com.example.Datenow.service.MapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j // 로깅에 대한 추상 레이어를 제공하는 인터페이스의 모음이다.
public class MapController {

    @Autowired private final MapService mapService;

    // Map 생성
    @PostMapping("api/v1/posts/{postId}/maps")
    public ResponseEntity<PostMapResponseDto> save(@Valid MapRequestDto postDTO,
                                                   @PathVariable(name = "postId") Long postId)
                                                   throws Exception {

        Map map = mapService.save(postDTO, postId);

        return new ResponseEntity(MapResponseDTO.fromMap(map), HttpStatus.CREATED);
    }
    
    // Map 삭제
    @DeleteMapping("api/v1/posts/maps/{mapId}")
    public ResponseEntity<PostLikeDto> delete(// 해당 userId는 추후 jwt를 이용한 Principal로 변경하기
                                              @PathVariable(name = "mapId") Long mapId) throws Exception {

        mapService.delete(mapId);

        return new ResponseEntity("게시글 Map이 삭제되었습니다.", HttpStatus.OK);
    }
}
