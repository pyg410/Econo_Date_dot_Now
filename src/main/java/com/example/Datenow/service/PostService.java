package com.example.Datenow.service;

import com.example.Datenow.DTO.CommentResponseDto;
import com.example.Datenow.DTO.PostRequestDto;
import com.example.Datenow.DTO.PostResponseDto;
import com.example.Datenow.domain.Category;
import com.example.Datenow.domain.Post.Post;
import com.example.Datenow.domain.Post.PostLike;
import com.example.Datenow.domain.User;
import com.example.Datenow.repository.PostLikeRepository;
import com.example.Datenow.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    @Autowired
    private final PostRepository postRepository;

    @Autowired
    private final PostLikeRepository postLikeRepository;

    // 게시글 생성
    public Post save(PostRequestDto postDTO, Long user_id) {
        //User user = getUserInService(user_id);

        Post post = Post.builder()
                .title(postDTO.getTitle())
                .content(postDTO.getContent())
                //.user(user)
                .category(postDTO.getCategory())
                .postMapList(postDTO.getMap())
                .viewCnt(0)
                .scrapCnt(0)
                .recommendCnt(0)
                .imageUrl(postDTO.getImageUrl())
                .build();

        // post.mappingUser(user);

        return postRepository.save(post);
    }

    // 게시글 삭제
    public void delete(Long postId, Long user_id) {
        Optional<Post> optPost = postRepository.findById(postId);
        Post post = optPost.get();

//        if (post.getUser().getEmail().equals(user_id)) {
//            postRepository.delete(post);
//        } else {
//            throw new AuthorizationServiceException("권한이 없습니다.");
//        }
    }

    // 게시글 좋아요
    public void postLike(Long postId, String email) {
        Post post = getPostInService(postId);
        //User user = getUserInService(email);
        Optional<PostLike> byPostAndUser = postLikeRepository.findByPostAndUser(post, user);

        byPostAndUser.ifPresentOrElse(
                postLike -> {
                    postLikeRepository.delete(postLike);
                    post.discountLike(postLike);
                },
                () -> {
                    PostLike postLike = PostLike.builder().build();

                    postLike.mappingPost(post);
                    //postLike.mappingUser(user);
                    post.updateLikeCount();

                    postLikeRepository.save(postLike);
                }
        );
    }

    // 게시글 한개 반환
    @Transactional(readOnly = true)
    public PostResponseDto findById(Long postId) {
        Optional<Post> optPost = postRepository.findById(postId);
        Post post = optPost.get();

        post.addViewCount();

        List<CommentResponseDto> commentDTOS = CommentResponseDto.FromCommentList(post.getCommentList());

        return PostResponseDto.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .category(post.getCategory())
                .viewCnt(post.getViewCnt())
                .recommendCnt(post.getRecommendCnt())
                //.writer(UserDTO.convertToUserDTO(findPost.getUser()))
                .map(post.getPostMapList())
                .scrapCnt(post.getScrapCnt())
                .comments(commentDTOS)
                .build();
    }
    
    // 서비스 내에서 POST 객체 반환
    private Post getPostInService(Long postId) {
        Optional<Post> optPost = postRepository.findById(postId);
        Post post = optPost.get();
        return post;
    }
    
    // 게시글 전체 반환
    @Transactional(readOnly = true)
    public List<PostResponseDto> findAll() {

        Stream<Post> stream = postRepository.findAll().stream();

        return stream.map(PostResponseDto::FromManyPost).collect(Collectors.toList());
    }

    // 게시글 제목 검색 결고 반환
    @Transactional(readOnly = true)
    public List<PostResponseDto> findByTitleContaining(String search) {

        Stream<Post> stream = postRepository.findByTitleContaining(search).stream();

        return stream.map(PostResponseDto::FromDetailPost).collect(Collectors.toList());
    }

    // 게시글 추천순 반환
    @Transactional
    public List<PostResponseDto> findAllByOrderByRecommendCntDesc() {
        Stream<Post> stream = postRepository.findAllByOrderByRecommendCntDesc().stream();

        return stream.map(PostResponseDto::FromManyPost).collect(Collectors.toList());
    }

    // 카테코리별 게시글
    @Transactional
    public List<PostResponseDto> findByCategory(Category category){
        Stream<Post> stream = postRepository.findByCategory(category).stream();
        /*
        즉 Post 자료형을 가진 스트림 내 요소들을 PostResponseDto.FromManyPost 맞게 바꿔준 후 하나의 리스트로 만들어준다.
         */
        return stream.map(PostResponseDto::FromManyPost).collect(Collectors.toList());
    }

}
