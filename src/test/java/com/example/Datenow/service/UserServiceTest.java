package com.example.Datenow.service;

import com.example.Datenow.DTO.user.UserSignupDTO;
import com.example.Datenow.domain.User.Gender;

import com.example.Datenow.domain.User.User;
import com.example.Datenow.repository.UserRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired private UserService userService;
    @Autowired private UserRepository userRepository;

    public UserSignupDTO initSignupDTO(){
        return UserSignupDTO.builder()
                .email("a@naver.com")
                .password("password!1A")
                .name("nickname")
                .birth(LocalDate.now())
                .profileImg(null)
                .gender(Gender.MAN)
                .phoneNum("01012341234")
                .build();
    }

    @Test
    public void 회원가입() throws Exception{
        // given
        UserSignupDTO userSignupDTO = initSignupDTO();

        // when
        User user = userService.join(userSignupDTO);

        // then
        assertThat(user).isEqualTo(userRepository.findById(user.getId()).get());

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

    @Test
    public void 유효성검사() throws Exception{
        // given

        // when
        assertThrows(IllegalStateException.class, () ->{
            UserSignupDTO userSignupDTO = UserSignupDTO.builder()
                        .email("anavercom")
                        .password("password")
                        .name("e")
                        .birth(LocalDate.now())
                        .profileImg(null)
                        .gender(Gender.MAN)
                        .phoneNum("12341234")
                        .build();
        });

        // then
        fail("예외가 발생해야합니다!");
    }
}