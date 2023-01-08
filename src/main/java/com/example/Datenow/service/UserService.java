package com.example.Datenow.service;

import com.example.Datenow.DTO.UserJoinDTO;
import com.example.Datenow.domain.User.User;
import com.example.Datenow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    // 회원가입
    public Long join(UserJoinDTO userJoinDTO){
        // DTO -> User
        User user = User.builder()
                .birth(userJoinDTO.getBirth())
                .profileImg(userJoinDTO.getProfileImg())
                .phoneNum(userJoinDTO.getPhoneNum())
                .email(userJoinDTO.getEmail())
                .gender(userJoinDTO.getGender())
                .name(userJoinDTO.getName())
                .password(userJoinDTO.getPassword())
                .created_at(LocalDateTime.now())
                .build();
        //중복 회원 검증


        userRepository.save(user);

        // ID값만 return. 엔티티 통째로 넘기면 위험하지않나?
        return user.getId();
    }

}
