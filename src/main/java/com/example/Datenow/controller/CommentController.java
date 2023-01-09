package com.example.Datenow.controller;

import com.example.Datenow.DTO.CommentDto.CommentRequestDto;
import com.example.Datenow.DTO.CommentDto.CommentResponseDto;
import com.example.Datenow.domain.Comment;
import com.example.Datenow.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController //@RestController는 @Controller에 @ResponseBody가 추가된 것입니다. 당연하게도 RestController의 주용도는 Json 형태로 객체 데이터를 반환하는 것입니다.
@RequiredArgsConstructor
@Slf4j // 로깅에 대한 추상 레이어를 제공하는 인터페이스의 모음이다.
public class CommentController {
    @Autowired private final CommentService commentService;
    
    // 댓글 생성
    @PostMapping("api/v1/posts/{postId}/comments/{userId}")
    public ResponseEntity<CommentResponseDto> save(@Valid CommentRequestDto commentDTO,
                                                   // 해당 userId는 추후 jwt를 이용한 Principal로 변경하기
                                                   @PathVariable(name = "postId") Long postId,
                                                   @PathVariable(name = "userId") Long userId) throws Exception {

        Comment comment = commentService.save(postId, commentDTO, userId);

        return new ResponseEntity(CommentResponseDto.FromComment(comment), HttpStatus.CREATED);
    }

    
    // 댓글 수정
    @PutMapping("api/v1/posts/comments/{commentId}/{userId}")
    public ResponseEntity<CommentResponseDto> update(@Valid CommentRequestDto commentDTO,
                                                   // 해당 userId는 추후 jwt를 이용한 Principal로 변경하기
                                                   @PathVariable(name = "commentId") Long commentId,
                                                   @PathVariable(name = "userId") Long userId) throws Exception {

        Comment comment = commentService.update(commentId, userId, commentDTO);

        return new ResponseEntity(CommentResponseDto.FromComment(comment), HttpStatus.OK);
    }
    
    
    // 댓글 삭제
    @DeleteMapping("api/v1/posts/comments/{commentId}/{userId}")
    public ResponseEntity<CommentResponseDto> delete(@Valid CommentRequestDto commentDTO,
                                                     // 해당 userId는 추후 jwt를 이용한 Principal로 변경하기
                                                     @PathVariable(name = "commentId") Long commentId,
                                                     @PathVariable(name = "userId") Long userId) throws Exception {

        commentService.delete(commentId, userId);
        return new ResponseEntity("잘 삭제 되었습니다!", HttpStatus.OK);
    }
}
