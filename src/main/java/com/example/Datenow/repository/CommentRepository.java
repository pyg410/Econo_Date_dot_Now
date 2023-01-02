package com.example.Datenow.repository;

import com.example.Datenow.domain.Comment;
import com.example.Datenow.domain.Post.Post;
import com.example.Datenow.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
