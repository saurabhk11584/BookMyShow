package com.example.BookMyShow.controllers;

import com.example.BookMyShow.dtos.SignUpRequestDto;
import com.example.BookMyShow.dtos.SignUpResponseDto;
import com.example.BookMyShow.models.ResponseStatus;
import com.example.BookMyShow.models.User;
import com.example.BookMyShow.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    private UserService userService;
    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }
    public SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto) {
        User user;
        SignUpResponseDto responseDto = new SignUpResponseDto();
        try {
            user = userService.signUp(signUpRequestDto.getEmailId(), signUpRequestDto.getPassword());
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
            responseDto.setUserId(user.getId());
        } catch (Exception e) {
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
            responseDto.setUserId(-1L);
        }
        return responseDto;
    }
}
