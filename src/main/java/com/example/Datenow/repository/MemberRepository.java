package com.example.Datenow.repository;

import com.example.Datenow.domain.User.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
public class MemberRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
