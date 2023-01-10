package com.example.Datenow.controller;

import com.example.Datenow.DTO.UserResponseDTO;
import com.example.Datenow.DTO.UserSignupDTO;
import com.example.Datenow.domain.User.User;
import com.example.Datenow.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/api/v1/user/signup")
    public UserResponseDTO createUserResponse(@RequestBody @Valid UserSignupDTO userSignupDTO){
        User user = userService.join(userSignupDTO);
        return new UserResponseDTO(user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getName(),
                user.getCreated_at());
    }
}
