package com.example.Datenow.service;

import com.example.Datenow.DTO.UserJoinDTO;
import com.example.Datenow.domain.User.Gender;

import com.example.Datenow.domain.User.User;
import com.example.Datenow.repository.UserRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired private UserService userService;

    public UserJoinDTO initSignupDTO(){
        return UserJoinDTO.builder()
                .email("a@naver.com")
                .password("password")
                .name("nickname")
                .birth(LocalDate.now())
                .profileImg(null)
                .gender(Gender.MAN)
                .phoneNum("010 1234 1234")
                .build();
    }

    @Test
    public void 회원가입() throws Exception{
        // given
        UserJoinDTO userJoinDTO = initSignupDTO();

        // when
        User user = userService.join(userJoinDTO);

        // then
        assertThat(userJoinDTO.getEmail()).isEqualTo(user.getEmail());
        assertThat(userJoinDTO.getName()).isEqualTo(user.getName());
    }

    @Test
    public void 중복회원예외() throws Exception{
        // given
        User user1 = userService.join(initSignupDTO());

        // when
        assertThrows(IllegalStateException.class, () ->{
            User user2 = userService.join(initSignupDTO());
        });

        // then
        fail("예외가 발생해야합니다!");
    }
}