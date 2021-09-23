package com.ale.userqueryservice.service.impl;

import com.ale.userqueryservice.model.UserDto;
import com.ale.userqueryservice.repository.UserRepository;
import com.ale.userqueryservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public Mono<UserDto> addUserToCb(UserDto userDto) {
        return userRepository.save(userDto);
    }

    @Override
    public Flux<UserDto> getAllUsers() {
        return userRepository.findAll();
    }
}
