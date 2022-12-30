package com.example.Datenow.controller;

import com.example.Datenow.DTO.PostCreateResponseDto;
import com.example.Datenow.DTO.PostRequestDto;
import com.example.Datenow.DTO.PostResponseDto;
import com.example.Datenow.domain.Post.Post;
import com.example.Datenow.domain.User;
import com.example.Datenow.repository.UserRepository;
import com.example.Datenow.service.PostService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;


import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController //@RestController는 @Controller에 @ResponseBody가 추가된 것입니다. 당연하게도 RestController의 주용도는 Json 형태로 객체 데이터를 반환하는 것입니다.
@RequiredArgsConstructor
@Slf4j // 로깅에 대한 추상 레이어를 제공하는 인터페이스의 모음이다.
public class PostController {

    @Autowired PostService postService;
    @Autowired UserRepository userRepository;

    @Operation(summary = "home", description = "home입니다.", tags = { "Post Controller" })
    @GetMapping("api/v1/home/{id}")
    public ResponseEntity<User> home(@PathVariable(name = "id") Long userId) {
        User user = new User();
        user.setId(userId);
        user.setNickname("붕붕");
        userRepository.save(user);

        Optional<User> responseUser = userRepository.findById(userId);

        return new ResponseEntity(responseUser, HttpStatus.OK);
    }

    @GetMapping("api/v1/posts")
    public ResponseEntity<List<PostResponseDto>> findAll() {
        List<PostResponseDto> postList = postService.findAll();

        return new ResponseEntity(postList, HttpStatus.OK);
    }

    @PostMapping("api/v1/post/{id}")
    public ResponseEntity<PostCreateResponseDto> save(@Valid PostRequestDto postDTO,
                                                      // 해당 userId는 추후 jwt를 이용한 Principal로 변경하기
                                                      @PathVariable(name = "id") Long userId) {

        Post post = postService.save(postDTO, userId);

        return new ResponseEntity(PostCreateResponseDto.fromCreatePost(post), HttpStatus.CREATED);
    }




}
