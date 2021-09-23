package com.ale.userservice.service;

import com.ale.userservice.dto.UserDto;

public interface UserService {
    Long createUser(UserDto userDto);
    void updateUser(UserDto userDto);
}
