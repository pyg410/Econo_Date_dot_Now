package com.example.Datenow.repository;

import com.example.Datenow.domain.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // save
    // findByOne

    // 회원가입 중복 검증(이름, 이메일)
    boolean existsByName(String name);
    boolean existsByEmail(String email);

    List<User> findByNameOrEmail(String name, String email);



}
