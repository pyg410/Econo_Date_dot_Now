package com.example.Datenow.controller;

import com.example.Datenow.DTO.UserJoinDTO;
import com.example.Datenow.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/api/v1/user/signup")
    public String createForm(UserJoinDTO userJoinDTO){

    }

    @PostMapping("/api/v1/user/signup")
    public String create(@Valid UserJoinDTO userJoinDTO, BindingResult result){

    }
}
