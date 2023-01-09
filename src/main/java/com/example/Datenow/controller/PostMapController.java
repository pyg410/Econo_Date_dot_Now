package com.example.Datenow.controller;

import com.example.Datenow.DTO.PostDto.*;
import com.example.Datenow.DTO.PostDto.PostMapDto.PostMapRequestDto;
import com.example.Datenow.DTO.PostDto.PostMapDto.PostMapResponseDto;
import com.example.Datenow.domain.Post.PostMap;
import com.example.Datenow.service.PostMapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
//@RestController는 @Controller에 @ResponseBody가 추가된 것입니다. 당연하게도 RestController의 주용도는 Json 형태로 객체 데이터를 반환하는 것입니다.
@RequiredArgsConstructor
@Slf4j // 로깅에 대한 추상 레이어를 제공하는 인터페이스의 모음이다.
public class PostMapController {

    @Autowired private final PostMapService postMapService;

    // PostMap 생성
    @PostMapping("api/v1/posts/{postId}/postmaps/{userId}")
    public ResponseEntity<PostMapResponseDto> save(@Valid PostMapRequestDto postDTO,
                                                   @PathVariable(name = "postId") Long postId,
                                                   @PathVariable(name = "userId") Long userId) throws Exception {

        PostMap postMap = postMapService.save(postDTO, postId, userId);

        return new ResponseEntity(PostMapResponseDto.FromPostMap(postMap), HttpStatus.CREATED);
    }

    
    // PostMap 수정
    @PutMapping("api/v1/posts/postmaps/{postMapId}/{userId}")
    public ResponseEntity<PostMapResponseDto> update(@Valid PostMapRequestDto postDTO,
                                                  @PathVariable(name = "userId") Long userId,
                                                  @PathVariable(name = "postMapId") Long postMapId) throws Exception {

        PostMap postMap = postMapService.updatePostMap(postDTO, postMapId, userId);

        return new ResponseEntity(PostMapResponseDto.FromPostMap(postMap), HttpStatus.OK);
    }
    
    // PostMap 삭제
    @DeleteMapping("api/v1/postmaps/{postMapId}/{userId}")
    public ResponseEntity<PostLikeDto> delete(// 해당 userId는 추후 jwt를 이용한 Principal로 변경하기
                                              @PathVariable(name = "userId") Long userId,
                                              @PathVariable(name = "postMapId") Long postMapId) throws Exception {
        postMapService.delete(postMapId, userId);

        return new ResponseEntity("게시글 PostMap이 삭제되었습니다.", HttpStatus.OK);
    }
}
