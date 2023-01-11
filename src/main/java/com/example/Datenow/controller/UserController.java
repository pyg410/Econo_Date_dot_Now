package com.example.Datenow.controller;

import com.example.Datenow.DTO.user.UserResponseDTO;
import com.example.Datenow.DTO.user.UserSignupDTO;
import com.example.Datenow.domain.User.User;
import com.example.Datenow.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/api/v1/user/signup") // 회원가입
    public UserResponseDTO createUserResponse(@RequestBody @Valid UserSignupDTO userSignupDTO){
        User user = userService.join(userSignupDTO);
        UserResponseDTO responseDTO = UserResponseDTO.toResponseDTO(user);
        return responseDTO;
    }
}
