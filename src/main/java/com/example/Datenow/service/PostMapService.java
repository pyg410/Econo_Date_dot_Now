package com.example.Datenow.service;

import com.example.Datenow.DTO.PostDto.PostMapRequestDto;
import com.example.Datenow.DTO.PostDto.PostRequestDto;
import com.example.Datenow.domain.Post.Post;
import com.example.Datenow.domain.Post.PostMap;
import com.example.Datenow.domain.User;
import com.example.Datenow.repository.PostMapRepository;
import com.example.Datenow.repository.PostRepository;
import com.example.Datenow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostMapService {

    @Autowired private final PostRepository postRepository;

    @Autowired private final UserRepository userRepository;

    @Autowired private final PostMapRepository Repository;

    // PostMap 생성
    @Transactional
    public PostMap save(PostMapRequestDto postMapDTO, Long postId, Long userId) {

        // Post 생성
        Optional<Post> optPost = postRepository.findById(postId);
        Post post = optPost.get();

        // User 생성
        Optional<User> optUser = userRepository.findById(userId);
        User user = optUser.get();
        
        // PostMap 생성
        PostMap postMap = PostMap.builder()
                        .lat(postMapDTO.getLat())
                        .lng(postMapDTO.getLng())
                        .expCost(postMapDTO.getExpCost())
                        .keyword(postMapDTO.getKeyword())
                        .content(postMapDTO.getContent())
                        .post(post)
                        .user(user)
                        .build();

        postMap.mappingPost(post);
        postMap.mappingUser(user);

        PostMap responsePostMap = Repository.save(postMap);

        return responsePostMap;
    }

    // PostMap 수정
    @Transactional
    public PostMap updatePostMap(PostMapRequestDto postDTO, Long postMapId, Long userId) {

        Optional<PostMap> optPostMap = Repository.findById(postMapId);
        PostMap postMap = optPostMap.get();

        // 만약 PostMap 작성자와 현 유저의 id가 같다면
        if (postMap.getUser().getId().equals(userId)) {
            // 위도, 경도 변경하기
            if (postDTO.getLat() != null || postDTO.getLng() != null) {
                postMap.changeLatLng(postDTO.getLat(), postDTO.getLng());
            }
            // 예상 비용 변경하기
            if (postDTO.getExpCost() != null) {
                postMap.changeExpCost(postDTO.getExpCost());
            }
            // 위치 키워드 변경하기
            if (postDTO.getKeyword() != null) {
                postMap.changeKeyword(postDTO.getKeyword());
            }
            // 내용 변경하기
            if (postDTO.getContent() != null) {
                postMap.changeContent(postDTO.getContent());
            }
            return postMap;

        } else {
            throw new IllegalStateException("권한이 없습니다.");
        }
    }



    // PostMap 삭제
    @Transactional
    public void delete(Long postMapId, Long userId) {
        Optional<PostMap> optPostMap = Repository.findById(postMapId);
        PostMap postMap = optPostMap.get();

        if (postMap.getUser().getId().equals(userId)) {
            // postMap을 삭제한다.
            Repository.delete(postMap);
        } else {
            throw new IllegalStateException("권한이 없습니다.");
        }
    }
}
