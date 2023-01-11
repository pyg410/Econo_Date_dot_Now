package com.example.Datenow.service;

import com.example.Datenow.DTO.user.UserSignupDTO;
import com.example.Datenow.domain.User.Gender;

import com.example.Datenow.domain.User.User;
import com.example.Datenow.repository.UserRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class UserServiceTest {

    @Autowired private UserService userService;
    @Autowired private UserRepository userRepository;
    @Autowired
    MockMvc mock;

    public UserSignupDTO initSignupDTO(){
        return UserSignupDTO.builder()
                .password("password!1A")
                .email("a@naver.com")
                .name("nickname")
                .birth(LocalDate.now())
                .phoneNum("01012341234")
                .profileImg(null)
                .gender(Gender.MAN)
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
    }

    @Test
    @DisplayName("유효성 검사")
    public void rightInputTest() throws Exception{
        // given
        JSONObject json = new JSONObject();
        json.put("password", "3456"); // exception
        json.put("email", "aa@gmail.com"); // exception
        json.put("name", "aasd"); // exception
        json.put("birth", "2000-01-01");
        json.put("phoneNum", "01011231231"); // exception
        json.put("profileImg", null);
        json.put("gender", "MAN");
        // when
        ResultActions act = mock.perform(post("/api/v1/user/signup").contentType(MediaType.APPLICATION_JSON).content(json.toString()));
        // then
        act.andExpect(status().is4xxClientError()); // 4xx 클라이언트 에러 발생시 성공
    }
}