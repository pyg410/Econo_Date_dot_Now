package com.example.Datenow.repository;

import com.example.Datenow.domain.Post.PostLike;
import com.example.Datenow.domain.Post.PostMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostMapRepository extends JpaRepository<PostMap, Long> {

}
