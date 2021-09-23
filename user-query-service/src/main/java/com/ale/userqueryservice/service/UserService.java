package com.ale.userqueryservice.service;


import com.ale.userqueryservice.model.UserDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<UserDto> addUserToCb(UserDto userDto);
    Flux<UserDto> getAllUsers();
}
