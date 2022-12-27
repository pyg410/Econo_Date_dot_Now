package com.example.Datenow.repository;

import com.example.Datenow.domain.Category;
import com.example.Datenow.domain.Post.Post;
import com.example.Datenow.domain.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // 게시글 전체 반환
    List<Post> findAll(Sort sort);


    // 게시글 1개 반환
    Optional<Post> findById(Long postId);


    // 게시글 생성 및 업데이트
    Post save(Post post);


    // 게시글 삭제
    void delete(Post post);


    // 추천순 정렬
    List<Post> findAllByOrderByRecommendCntDesc();


    // 카테코리별 게시글
    @Query("select m from Post m where m.category = :category")
    List<Post> findByCategory(@Param("category") Category category);


    // 게시글 검색
    List<Post> findByTitleContaining(String keyword);


    // 작성자별 게시글
//    @Query("select m from Post m where m.username = :username")
//    List<Post> findByUsername(@Param("username") String username);

}
