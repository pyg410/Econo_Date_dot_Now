package com.example.Datenow.service;

import com.example.Datenow.DTO.user.UserSignupDTO;
import com.example.Datenow.domain.User.User;
import com.example.Datenow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    // 회원가입
    public User join(UserSignupDTO userSignupDTO){
        // DTO -> User
        User user = User.builder()
                .birth(userSignupDTO.getBirth())
                .profileImg(userSignupDTO.getProfileImg())
                .phoneNum(userSignupDTO.getPhoneNum())
                .email(userSignupDTO.getEmail())
                .gender(userSignupDTO.getGender())
                .name(userSignupDTO.getName())
                .password(userSignupDTO.getPassword())
                .created_at(LocalDateTime.now())
                .build();
        //중복 회원 검증
        duplicateUser(user);

        userRepository.save(user);

        // ID값만 return. 엔티티 통째로 넘기면 위험하지않나? -> 나중에 DTO 반환해야겠다.
        return user;
    }

    private void duplicateUser(User user) {
        List<User> duplicatedUser = userRepository.findByNameOrEmail(user.getName(), user.getEmail());
        if(!duplicatedUser.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }

    }
}
