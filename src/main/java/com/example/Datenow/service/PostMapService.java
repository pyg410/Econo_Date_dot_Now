package com.example.Datenow.service;

import com.example.Datenow.DTO.PostDto.PostMapDto.PostMapRequestDto;
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


@Service
@RequiredArgsConstructor
@Transactional
public class PostMapService {

    @Autowired private final PostRepository postRepository;

    @Autowired private final UserRepository userRepository;

    @Autowired private final PostMapRepository repository;

    // PostMap 생성
    @Transactional
    public PostMap save(PostMapRequestDto postMapDTO, Long postId, Long userId) throws Exception{

        // Post 생성 예외처리하기
        Post post = postRepository.findById(postId).orElseThrow(() -> new Exception("Post가 Null값입니다."));

        // User 생성 예외처리하기
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User가 Null값입니다."));


        // PostMap 생성
        PostMap postMap = PostMap.builder()
                        .expCost(postMapDTO.getExpCost())
                        .keyword(postMapDTO.getKeyword())
                        .content(postMapDTO.getContent())
                        .post(post)
                        .user(user)
                        .build();

        postMap.mappingPost(post);
        postMap.mappingUser(user);

        PostMap responsePostMap = repository.save(postMap);

        return responsePostMap;
    }

    // PostMap 수정
    @Transactional
    public PostMap updatePostMap(PostMapRequestDto postDTO, Long postMapId, Long userId) throws Exception {


        PostMap postMap = repository.findById(postMapId).orElseThrow(() -> new Exception("PostMap이 Null값입니다."));


        // 만약 PostMap 작성자와 현 유저의 id가 같다면
        if (postMap.getUser().getId().equals(userId)) {
            // 위도, 경도 변경하기
//            if (postDTO.getLat() != null || postDTO.getLng() != null) {
//                postMap.changeLatLng(postDTO.getLat(), postDTO.getLng());
//            }
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
    public void delete(Long postMapId, Long userId) throws Exception {
        PostMap postMap = repository.findById(postMapId).orElseThrow(() -> new Exception("PostMap이 Null값입니다."));


        if (postMap.getUser().getId().equals(userId)) {
            // postMap을 삭제한다.
            repository.delete(postMap);
        } else {
            throw new IllegalStateException("권한이 없습니다.");
        }
    }
}
