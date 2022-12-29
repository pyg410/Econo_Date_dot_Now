package com.example.Datenow.service;

import com.example.Datenow.DTO.CommentRequestDto;
import com.example.Datenow.DTO.CommentResponseDto;
import com.example.Datenow.domain.Comment;
import com.example.Datenow.domain.Post.Post;
import com.example.Datenow.domain.User;
import com.example.Datenow.repository.CommentRepository;
import com.example.Datenow.repository.PostRepository;
import com.example.Datenow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentResponseDto createComment(Long postId, CommentRequestDto commentRequestDto, Long userId) {
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

        Comment saveComment = commentRepository.save(comment);

        return CommentResponseDto.FromComment(saveComment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> findAllByPostAndUser(Long postId, Long userId) {
        Optional<Post> optPost = postRepository.findById(postId);

        Post post = optPost.get();

        Optional<User> optUser = userRepository.findById(userId);

        User user = optUser.get();

        List<Comment> commentList = commentRepository.findAllByPostAndUser(post, user);

        return CommentResponseDto.FromCommentList(commentList);
    }
}