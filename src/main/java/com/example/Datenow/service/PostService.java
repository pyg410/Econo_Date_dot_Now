package com.example.Datenow.service;

import com.example.Datenow.DTO.CommentDto.CommentResponseDto;
import com.example.Datenow.DTO.PostDto.*;
import com.example.Datenow.DTO.PostDto.MapDto.MapResponseDTO;
import com.example.Datenow.DTO.PostDto.PostMapDto.PostMapResponseDto;
import com.example.Datenow.domain.Category;
import com.example.Datenow.domain.Post.Post;
import com.example.Datenow.domain.Post.PostLike;
import com.example.Datenow.domain.User;
import com.example.Datenow.repository.PostLikeRepository;
import com.example.Datenow.repository.PostRepository;
import com.example.Datenow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    // 본인 엔티티 Repository는 그냥 Repository라 네이밍한다.
    @Autowired private final PostRepository repository;

    @Autowired private final PostLikeRepository postLikeRepository;

    @Autowired private final UserRepository userRepository;

    @Autowired private final S3Upload s3Upload;

    /*
    Home
     */

    // 카테고리 반환
    @Transactional(readOnly = true)
    public List<Category> findCategorys(){
        List<Category> categoryList = Stream.of(Category.values())
                .collect(Collectors.toList());


        return categoryList;
    }

    // 특정 id 게시글들만 선정해서 보내주기
    @Transactional(readOnly = true)
    public List<PostResponseDto> findEvent() throws Exception {
        List<PostResponseDto> responseDtoList = new ArrayList<>();
        
        // 특정 id값으로 게시글들을 가져옴
        responseDtoList.add(PostResponseDto.fromManyPost(repository.findById(1L).
                orElseThrow(() -> new Exception("1번 Post가 Null값입니다."))));

        responseDtoList.add(PostResponseDto.fromManyPost(repository.findById(2L).
                orElseThrow(() -> new Exception("2번 Post가 Null값입니다."))));

        responseDtoList.add(PostResponseDto.fromManyPost(repository.findById(3L).
                orElseThrow(() -> new Exception("3번 Post가 Null값입니다."))));

        responseDtoList.add(PostResponseDto.fromManyPost(repository.findById(4L).
                orElseThrow(() -> new Exception("4번 Post가 Null값입니다."))));

        responseDtoList.add(PostResponseDto.fromManyPost(repository.findById(5L).
                orElseThrow(() -> new Exception("5번 Post가 Null값입니다."))));
        
        

        return responseDtoList;
    }

    // 게시글 추천순 글 5개 반환
    @Transactional(readOnly = true)
    public List<PostResponseDto> findAllByOrderByRecommendCntDesc5(Pageable pageable) {
        /*
        Post 자료형을 가진 스트림 내 요소들을 PostResponseDto.FromManyPost 맞게 바꿔준 후 하나의 리스트로 만들어준다.
         */
        return repository.findAllByOrderByRecommendCntDesc(pageable)
                .stream()
                .map(PostResponseDto::fromManyPost)
                .collect(Collectors.toList());
    }

    /*
    게시판
     */
    // 게시글 추천순 반환
    @Transactional(readOnly = true)
    public List<PostResponseDto> findAllByOrderByRecommendCntDesc(Pageable pageable) {
        /*
        Post 자료형을 가진 스트림 내 요소들을 PostResponseDto.FromManyPost 맞게 바꿔준 후 하나의 리스트로 만들어준다.
         */
        return repository.findAllByOrderByRecommendCntDesc(pageable)
                .stream()
                .map(PostResponseDto::fromManyPost)
                .collect(Collectors.toList());
    }

    // 게시글 최신순 반환
    @Transactional(readOnly = true)
    public List<PostResponseDto> findAllByOrderByCreatedDateDesc(Pageable pageable) {
        /*
        Post 자료형을 가진 스트림 내 요소들을 PostResponseDto.FromManyPost 맞게 바꿔준 후 하나의 리스트로 만들어준다.
         */
        return repository.findAllByOrderByCreatedAtDesc(pageable)
                .stream()
                .map(PostResponseDto::fromManyPost)
                .collect(Collectors.toList());
    }

    // 게시글 오래된순 반환
    @Transactional(readOnly = true)
    public List<PostResponseDto> findAllByOrderByCreatedDateAsc(Pageable pageable) {
        /*
        Post 자료형을 가진 스트림 내 요소들을 PostResponseDto.FromManyPost 맞게 바꿔준 후 하나의 리스트로 만들어준다.
         */
        return repository.findAllByOrderByCreatedAtAsc(pageable)
                .stream()
                .map(PostResponseDto::fromManyPost)
                .collect(Collectors.toList());
    }

    /*
    게시글
     */
    // 게시글 생성
    @Transactional
    public Post save(PostRequestDto postDTO, MultipartFile multipartFile, Long userId) throws Exception {

        // User 생성 예외처리하기
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User가 Null값입니다."));


        // image
        if (multipartFile == null) {
            throw new IOException();
        }
        String imageUrl = s3Upload.upload(multipartFile);

        Post post = Post.builder()
                .title(postDTO.getTitle())
                .content(postDTO.getContent())
                .user(user)
                .category(postDTO.getCategory())
                .viewCnt(0)
                .recommendCnt(0)
                .imageUrl(imageUrl)
                .build();

        post.mappingUser(user);

        Post responsePost = repository.save(post);

        return responsePost;
    }
    
    // 게시글 수정
    @Transactional
    public Post updatePost(Long postId, PostRequestDto postDTO, MultipartFile multipartFile, Long userId) throws Exception {
        // Post 생성 예외처리하기
        Post post = repository.findById(postId).orElseThrow(() -> new Exception("Post가 Null값입니다."));


        // 만약 글 작성자와 현 유저의 id가 같다면
        if (post.getUser().getId().equals(userId)) {
            // 글의 제목과 내용을 입력받은대로 수정해라
            if (postDTO.getTitle() != null) {
                post.changeTitle(postDTO.getTitle());
            }
            if (postDTO.getContent() != null) {
                post.changeContents(postDTO.getContent());
            }
            // S3의 이미지 주소를 교체하라
            if (multipartFile != null) {
                post.changeImageUrl(s3Upload.upload(multipartFile));
            }
            // 수정하고, 수정한 Post를 반환해라
            return post;

        } else {
            throw new IllegalStateException("권한이 없습니다.");
        }
    }
    
    // 게시글 삭제
    @Transactional
    public void delete(Long postId, Long userId) throws Exception {
        // Post 생성 예외처리하기
        Post post = repository.findById(postId).orElseThrow(() -> new Exception("Post가 Null값입니다."));

        if (post.getUser().getId().equals(userId)) {
            // S3 이미지와 게시글 모두 삭제
            String uri = post.getImageUrl();
            s3Upload.deleteFile(uri);
            repository.delete(post);
        } else {
            throw new IllegalStateException("권한이 없습니다.");
        }
    }

    // 게시글 좋아요
    @Transactional
    public PostLikeDto postLike(Long postId, Long userId) throws Exception {
        // Post 생성 예외처리하기
        Post post = repository.findById(postId).orElseThrow(() -> new Exception("Post가 Null값입니다."));

        // User 생성 예외처리하기
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User가 Null값입니다."));


        // PostLike 객체를 가져온다.
        Optional<PostLike> byPostAndUser = postLikeRepository.findByPostAndUser(post, user);

        // PostLike 객체가 있다면 == 좋아요를 이미 눌렀다면, PostLike 객체에서 삭제하고, 좋아요를 취소한다.
        byPostAndUser.ifPresentOrElse(
                postLike -> {
                    postLikeRepository.delete(postLike);
                    post.discountLike(postLike);
                    post.updateLikeCount();
                },
                () -> {
                    // PostLike 객체가 없다면 == 좋아요를 누르지않았다면 PostLike 객체를 만들어주고,
                    PostLike postLike = PostLike.builder().build();

                    postLike.mappingPost(post);
                    postLike.mappingUser(user);
                    post.updateLikeCount();

                    postLikeRepository.save(postLike);
                }
        );
        return PostLikeDto.fromLikePost(post);
    }

    // 게시글 한개 반환
    @Transactional
    public PostResponseDto findById(Long postId) throws Exception {
        Post post = repository.findById(postId).orElseThrow(() -> new Exception("Post가 Null값입니다."));

        post.addViewCount();

        List<CommentResponseDto> commentDTOS = CommentResponseDto.FromCommentList(post.getCommentList());
        List<PostMapResponseDto> postMapDTOS = PostMapResponseDto.fromPostMapList(post.getPostMapList());
        List<MapResponseDTO> MapDTOS = MapResponseDTO.fromMapList(post.getMapList());

        int commentSize = post.getCommentList().size();

        return PostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .imageUrl(post.getImageUrl())
                .category(post.getCategory())
                .commentCnt(commentSize)
                .postMapList(postMapDTOS)
                .mapList(MapDTOS)
                .viewCnt(post.getViewCnt())
                .recommendCnt(post.getRecommendCnt())
                .writer(post.getUser().getUsername())
                .comments(commentDTOS)
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getUpdatedAt())
                .build();
    }
    
    // 서비스 내에서 POST 객체 반환 -> 공통되는 기능을 하나의 메서드로 묶어놓았다.
//    private Post getPostInService(Long postId) {
//        Optional<Post> optPost = repository.findById(postId);
//        return optPost.get();
//    }
    
    // 게시글 전체 반환
    @Transactional(readOnly = true)
    public List<PostResponseDto> findAll(Pageable pageable) {

        return repository.findAll(pageable).stream().map(PostResponseDto::fromManyPost).collect(Collectors.toList());
    }

    // 게시글 제목 검색 결과 반환
    @Transactional(readOnly = true)
    public List<PostResponseDto> findByTitleContaining(String search, Pageable pageable) {

        return repository.findByTitleContaining(pageable, search).stream().map(PostResponseDto::fromDetailPost).collect(Collectors.toList());
    }

    // 카테코리별 게시글
    @Transactional(readOnly = true)
    public List<PostResponseDto> findByCategory(Category category, Pageable pageable){
        /*
        Post 자료형을 가진 스트림 내 요소들을 PostResponseDto.FromManyPost 맞게 바꿔준 후 하나의 리스트로 만들어준다.
         */
        return repository.findByCategory(pageable, category).stream().map(PostResponseDto::fromManyPost).collect(Collectors.toList());
    }
    
    // 카테고리별 추천 게시글들
    @Transactional(readOnly = true)
    public List<PostResponseDto> findByCategoryOrderByRecommendCntDesc(Category category, Pageable pageable){
        return repository.findByCategoryOrderByRecommendCntDesc(pageable, category).stream().map(PostResponseDto::fromManyPost).collect(Collectors.toList());
    }


}
