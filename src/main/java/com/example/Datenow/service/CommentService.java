package com.example.Datenow.service;

import com.example.Datenow.DTO.CommentDto.CommentRequestDto;
import com.example.Datenow.domain.Comment;
import com.example.Datenow.domain.Post.Post;
import com.example.Datenow.domain.User;
import com.example.Datenow.repository.CommentRepository;
import com.example.Datenow.repository.PostRepository;
import com.example.Datenow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    @Autowired private final CommentRepository repository;
    @Autowired private final PostRepository postRepository;
    @Autowired private final UserRepository userRepository;

    // 댓글 생성
    @Transactional
    public Comment save(Long postId, CommentRequestDto commentRequestDto, Long userId) throws Exception {
        // new Exception -> Illegal등 특정 에러 문장 적기
        // try - catch 문으로 에러 핸들링
        //
        Post post = postRepository.findById(postId).orElseThrow(() -> new Exception("Post가 Null값입니다."));

        // User 생성 예외처리하기
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User가 Null값입니다."));

        Comment comment = Comment.builder()
                .content(commentRequestDto.getContent())
                .post(post)
                .user(user)
                .build();

        comment.mappingPostAndUser(post, user);

        Comment saveComment = repository.save(comment);

        return saveComment;
    }
    
    // 댓글 수정
    @Transactional
    public Comment update(Long commentId, Long userId, CommentRequestDto commentRequestDto) throws Exception {
        // Comment 생성 예외처리하기
        Comment comment = repository.findById(userId).orElseThrow(() -> new Exception("Comment가 Null값입니다."));


        // 만약 댓글 작성자와 현 유저의 id가 같다면
        if (comment.getUser().getId().equals(userId)) {
            // 글의 제목과 내용을 입력받은대로 수정해라
            comment.changeContents(commentRequestDto.getContent());
            // 수정하고, 수정한 comment를 반환해라
            return comment;

        } else {
            throw new IllegalStateException("권한이 없습니다.");
        }
    }
    
    // 댓글 삭제
    @Transactional
    public void delete(Long commentId, Long userId) throws Exception {
        // Comment 생성 예외처리하기
        Comment comment = repository.findById(userId).orElseThrow(() -> new Exception("Comment가 Null값입니다."));


        if (comment.getUser().getId().equals(userId)) {
            repository.delete(comment);
        } else {
            throw new IllegalStateException("권한이 없습니다.");
        }
    }

}