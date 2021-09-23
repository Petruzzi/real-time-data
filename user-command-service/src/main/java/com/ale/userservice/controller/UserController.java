package com.ale.userservice.controller;

import com.ale.userservice.dto.UserDto;
import com.ale.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    private Long createUser(@RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }

}
