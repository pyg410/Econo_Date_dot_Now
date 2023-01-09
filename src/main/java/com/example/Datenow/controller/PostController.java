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
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import javax.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController //@RestController는 @Controller에 @ResponseBody가 추가된 것입니다. 당연하게도 RestController의 주용도는 Json 형태로 객체 데이터를 반환하는 것입니다.
@RequiredArgsConstructor
@Slf4j // 로깅에 대한 추상 레이어를 제공하는 인터페이스의 모음이다.
public class PostController {

    @Autowired private final PostService postService;
    @Autowired private final UserRepository userRepository;


    /*
        http method를 통해서 이 uri은 어떤 행위를 할지 표현이 가능하기 때문에, uri에는 명사로 어떤 자원인지만 표현
    
     */
    //===== Home =====//
    // 카테고리 반환
    @Operation(summary = "get posts", description = "지역에 대한 posts들 가져오기")
    @GetMapping("api/v1/home/categorys")
    public ResponseEntity<List<Category>> findCategorys() {
        List<Category> categorys= postService.findCategorys();
        return new ResponseEntity(categorys, HttpStatus.OK);
    }
    
    // 특정 id 게시글들만 선정해서 보내주기
    @GetMapping("api/v1/home/posts/event")
    public ResponseEntity<List<PostResponseDto>> findEvent() throws Exception {
        List<PostResponseDto> postList = postService.findEvent();

        return new ResponseEntity(postList, HttpStatus.OK);
    }

    // 추천순 게시글 5개 반환
    @GetMapping("api/v1/home/posts/recommend")
    public ResponseEntity<List<PostResponseDto>> findAllByOrderByRecommendCntDesc5(@PageableDefault(size = 9) Pageable pageable) {
        List<PostResponseDto> postList = postService.findAllByOrderByRecommendCntDesc5(pageable);
        
        // 추천순 게시글 5개 반환
        List<PostResponseDto> responseDtoList =  postList.subList(0, 5);

        return new ResponseEntity(responseDtoList, HttpStatus.OK);
    }

    //===== 게시판 =====//
    // 게시글들 조회
    // Pageable은 Sort도 포함되어있다.
    @GetMapping("api/v1/posts")
    public ResponseEntity<List<PostResponseDto>> findAll(@PageableDefault(size = 9) Pageable pageable) {
        List<PostResponseDto> postList = postService.findAll(pageable);

        return new ResponseEntity(postList, HttpStatus.OK);
    }

    // 게시글 추천순으로 조회
    @GetMapping("api/v1/posts/recommend")
    public ResponseEntity<List<PostResponseDto>> findAllByOrderByRecommendCntDesc(@PageableDefault(size = 9) Pageable pageable) {
        List<PostResponseDto> postList = postService.findAllByOrderByRecommendCntDesc(pageable);

        return new ResponseEntity(postList, HttpStatus.OK);
    }

    // 게시글 생성 오래된순으로 조회
    @GetMapping("api/v1/posts/old")
    public ResponseEntity<List<PostResponseDto>> findAllByOrderByCreatedDateDesc(@PageableDefault(size = 9) Pageable pageable) {
        List<PostResponseDto> postList = postService.findAllByOrderByCreatedDateDesc(pageable);

        return new ResponseEntity(postList, HttpStatus.OK);
    }


    // 게시글 생성 최신순으로 조회
    @GetMapping("api/v1/posts/new")
    public ResponseEntity<List<PostResponseDto>> findAllByOrderByCreatedDateAsc(@PageableDefault(size = 9) Pageable pageable) {
        List<PostResponseDto> postList = postService.findAllByOrderByCreatedDateAsc(pageable);

        return new ResponseEntity(postList, HttpStatus.OK);
    }



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


    //===== 검색 =====//
    // 게시글 검색 제목에 맞게 조회
    @GetMapping("api/v1/posts/title/{title}")
    public ResponseEntity<List<PostResponseDto>> findByTitleContaining(@PageableDefault(size = 9) Pageable pageable, @PathVariable(name = "title") String postTitle) {
        List<PostResponseDto> postList = postService.findByTitleContaining(postTitle, pageable);

        return new ResponseEntity(postList, HttpStatus.OK);
    }


    // 게시글 카테고리에 맞게 조회
    @GetMapping("api/v1/posts/category/{category}")
    public ResponseEntity<List<PostResponseDto>> findByCategory(@PageableDefault(size = 9) Pageable pageable, @PathVariable(name = "category") Category category) {
        List<PostResponseDto> postList = postService.findByCategory(category, pageable);

        return new ResponseEntity(postList, HttpStatus.OK);
    }

    // 게시글 카테고리에 맞게 조회하되 추천순으로 반환
    @GetMapping("api/v1/posts/category-top/{category}")
    public ResponseEntity<List<PostResponseDto>> findByCategoryOrderByRecommendCntDesc(@PageableDefault(size = 5) Pageable pageable, @PathVariable(name = "category") Category category) {
        List<PostResponseDto> postList = postService.findByCategoryOrderByRecommendCntDesc(category, pageable);

        return new ResponseEntity(postList, HttpStatus.OK);
    }

     
    //===== 게시글 =====//
    // 게시글 하나 조회
    @GetMapping("api/v1/posts/{id}")
    public ResponseEntity<PostResponseDto> findById(@PathVariable(name = "id") Long postId) throws Exception {

        PostResponseDto post = postService.findById(postId);

        return new ResponseEntity(post, HttpStatus.OK);
    }
    
    // 게시글 생성

    // 예외 던지기 = throws -> 이 메서드는 Exception1, Exception2....ExceptionN와 같은 Exception이 발생할 수 있으니,
    // 이 메서드를 호출하고자 하는 메서드에서는  Exception1, Exception2....ExceptionN을 처리 해주어야 한다는 뜻이다.
    // 자신을 호출한 메서드에 예외를 전가시키는 것.
    @PostMapping("api/v1/posts/{id}")
    public ResponseEntity<PostCreateResponseDto> save(@Valid PostRequestDto postDTO,
                                                      // @RequestPart("image")은 API 요청시 JSON을 해당 인자 이름으로 넘기면 된다.
                                                      @RequestPart(value ="image") MultipartFile multipartFile,
                                                      // 해당 userId는 추후 jwt를 이용한 Principal로 변경하기
                                                      @PathVariable(name = "id") Long userId) throws Exception {

        Post post = postService.save(postDTO, multipartFile, userId);

        return new ResponseEntity(PostCreateResponseDto.fromCreatePost(post), HttpStatus.CREATED);
    }

    // 게시글 수정
    @PutMapping("api/v1/posts/{postId}/{userId}")
    public ResponseEntity<PostResponseDto> update(@Valid PostRequestDto postDTO,
                                                  // required=false는 보내지않아도 에러를 일으키지 않는다.
                                                  @RequestPart(value ="image", required = false) MultipartFile multipartFile,
                                                  // 해당 userId는 추후 jwt를 이용한 Principal로 변경하기
                                                  @PathVariable(name = "userId") Long userId,
                                                  @PathVariable(name = "postId") Long postId) throws Exception {

        Post post = postService.updatePost(postId, postDTO, multipartFile, userId);

        return new ResponseEntity(PostResponseDto.fromUpdatePost(post), HttpStatus.OK);
    }
    
    // 게시글 삭제
    @DeleteMapping("api/v1/posts/{postId}/{userId}")
    public ResponseEntity<PostLikeDto> delete(// 해당 userId는 추후 jwt를 이용한 Principal로 변경하기
                                                @PathVariable(name = "userId") Long userId,
                                                @PathVariable(name = "postId") Long postId) throws Exception {
        postService.delete(postId, userId);

        return new ResponseEntity("게시글이 삭제되었습니다.", HttpStatus.OK);
    }

    // 게시글 좋아요
    @PostMapping("api/v1/postlike/{postId}/{userId}")
    public ResponseEntity<PostLikeDto> postLike(// 해당 userId는 추후 jwt를 이용한 Principal로 변경하기
                                                @PathVariable(name = "userId") Long userId,
                                                @PathVariable(name = "postId") Long postId) throws Exception {

        PostLikeDto post = postService.postLike(postId, userId);

        return new ResponseEntity(post, HttpStatus.OK);
    }

}
