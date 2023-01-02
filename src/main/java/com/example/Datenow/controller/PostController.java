package com.example.Datenow.controller;

import com.example.Datenow.DTO.PostDto.PostCreateResponseDto;
import com.example.Datenow.DTO.PostDto.PostLikeDto;
import com.example.Datenow.DTO.PostDto.PostRequestDto;
import com.example.Datenow.DTO.PostDto.PostResponseDto;
import com.example.Datenow.domain.Category;
import com.example.Datenow.domain.Post.Post;
import com.example.Datenow.domain.User;
import com.example.Datenow.repository.UserRepository;
import com.example.Datenow.service.PostService;
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

    /*
        http method를 통해서 이 uri은 어떤 행위를 할지 표현이 가능하기 때문에, uri에는 명사로 어떤 자원인지만 표현
    
     */
    //===== GET =====//
    // 유저 생성
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
    
    // 게시글들 조회
    @GetMapping("api/v1/posts")
    public ResponseEntity<List<PostResponseDto>> findAll() {
        List<PostResponseDto> postList = postService.findAll();

        return new ResponseEntity(postList, HttpStatus.OK);
    }

    // 게시글 하나 조회
    @GetMapping("api/v1/posts/{id}")
    public ResponseEntity<PostResponseDto> findById( @PathVariable(name = "id") Long postId) {

        PostResponseDto post = postService.findById(postId);

        return new ResponseEntity<>(post, HttpStatus.OK);
    }
    
    // 게시글 추천순으로 조회
    @GetMapping("api/v1/posts/recommend")
    public ResponseEntity<List<PostResponseDto>> findAllByOrderByRecommendCntDesc() {
        List<PostResponseDto> postList = postService.findAllByOrderByRecommendCntDesc();

        return new ResponseEntity(postList, HttpStatus.OK);
    }

    // 게시글 생성 최신순으로 조회
    @GetMapping("api/v1/posts/new")
    public ResponseEntity<List<PostResponseDto>> findAllByOrderByCreatedDateDesc() {
        List<PostResponseDto> postList = postService.findAllByOrderByCreatedDateDesc();

        return new ResponseEntity(postList, HttpStatus.OK);
    }


    // 게시글 생성 오래된순으로 조회
    @GetMapping("api/v1/posts/old")
    public ResponseEntity<List<PostResponseDto>> findAllByOrderByCreatedDateAsc() {
        List<PostResponseDto> postList = postService.findAllByOrderByCreatedDateAsc();

        return new ResponseEntity(postList, HttpStatus.OK);
    }
    
    // 게시글 검색 제목에 맞게 조회
    @GetMapping("api/v1/posts/title/{title}")
    public ResponseEntity<List<PostResponseDto>> findByTitleContaining(@PathVariable(name = "title") String postTitle) {
        List<PostResponseDto> postList = postService.findByTitleContaining(postTitle);

        return new ResponseEntity(postList, HttpStatus.OK);
    }


    // 게시글 카테고리에 맞게 조회
    @GetMapping("api/v1/posts/category/{category}")
    public ResponseEntity<List<PostResponseDto>> findByCategory(@PathVariable(name = "category") Category category) {
        List<PostResponseDto> postList = postService.findByCategory(category);

        return new ResponseEntity(postList, HttpStatus.OK);
    }

    // 게시글 카테고리에 맞게 조회하되 추천순으로 반환
    @GetMapping("api/v1/posts/category-top/{category}")
    public ResponseEntity<List<PostResponseDto>> findByCategoryOrderByRecommendCntDesc(@PathVariable(name = "category") Category category) {
        List<PostResponseDto> postList = postService.findByCategoryOrderByRecommendCntDesc(category);

        return new ResponseEntity(postList, HttpStatus.OK);
    }
    
     
    //===== POST =====//
    // 게시글 생성
    @PostMapping("api/v1/posts/{id}")
    public ResponseEntity<PostCreateResponseDto> save(@Valid PostRequestDto postDTO,
                                                      // 해당 userId는 추후 jwt를 이용한 Principal로 변경하기
                                                      @PathVariable(name = "id") Long userId) {

        Post post = postService.save(postDTO, userId);

        return new ResponseEntity(PostCreateResponseDto.fromCreatePost(post), HttpStatus.CREATED);
    }

    // 게시글 수정
    @PutMapping("api/v1/posts/{postId}/{userId}")
    public ResponseEntity<PostResponseDto> update(@Valid PostRequestDto postDTO,
                                                  // 해당 userId는 추후 jwt를 이용한 Principal로 변경하기
                                                  @PathVariable(name = "userId") Long userId,
                                                  @PathVariable(name = "postId") Long postId) {

        Post post = postService.updatePost(postId, postDTO, userId);

        return new ResponseEntity(PostResponseDto.fromUpdatePost(post), HttpStatus.OK);
    }
    
    // 게시글 삭제
    @DeleteMapping("api/v1/posts/{postId}/{userId}")
    public ResponseEntity<PostLikeDto> delete(// 해당 userId는 추후 jwt를 이용한 Principal로 변경하기
                                                @PathVariable(name = "userId") Long userId,
                                                @PathVariable(name = "postId") Long postId) {
        postService.delete(postId, userId);

        return new ResponseEntity("게시글이 삭제되었습니다.", HttpStatus.OK);
    }

    // 게시글 좋아요
    @PostMapping("api/v1/postlike/{postId}/{userId}")
    public ResponseEntity<PostLikeDto> postLike(// 해당 userId는 추후 jwt를 이용한 Principal로 변경하기
                                                @PathVariable(name = "userId") Long userId,
                                                @PathVariable(name = "postId") Long postId) {

        PostLikeDto post = postService.postLike(postId, userId);

        return new ResponseEntity(post, HttpStatus.OK);
    }

}
