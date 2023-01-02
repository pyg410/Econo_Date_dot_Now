package com.example.Datenow.service;

import com.example.Datenow.DTO.CommentDto.CommentRequestDto;
import com.example.Datenow.DTO.CommentDto.CommentResponseDto;
import com.example.Datenow.domain.Comment;
import com.example.Datenow.domain.Post.Post;
import com.example.Datenow.domain.User;
import com.example.Datenow.repository.CommentRepository;
import com.example.Datenow.repository.PostRepository;
import com.example.Datenow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository Repository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 댓글 생성
    @Transactional
    public Comment save(Long postId, CommentRequestDto commentRequestDto, Long userId) {
        Optional<Post> optPost = postRepository.findById(postId);

        Post post = optPost.get();

        //Post post = byId.orElseThrow(() -> new PostNotFound("게시물이 삭제되었거나 존재하지 않습니다."));

        Optional<User> optUser = userRepository.findById(userId);

        User user = optUser.get();
        //User user = byEmail.orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다."));

        Comment comment = Comment.builder()
                .content(commentRequestDto.getContent())
                .post(post)
                .user(user)
                .build();

        comment.mappingPostAndUser(post, user);

        Comment saveComment = Repository.save(comment);

        return saveComment;
    }
    
    // 댓글 수정
    @Transactional
    public Comment update(Long commentId, Long userId, CommentRequestDto commentRequestDto) {
        Optional<Comment> optComment = Repository.findById(commentId);
        Comment comment = optComment.get();

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
    public void delete(Long commentId, Long userId) {
        Optional<Comment> optComment = Repository.findById(commentId);
        Comment comment = optComment.get();

        if (comment.getUser().getId().equals(userId)) {
            Repository.delete(comment);
        } else {
            throw new IllegalStateException("권한이 없습니다.");
        }
    }

}