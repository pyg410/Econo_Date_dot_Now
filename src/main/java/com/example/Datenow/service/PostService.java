package com.example.Datenow.service;

import com.example.Datenow.DTO.CommentDto.CommentResponseDto;
import com.example.Datenow.DTO.PostDto.PostLikeDto;
import com.example.Datenow.DTO.PostDto.PostRequestDto;
import com.example.Datenow.DTO.PostDto.PostResponseDto;
import com.example.Datenow.domain.Category;
import com.example.Datenow.domain.Post.Post;
import com.example.Datenow.domain.Post.PostLike;
import com.example.Datenow.domain.User;
import com.example.Datenow.repository.PostLikeRepository;
import com.example.Datenow.repository.PostRepository;
import com.example.Datenow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    // 본인 엔티티 Repository는 그냥 Repository라 네이밍한다.
    @Autowired
    private final PostRepository Repository;

    @Autowired
    private final PostLikeRepository postLikeRepository;

    @Autowired
    private final UserRepository userRepository;

    // 게시글 생성
    @Transactional
    public Post save(PostRequestDto postDTO, Long userId) {

        Optional<User> optUser = userRepository.findById(userId);
        User user = optUser.get();

        HashMap<Double, Double> map = new HashMap<>();
        map.put(postDTO.getLat(), postDTO.getLng());

        List<HashMap<Double, Double>> mapArray = new ArrayList<>();
        mapArray.add(map);

        Post post = Post.builder()
                .title(postDTO.getTitle())
                .content(postDTO.getContent())
                .user(user)
                .category(postDTO.getCategory())
                .postMapList(mapArray)
                .viewCnt(0)
                .scrapCnt(0)
                .recommendCnt(0)
                .imageUrl(postDTO.getImageUrl())
                .build();

        post.mappingUser(user);

        Post responsePost = Repository.save(post);

        return responsePost;
    }
    
    // 게시글 수정
    @Transactional
    public Post updatePost(Long postId, PostRequestDto postDTO, Long userId) {
        Post post = getPostInService(postId);
        
        // 만약 글 작성자와 현 유저의 id가 같다면
        if (post.getUser().getId().equals(userId)) {
            // 글의 제목과 내용을 입력받은대로 수정해라
            post.changeTitle(postDTO.getTitle());
            post.changeContents(postDTO.getContent());
            // 수정하고, 수정한 Post를 반환해라
            return post;

        } else {
            throw new IllegalStateException("권한이 없습니다.");
        }
    }
    
    // 게시글 삭제
    @Transactional
    public void delete(Long postId, Long userId) {
        Optional<Post> optPost = Repository.findById(postId);
        Post post = optPost.get();

        if (post.getUser().getId().equals(userId)) {
            Repository.delete(post);
        } else {
            throw new IllegalStateException("권한이 없습니다.");
        }
    }

    // 게시글 좋아요
    @Transactional
    public PostLikeDto postLike(Long postId, Long userId) {
        Post post = getPostInService(postId);
        Optional<User> optUser = userRepository.findById(userId);
        User user = optUser.get();

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
    public PostResponseDto findById(Long postId) {
        Optional<Post> optPost = Repository.findById(postId);
        Post post = optPost.get();

        post.addViewCount();

        List<CommentResponseDto> commentDTOS = CommentResponseDto.FromCommentList(post.getCommentList());

        int commentSize = post.getCommentList().size();

        return PostResponseDto.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .category(post.getCategory())
                .commentCnt(commentSize)
                .viewCnt(post.getViewCnt())
                .recommendCnt(post.getRecommendCnt())
                .writer(post.getUser().getUsername())
                .map(post.getPostMapList())
                .scrapCnt(post.getScrapCnt())
                .comments(commentDTOS)
                .createdDate(post.getCreatedDate())
                .modifiedDate(post.getModifiedDate())
                .build();
    }
    
    // 서비스 내에서 POST 객체 반환
    private Post getPostInService(Long postId) {
        Optional<Post> optPost = Repository.findById(postId);
        return optPost.get();
    }
    
    // 게시글 전체 반환
    @Transactional(readOnly = true)
    public List<PostResponseDto> findAll() {

        return Repository.findAll().stream().map(PostResponseDto::fromManyPost).collect(Collectors.toList());
    }

    // 게시글 제목 검색 결과 반환
    @Transactional(readOnly = true)
    public List<PostResponseDto> findByTitleContaining(String search) {

        return Repository.findByTitleContaining(search).stream().map(PostResponseDto::fromDetailPost).collect(Collectors.toList());
    }

    // 게시글 추천순 반환
    @Transactional(readOnly = true)
        public List<PostResponseDto> findAllByOrderByRecommendCntDesc() {
        /*
        Post 자료형을 가진 스트림 내 요소들을 PostResponseDto.FromManyPost 맞게 바꿔준 후 하나의 리스트로 만들어준다.
         */
        return Repository.findAllByOrderByRecommendCntDesc().stream().map(PostResponseDto::fromManyPost).collect(Collectors.toList());
    }

    // 게시글 최신순 반환
    @Transactional(readOnly = true)
    public List<PostResponseDto> findAllByOrderByCreatedDateDesc() {
        /*
        Post 자료형을 가진 스트림 내 요소들을 PostResponseDto.FromManyPost 맞게 바꿔준 후 하나의 리스트로 만들어준다.
         */
        return Repository.findAllByOrderByCreatedDateDesc().stream().map(PostResponseDto::fromManyPost).collect(Collectors.toList());
    }

    // 게시글 오래된순 반환
    @Transactional(readOnly = true)
    public List<PostResponseDto> findAllByOrderByCreatedDateAsc() {
        /*
        Post 자료형을 가진 스트림 내 요소들을 PostResponseDto.FromManyPost 맞게 바꿔준 후 하나의 리스트로 만들어준다.
         */
        return Repository.findAllByOrderByCreatedDateAsc().stream().map(PostResponseDto::fromManyPost).collect(Collectors.toList());
    }
    
    // 카테코리별 게시글
    @Transactional(readOnly = true)
    public List<PostResponseDto> findByCategory(Category category){
        /*
        Post 자료형을 가진 스트림 내 요소들을 PostResponseDto.FromManyPost 맞게 바꿔준 후 하나의 리스트로 만들어준다.
         */
        return Repository.findByCategory(category).stream().map(PostResponseDto::fromManyPost).collect(Collectors.toList());
    }
    
    // 카테고리별 추천 게시글들
    @Transactional(readOnly = true)
    public List<PostResponseDto> findByCategoryOrderByRecommendCntDesc(Category category){
        return Repository.findByCategoryOrderByRecommendCntDesc(category).stream().map(PostResponseDto::fromManyPost).collect(Collectors.toList());
    }

}
