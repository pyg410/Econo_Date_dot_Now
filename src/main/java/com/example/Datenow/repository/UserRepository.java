package com.example.Datenow.repository;

import com.example.Datenow.domain.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

        // 게시글 1개 반환
        //Optional<User> findById(Long userId);

        //List<User> findAll(Sort sort);

        // 기본 제공 메서드가 있음

        // findBypost 같은 @Query 통해서 직접 쿼리문을 작성하면된다.
        // 쿼리 같은 경우 querydsl 같은 것 = 빌더 패턴

}
