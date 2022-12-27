package com.example.Datenow.repository;

import com.example.Datenow.domain.Post.Post;
import com.example.Datenow.domain.Post.PostLike;
import com.example.Datenow.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    // 내가 좋아요한 게시글만 볼 수 있게
    Optional<PostLike> findByPostAndUser(Post post, User user);


}
